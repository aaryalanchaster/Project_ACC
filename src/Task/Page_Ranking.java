
package Task;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Page_Ranking {

    private static int page;

    private static int computeRelevanceScore(File file, List<String> keywords) throws IOException {
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

    public static void main(String[] args) throws IOException {
        // Set the path of the folder containing the txt files to be indexed
        // String folderPath =
        // "C:\\Users\\johna\\Downloads\\Webpages_project\\Webpages_project";
        String folderPath = "C:\\Java\\Project_ACC\\Webpages_project\\Webpages_project";

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter keywords to search for (comma separated): ");
            String[] searchwords = scanner.nextLine().split(",");
            List<String> keywords = new ArrayList<String>();
            for (String word : searchwords)
                keywords.add(word.toLowerCase().trim());

            // File folder = new File(folderPath);
            // takes all the files and adds them in the files array
            File[] files = new File(folderPath).listFiles();
            // Create a priority queue to store the top 10 most relevant files
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                    Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // For each file, read its content and calculate its relevance score
            System.out.println("[INFO] Searching...\n");
            for (File file : files) {
                // Check if the file is an HTML file
                if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm"))) {
                    // Compute the relevance score based on frequency of keywords in the files
                    int relevanceScore = computeRelevanceScore(file, keywords);

                    // Add the file and its relevance score to the priority queue
                    if (relevanceScore > 0) {
                        pq.offer(new AbstractMap.SimpleEntry<>(file.getName(), relevanceScore));
                    }
                }
            }

            // Print the top 10 most relevant files
            page = 1;
            System.out.println("\n\nTop 10 Search Results:\n");
            int count = 0;
            while (!pq.isEmpty()) {
                Map.Entry<String, Integer> entry = pq.poll();
                System.out.println(page++ + " " + entry.getKey() + ": " + entry.getValue());
                count++;
                if (count == 10)
                    break;
            }
        }
    }
}
