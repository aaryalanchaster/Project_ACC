package Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class dhrumilinverted {
    // A TrieNode represents a node in the trie
    static class Node {
        // Maps each character to the child node
        public Map<Character, Node> child;
        // The number of times this node has been visited during insertion
        public int count;
        // A set of filenames where this word occurs
        public Set<String> files;

        public Node() {
            this.child = new HashMap<>();
            this.count = 0;
            this.files = new HashSet<>();
        }

        // Adds the given filename to the set of filenames where this word occurs
        public void addFile(String filename) {
            files.add(filename);
        }

        // Returns a list of filenames where this word occurs
        public List<String> getFiles() {
            return new ArrayList<String>(files);
        }
    }

    // The root node of the trie
    private Node root;

    public dhrumilinverted() {
        this.root = new Node();
    }

    // Adds the given word and filename to the trie
    public void add(String word, String filename) {
        word = word.toLowerCase();
        Node current = root;
        current.count++;
        for (int i = 0; i < word.length(); i++) {
            // gets the word at position i
            char c = word.charAt(i);
            // checks whether the c exist or not
            if (!current.child.containsKey(c)) {
                current.child.put(c, new Node());
            }
            current = current.child.get(c);
            current.count++;
        }
        // adds the file name
        current.addFile(filename);
    }

    // Returns a list of filenames where the given word occurs
    public List<String> search(String word) {
        word = word.toLowerCase();
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!curr.child.containsKey(c)) {
                // Return an empty list if the word is not found in the trie
                return Collections.emptyList();
            }
            curr = curr.child.get(c);
        }
        // gets the files
        return curr.getFiles();
    }

    // public List<File> search(String word) {
    // word = word.toLowerCase();
    // Node curr = root;
    // for (int i = 0; i < word.length(); i++) {
    // char c = word.charAt(i);
    // if (!curr.child.containsKey(c)) {
    // // Return an empty list if the word is not found in the trie
    // return Collections.emptyList();
    // }
    // curr = curr.child.get(c);
    // }
    // // Get the list of files for the current node
    // List<String> fileNames = curr.getFiles();
    // List<File> files = new ArrayList<>();
    // // Convert the list of file names to a list of File objects
    // for (String fileName : fileNames) {
    // File file = new File(fileName);
    // files.add(file);
    // }
    // return files;
    // }

    // Returns the number of times the given word occurs in the trie
    public int count(String word) {
        word = word.toLowerCase();
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!curr.child.containsKey(c)) {
                return 0;
            }
            curr = curr.child.get(c);
        }
        // gets the count of the word
        return curr.count;
    }

    // Prints the list of filenames where each keyword occurs
    public void print(String[] keywords) {
        for (String word : keywords) {
            List<String> files = search(word);
            System.out.println(word + " - " + "found in " + files.size() + " files");
            for (String file : files) {
                // prints the list of files which have the keyword
                System.out.println("\t" + file);
            }
        }
    }

    /**
     * 
     * @param file
     * @return void
     * @description Method to convert html file to txt file
     */
    public static void getfiles(File file) {
        try {
            // gets the file name
            String fileName = file.getName();
            // gets the file extension
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
            if (!fileExtension.equalsIgnoreCase("htm") && !fileExtension.equalsIgnoreCase("html")) {
                // If the file is not of html or htm type, then return without processing
                // further and prints the error message
                // System.out.println("The added files are not html/htm files.Please add files
                // with proper exttention");
                return;
            }
            String txtFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".txt";

            File txtFile = new File(file.getParentFile(), txtFileName);
            Document doc;
            doc = Jsoup.parse(file, "UTF-8");
            FileWriter writer = new FileWriter(txtFile);

            writer.write(doc.text());
            // closing the file writer
            writer.close();
            // Print a success message after the file is converted to txt format
            // System.out.println("Successfully converted " + fileName + " to " +
            // txtFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param path
     * @return void
     * @description Method to get all the html files in a folder and convert them to
     *              txt files
     */
    public static void getfilesfromfolder(File path) {
        // takes all the files and adds them in the files array
        File[] files = path.listFiles();
        // traverses through all the files and calls getfiles method
        for (File file : files) {
            getfiles(file);
        }
    }

    public static void main(String[] args) throws Exception {
        // Directory containing the files to be indexed
        File htmlFile = new File("C:\\\\Users\\\\johna\\\\Downloads\\\\Webpages_project\\\\Webpages_project");
        // Convert all html files in the directory to txt format
        getfilesfromfolder(htmlFile);
        // Set the path of the folder containing the txt files to be indexed
        String folderPath = "C:\\\\Users\\\\johna\\\\Downloads\\\\Webpages_project\\\\Webpages_project";
        // Set the keywords to be searched for in the indexed files
        String[] keywords = { "fitness" };

        // Create an object of the InvertedIndex class
        dhrumilinverted invertedIndex = new dhrumilinverted();

        // Get all the txt files in the folder to be indexed
        File folder = new File(folderPath);
        // takes all the files and adds them in the files array
        File[] files = folder.listFiles();

        // For each file, read its content and add each word to the inverted index along
        // with the filename
        for (File file : files) {
            // checks where the file ends with "txt" extension
            if (file.isFile() && file.getName().endsWith(".txt")) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    // (\\W) stands for "word character"
                    // (+) means that all this together can be repeated at least once
                    String[] words = line.split("\\W+");
                    for (String word : words) {
                        // adds the word and the file name in the inverted index
                        invertedIndex.add(word, file.getName());
                    }
                }
                // closing the buffered reader
                reader.close();
            }
        }

        // Print the filenames where the keywords are found
        invertedIndex.print(keywords);
    }
}