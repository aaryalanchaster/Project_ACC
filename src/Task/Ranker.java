package tasks;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Ranker {

    public static void main(String[] args) throws IOException {
        dhrumilinverted invertedIndex1 = new dhrumilinverted();
        Map<String, Map<String, Integer>> keywordCountMap = new HashMap<>();
      
        // Set the path of the folder containing the txt files to be indexed
        String folderPath = "C:\\Users\\johna\\Downloads\\Webpages_project\\Webpages_project";
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter keywords to search for (comma separated): ");
            String[] keywords = scanner.nextLine().split(",");
            // File folder = new File(folderPath);
            //takes all the files and adds them in the files array
            File[] files = new File(folderPath).listFiles();

            // For each file, read its content and add each word to the inverted index along with the filename
            for (File file : files) {
                // Check if the file is an HTML file
                if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm") )) {
                    try {
                        // Parse the HTML file using Jsoup
                        Document doc = Jsoup.parse(file, "UTF-8");

                        // Extract the text from the HTML document
                        String text = doc.text();

                        // Split the text into words and add them to the inverted index
                        String[] words = text.split("\\W+");
                        		
                        for (String word : words) {
                            invertedIndex1.add(word, file.getName());
                            for (String keyword : keywords) {
                                if (word.equalsIgnoreCase(keyword)) {
                                    Map<String, Integer> keywordCount = keywordCountMap.getOrDefault(keyword, new HashMap<>());
                                    keywordCount.put(file.getName(), keywordCount.getOrDefault(file.getName(), 0) + 1);
                                    keywordCountMap.put(keyword, keywordCount);
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error parsing file " + file.getName());
                    }
                }
            }
            List<String> filenames = new ArrayList<>();
            for (String keyword : keywords) {
                filenames.addAll(invertedIndex1.search(keyword));
            }
            System.out.println("Files containing the keywords \"" + String.join(", ", keywords) + "\":");
            for (String filename : sortByValue(keywordCountMap.get(keywords[0])).keySet()) {
                boolean containsAllKeywords = true;
                for (int i = 1; i < keywords.length; i++) {
                    if (!keywordCountMap.get(keywords[i]).containsKey(filename)) {
                        containsAllKeywords = false;
                        break;
                    }
                }
                if (containsAllKeywords) {
                    System.out.print(filename + " (");
                    for (int i = 0; i < keywords.length; i++) {
                        System.out.print(keywords[i] + ": " + keywordCountMap.get(keywords[i]).getOrDefault(filename, 0));
                        if (i < keywords.length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println(")");
                }
            }
        }
    }
    
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}

