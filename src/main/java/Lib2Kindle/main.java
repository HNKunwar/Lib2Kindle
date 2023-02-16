package Lib2Kindle;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;

public class main {
  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to LibGen Search!");
    System.out.print("Enter your search query: ");
    String query = scanner.nextLine().trim();

    System.out.println("Select search field:");
    System.out.println("1. ID");
    System.out.println("2. Author");
    System.out.println("3. Title");
    System.out.println("4. Publisher");
    System.out.println("5. Year");
    System.out.println("6. Pages");
    System.out.println("7. Language");
    System.out.println("8. Size");
    System.out.println("9. Extension");
    System.out.print("Enter search field number: ");
    int fieldNum = scanner.nextInt();
    scanner.nextLine(); // consume the \n character

    String field;
    switch (fieldNum) {
      case 1:
        field = "ID";
        break;
      case 2:
        field = "Author";
        break;
      case 3:
        field = "Title";
        break;
      case 4:
        field = "Publisher";
        break;
      case 5:
        field = "Year";
        break;
      case 6:
        field = "Pages";
        break;
      case 7:
        field = "Language";
        break;
      case 8:
        field = "Size";
        break;
      case 9:
        field = "Extension";
        break;
      default:
        System.out.println("Invalid search field number.");
        return;
    }

    LibgenSearch libgenSearch = new LibgenSearch();
    List<Map<String, String>> results = libgenSearch.searchTitle(query);

    // Filter the results based on the selected field
    List<Map<String, String>> filteredResults = new ArrayList<>();
    for (Map<String, String> result : results) {
      if (result.get(field).toLowerCase().contains(query.toLowerCase())) {
        filteredResults.add(result);
      }
    }

    // Print the filtered results
    System.out.println("Search Results:");
    for (Map<String, String> result : filteredResults) {
      String output = String.format(
          "Title: %s\nAuthor: %s\nYear: %s\nPages: %s\nLanguage: %s\nSize: %s\nLink: %s",
          result.get("Title"),
          result.get("Author"),
          result.get("Year"),
          result.get("Pages"),
          result.get("Language"),
          result.get("Size"),
          result.get("Mirror_1")
      );
      System.out.println(output + "\n");
    }
  }
}

