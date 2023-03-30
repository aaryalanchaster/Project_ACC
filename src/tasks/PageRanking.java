
package tasks;

import java.io.File;
import java.io.IOException;
import java.util.*;

// @Author Aishwarya John

public class PageRanking {

    private static int page;

    public static void rank(Map<String, Integer> searchResults) {
        // Create a priority queue to store the top 10 most relevant files
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : searchResults.entrySet()) {
            pq.offer(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
        }

        page = 1;

        // Print Top 10 Results
        System.out.println("\nTop 10 Search Results:");
        int count = 0;// initialize count to 0
        while (!pq.isEmpty()) { // search if the queue is empty
            Map.Entry<String, Integer> entry = pq.poll(); // remove the head of the queue
            System.out.println(page++ + " " + entry.getKey() + ": " + entry.getValue());
            count++;
            if (count == 10)
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        // Set the path of the folder containing the txt files to be indexed
        // String folderPath =
        // "C:\\Users\\johna\\Downloads\\Webpages_project\\Webpages_project";
        String folderPath = "C:\\Java\\Project_ACC\\Webpages_project\\Webpages_project";// folder path

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter keywords to search for (comma separated): ");// take keyword as input from user
            String[] searchwords = scanner.nextLine().split(",");
            List<String> keywords = new ArrayList<String>();// create arraylist to store keywords
            for (String word : searchwords)
                keywords.add(word.toLowerCase().trim());// add keywords in lowercase to the arraylist

            // File folder = new File(folderPath);
            // takes all the files and adds them in the files array
            File[] files = new File(folderPath).listFiles();
            // Create a priority queue to store the top 10 most relevant files
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                    Map.Entry.comparingByValue(Comparator.reverseOrder()));// call the comparator

            // For each file, read its content and calculate its relevance score
            System.out.println("[INFO] Searching...\n");
            for (File file : files) {
                // Check if the file is an HTML file
                if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm"))) {
                    // Compute the relevance score based on frequency of keywords in the files

                    int relevanceScore = 0;

                    if (relevanceScore > 0) {
                        pq.offer(new AbstractMap.SimpleEntry<>(file.getName(), relevanceScore));// add filename with the
                                                                                                // score into the
                                                                                                // priority queue
                    }
                }
            }

        }
    }
}
