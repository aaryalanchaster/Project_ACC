

package tasks;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Aishwarya_John_Page_Ranking {
    public static void main(String[] args) throws IOException {
        // Set the path of the folder containing the txt files to be indexed
        String folderPath = "C:\\Users\\johna\\Downloads\\Webpages_project\\Webpages_project";

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter keywords to search for (comma separated): ");
            String[] keywords = scanner.nextLine().split(",");
            // File folder = new File(folderPath);
            //takes all the files and adds them in the files array
            File[] files = new File(folderPath).listFiles();

            // Create a priority queue to store the top 10 most relevant files
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // For each file, read its content and calculate its relevance score
            for (File file : files) {
                // Check if the file is an HTML file
                if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm") )) {
                    // Count the frequency of keywords in the file
                    Map<String, Integer> wordFrequency = countWordFrequency(file);

                    // Calculate the relevance score based on the keyword frequency
                    int relevanceScore = 0;
                    for (String keyword : keywords) {
                        relevanceScore += wordFrequency.getOrDefault(keyword.toLowerCase(), 0);
                    }

                    // Add the file and its relevance score to the priority queue
                    if (relevanceScore > 0) {
                        pq.offer(new AbstractMap.SimpleEntry<>(file.getName(), relevanceScore));
                    }
                }
            }

            // Print the top 10 most relevant files
            int count = 0;
            while (!pq.isEmpty()) {
                Map.Entry<String, Integer> entry = pq.poll();
                System.out.println(entry.getKey() + ": " + entry.getValue());
                count++;
            }
        }
    }

    private static Map<String, Integer> countWordFrequency(File file) throws IOException {
        Map<String, Integer> wordFrequency = new HashMap<>();

        // Parse the HTML file using Jsoup
        Document doc = Jsoup.parse(file, "UTF-8");

        // Extract the text from the HTML document
        String text = doc.text();

        // Split the text into words and calculate the frequency of each word
        String[] words = text.split("\\W+");
        for (String word : words) {
            wordFrequency.put(word.toLowerCase(), wordFrequency.getOrDefault(word.toLowerCase(), 0) + 1);
        }

        return wordFrequency;
    }
}
