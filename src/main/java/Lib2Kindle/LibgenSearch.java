package Lib2Kindle;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibgenSearch {
  private static final String[] MIRROR_SOURCES = {"GET", "Cloudflare", "IPFS.io", "Infura"};
  private Gson gson = new Gson();

  public List<Map<String, String>> searchTitle(String query) throws IOException {
    SearchRequest searchRequest = new SearchRequest(query, "title");
    return searchRequest.aggregateRequestData();
  }

  public List<Map<String, String>> searchAuthor(String query) throws IOException {
    SearchRequest searchRequest = new SearchRequest(query, "author");
    return searchRequest.aggregateRequestData();
  }

  public List<Map<String, String>> searchTitleFiltered(String query, Map<String, String> filters, boolean exactMatch) throws IOException {
    SearchRequest searchRequest = new SearchRequest(query, "title");
    List<Map<String, String>> results = searchRequest.aggregateRequestData();
    List<Map<String, String>> filteredResults = filterResults(results, filters, exactMatch);
    return filteredResults;
  }

  public List<Map<String, String>> searchAuthorFiltered(String query, Map<String, String> filters, boolean exactMatch) throws IOException {
    SearchRequest searchRequest = new SearchRequest(query, "author");
    List<Map<String, String>> results = searchRequest.aggregateRequestData();
    List<Map<String, String>> filteredResults = filterResults(results, filters, exactMatch);
    return filteredResults;
  }

  public Map<String, String> resolveDownloadLinks(Map<String, String> item) throws IOException {
    String mirror1 = item.get("Mirror_1");
    Document doc = Jsoup.connect(mirror1).get();
    Map<String, String> downloadLinks = new HashMap<>();
    for (String source : MIRROR_SOURCES) {
      Element link = doc.selectFirst("a[title=" + source + "]");
      if (link != null) {
        String href = link.attr("href");
        downloadLinks.put(source, href);
      }
    }
    return downloadLinks;
  }

  public List<Map<String, String>> filterResults(List<Map<String, String>> results, Map<String, String> filters, boolean exactMatch) {
    List<Map<String, String>> filteredList = new ArrayList<>();

    if (exactMatch) {
      for (Map<String, String> result : results) {
        boolean match = true;
        for (Map.Entry<String, String> entry : filters.entrySet()) {
          if (!result.containsKey(entry.getKey()) || !result.get(entry.getKey()).equals(entry.getValue())) {
            match = false;
            break;
          }
        }
        if (match) {
          filteredList.add(result);
        }
      }
    } else {
      for (Map<String, String> result : results) {
        boolean match = true;
        for (Map.Entry<String, String> entry : filters.entrySet()) {
          if ((!result.containsKey(entry.getKey()) || !(result.get(entry.getKey()).toLowerCase().indexOf(entry.getValue().toLowerCase()) >= 0))) {
            match = false;
            break;
          }
        }
        if (match) {
          filteredList.add(result);
        }
      }
    }

    return filteredList;
  }

}
