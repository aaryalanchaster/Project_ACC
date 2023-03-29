package tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class RelevanceFrequency {

    private static int computeRelevanceScore(File file, Set<String> keywords) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");
        String text = doc.text();
        int relevance = 0;
        // Split the text into words and calculate the frequency of each word
        String[] words = text.split("\\W+");
        for (String word : words)
            if (keywords.contains(word.toLowerCase()))
                relevance++;
        // System.out.printf("%d %s %d\n", page++, file.getName(), relevance);
        return relevance;
    }

    public static Map<String, Integer> computeRelevanceMap(Set<File> files, Set<String> keywords) throws IOException {
        Map<String, Integer> relevanceMap = new HashMap<String, Integer>();
        for (File file : files) {
            // Check if the file is an HTML file
            if ((file.getName().endsWith(".html") || file.getName().endsWith(".htm"))) {
                // Compute the relevance score based on frequency of keywords in the files
                int relevanceScore = computeRelevanceScore(file, keywords);

                // Add the file and its relevance score to the priority queue
                if (relevanceScore > 0) {
                    relevanceMap.put(file.getName(), relevanceScore);
                }
            }
        }
        return relevanceMap;
    }
}
