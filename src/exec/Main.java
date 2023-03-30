package exec;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

import helper.Parser;
import tasks.InvertedIndex;
import tasks.PageRanking;
import tasks.RelevanceFrequency;
import tasks.SpellChecker;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean close = false;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("OPTION [1]: Make a search query");
            System.out.println("OPTION [2]: Do a price comparison");
            System.out.println("OPTION [0]: EXIT");
            System.out.print(": ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    System.out.print("[ENTER YOUR QUERY]: ");
                    String query = sc.nextLine();
                    System.out.println("[INFO] Parsing Query...");
                    Set<String> queryTokens = Parser.filterStopWords(query);
                    // spellchecker and word completion with suggestions
                    System.out.println("[INFO] Augmenting Query...");
                    Set<String> suggestions = SpellChecker.extrapolateWords(queryTokens);
                    queryTokens.addAll(suggestions);
                    Set<String> keywords = new HashSet<String>();
                    for (String word : queryTokens) {
                        keywords.add(word.toLowerCase().trim());
                    }
                    System.out.println("[INFO] KEYWORDS " + keywords);
                    System.out.println("[INFO] Building Inverted Index...");
                    Set<File> lookup = InvertedIndex.generateInvertedIndex(keywords);
                    System.out.println("[INFO] Searching For Results...");
                    Map<String, Integer> searchResults = RelevanceFrequency.computeRelevanceMap(lookup, keywords);
                    System.out.println("[INFO] Ranking Results...");
                    PageRanking.rank(searchResults);
                    System.out.println(
                            "__________________________________________________________________________________");
                    break;
                case "2":
                    break;
                case "0":
                    close = true;
                    break;
            }
            if (close)
                break;
        }
        sc.close();
    }
}
