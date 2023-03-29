package exec;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;

import helper.Parser;
import tasks.InvertedIndex;
import tasks.PageRanking;
import tasks.RelevanceFrequency;
// import tasks.SpellChecker;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean close = false;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("OPTION: ");
            String option = sc.nextLine();
            System.out.print("Q: ");
            String query = sc.nextLine();
            // sc.close();
            switch (option) {
                case "1":
                    System.out.println("[INFO] Parsing...");
                    Set<String> keywords = Parser.filterStopWords(query);
                    // spellchecker and word completion with suggestions
                    // keywords = SpellChecker.extrapolateWords(keywords);
                    System.out.println("[INFO] Building Inverted Index...");
                    Set<File> lookup = InvertedIndex.generateInvertedIndex(keywords);
                    System.out.println("[INFO] Searching For Results...");
                    Map<String, Integer> searchResults = RelevanceFrequency.computeRelevanceMap(lookup, keywords);
                    System.out.println("[INFO] Ranking Results...");
                    PageRanking.rank(searchResults);
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
