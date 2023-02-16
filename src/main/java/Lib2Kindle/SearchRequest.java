package Lib2Kindle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRequest {
  private final String query;
  private final String searchType;

  public SearchRequest(String query, String searchType) {
    this.query = query;
    this.searchType = searchType;
    if (this.query.length() < 3) {
      throw new RuntimeException("Query is too short");
    }
  }

  public List<Map<String, String>> aggregateRequestData() {
    try {
      String queryParsed = String.join("%20", query.split(" "));
      String searchUrl;
      if (searchType.toLowerCase().equals("title")) {
        searchUrl = "http://gen.lib.rus.ec/search.php?req=" + queryParsed + "&column=title";
      } else if (searchType.toLowerCase().equals("author")) {
        searchUrl = "http://gen.lib.rus.ec/search.php?req=" + queryParsed + "&column=author";
      } else {
        throw new RuntimeException("Invalid search type");
      }

      Document searchPage = Jsoup.connect(searchUrl).get();
      Element informationTable = searchPage.select("table").get(2);

      Elements rows = informationTable.select("tr:gt(0)");
      List<Map<String, String>> outputData = new ArrayList<>();

      for (Element row : rows) {
        Elements columns = row.select("td");
        Map<String, String> rowData = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
          Element column = columns.get(i);
          if (column.select("a").size() > 0 && column.select("a").attr("title").length() > 0) {
            rowData.put(SearchRequest.colNames[i], column.select("a").attr("href"));
          } else {
            rowData.put(SearchRequest.colNames[i], column.text());
          }
        }
        outputData.add(rowData);
      }
      return outputData;
    } catch (IOException e) {
      throw new RuntimeException("Failed to get search results");
    }
  }

  private static final String[] colNames = {
      "ID",
      "Author",
      "Title",
      "Publisher",
      "Year",
      "Pages",
      "Language",
      "Size",
      "Extension",
      "Mirror_1",
      "Mirror_2",
      "Mirror_3",
      "Mirror_4",
      "Mirror_5",
      "Edit"
  };
}
