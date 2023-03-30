package tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @Author Dhrumil Limbad (110097066)
 * 
 *         Working of the code:
 *         The InvertedIndex class has two instance variables: index and
 *         fileSet.
 *         index is a Map that maintains the mapping between terms and the set
 *         of files that contain the term.
 *         fileSet is a Set that maintains the set of files that have been
 *         indexed.
 * 
 *         The InvertedIndex constructor initializes the index and fileSet
 *         instance variables.
 * 
 *         The indexFiles method takes a folder path as input, reads all the
 *         files in the folder
 *         (that have .html or .htm extensions), and indexes them using the
 *         indexFile method.
 *         The indexFile method reads the contents of each file, tokenizes the
 *         contents into individual words,
 *         and adds the file name to the set of files associated with each word
 *         in the index map.
 * 
 *         The generateInvertedIndex method takes a set of keywords as input,
 *         builds an
 *         inverted index of the files in the specified folder, and returns a
 *         set of files that contain the specified keywords.
 *         It first initializes a new InvertedIndex instance and indexes the
 *         files in the specified folder using the indexFiles method.
 *         It then iterates over each keyword in the input set and retrieves the
 *         set of files associated
 *         with that keyword from the index map. For each file in the retrieved
 *         set, it creates a File object and adds it to the result set.
 * 
 *         The main method is a simple example of how to use the
 *         generateInvertedIndex method.
 *         It creates a set of two keywords ("diet" and "fitness"), calls
 *         generateInvertedIndex with these keywords,
 *         and prints the absolute path of each file that contains at least one
 *         of the keywords.
 * 
 *         Overall, the code efficiently builds an inverted index from a set of
 *         text
 *         documents and provides a simple method for searching the index to
 *         retrieve the files that contain specified keywords.
 */

public class InvertedIndex {

    // private int page = 1;
    // Define a static map to store the index for each word
    private static Map<String, Set<String>> index;
    // Define a set to store all the files in the folder
    private Set<File> fileSet;

    public InvertedIndex() {
        index = new HashMap<>();
        fileSet = new HashSet<>();
    }

    // Method to index all the files in the folder path
    public void indexFiles(String folderPath) {
        // Create a file object for the folder path
        File folder = new File(folderPath);
        // Get all the files in the folder
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm"))) {
                indexFile(file);
            }
        }
    }

    // Method to index a single file
    private void indexFile(File file) {
        // System.out.println(page++ + " " + file.getName());
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            // Read each line of the file
            while ((line = bufferedReader.readLine()) != null) {
                // Iterate through each word
                String[] words = line.split("\\W+");
                for (String word : words) {
                    // Check if the word has length greater than 0
                    if (word.length() > 0) {
                        // Get the set of files for the current word, or create a new set if it doesn't
                        // exist
                        Set<String> files = index.getOrDefault(word, new HashSet<>());
                        // Add the current file to the set of files for the current word
                        files.add(file.getAbsolutePath());
                        // Put the set of files for the current word into the map
                        index.put(word, files);
                    }
                }
            }
            fileSet.add(file);
            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to generate the inverted index for the given set of keywords
    public static Set<File> generateInvertedIndex(Set<String> keywords) {
        InvertedIndex invertedIndex = new InvertedIndex();
        // String folderPath = "C:\\Users\\ASUS\\Downloads\\webpages\\Webpages_project";
        String folderPath = "C:\\Java\\Project_ACC\\Webpages_project\\Webpages_project\\";
        // Index all the files in the folder
        invertedIndex.indexFiles(folderPath);
        // Create a new set to store the files containing the keywords
        Set<File> result = new HashSet<>();
        // Iterate through each keyword
        for (String keyword : keywords) {
            // Get the set of files containing the current keyword
            Set<String> files = index.get(keyword);
            if (files != null) {
                // Iterate through each file in the set of files containing the current keyword
                for (String fileName : files) {
                    // Create a file object for the current file and add it to the set of files
                    // containing the keywords
                    File file = new File(fileName);
                    result.add(file);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Set<String> keywords = new HashSet<>();
        keywords.add("diet");
        keywords.add("fitness");
        Set<File> result = generateInvertedIndex(keywords);
        System.out.println("Files containing the keywords: " + keywords);
        for (File file : result) {
            System.out.println(file.getAbsolutePath());
        }
    }

}