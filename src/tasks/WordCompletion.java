package tasks;

import java.util.*;

import helper.TrieNode;

public class WordCompletion {
    // Static HashMap to store the dictionary of words
    private static Map<String, Integer> dictionary = new HashMap<>();
    // TrieNode to store the words in the dictionary
    private static TrieNode trie = new TrieNode();

    public static Map<String, List<String>> sendWordCompletion(String input) {
        // Split the input string into an array of words and search for suggestions for
        // each word
        String[] words = input.split(" ");
        Map<String, List<String>> wordSuggestions = new HashMap<>();
        for (String word : words) {
            // Trim any whitespace around the word
            word = word.trim();

            // Search for all words in the Trie that start with the user's input
            List<String> suggestions = searchTrie(word);

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found for " + input);
            } else {
                System.out.println("Suggestions for " + word + ":");
                for (String suggestion : suggestions) {
                    System.out.println(suggestion);
                }
                wordSuggestions.put(word, suggestions);
            }
        }
        return wordSuggestions;
    }

    public static void main(String[] args) {
        // Populate the dictionary and Trie with the words from the arrayli
        populateDictionary();

        // Create a Scanner object to get input from the user
        Scanner scanner = new Scanner(System.in);

        // Ask the user for a comma-separated list of words or prefixes
        System.out.println("Enter a list of words or prefixes to complete: ");
        String input = scanner.nextLine().toLowerCase();

        // Call sendWordCompletion and print the results
        Map<String, List<String>> wordSuggestions = sendWordCompletion(input);
        for (Map.Entry<String, List<String>> entry : wordSuggestions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Close the scanner object
        scanner.close();
    }

    // Method to populate the dictionrds
    private static void populateDictionary() {
        // get arraylit from
        // mauli**************************************(arraylist).grtwordsfuvtion(pareser)
        dictionary.put("apple", 1);
        dictionary.put("tree", 1);
        dictionary.put("trees", 1);
        dictionary.put("grape", 1);
        dictionary.put("kiwi", 1);
        dictionary.put("orange", 1);
        dictionary.put("peach", 1);
        dictionary.put("pear", 1);
        dictionary.put("pineapple", 1);
        dictionary.put("strawberry", 1);

        for (String word : dictionary.keySet()) {
            trie.insert(word);
        }
  

    // Method to populate the dictionary and Trie with words
	private static void populateDictionary() {
		// get arraylit from
		// mauli**************************************(arraylist).grtwordsfuvtion(pareser)
		dictionary.put("apple", 1);
		dictionary.put("tree", 1);
		dictionary.put("trees", 1);
		dictionary.put("grape", 1);
		dictionary.put("kiwi", 1);
		dictionary.put("orange", 1);
		dictionary.put("peach", 1);
		dictionary.put("pear", 1);
		dictionary.put("pineapple", 1);
		dictionary.put("strawberry", 1);

		for (String word : dictionary.keySet()) {
			trie.insert(word);
		}
	}  }

    // Method to search the Trie for words that start with a given prefix
    private static List<String> searchTrie(String prefix) {
        TrieNode currentNode = trie;
        // Find the node corresponding to the last character in the prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int index = c - 'a';
            if (currentNode.children[index] == null) {
                return Collections.emptyList(); // No words in the Trie start with the given prefix
            }
            currentNode = currentNode.children[index];
        }

        // Find all words in the Trie that start with the prefix
        List<String> words = new ArrayList<>();
        findWords(currentNode, prefix, words);
        return words;
    }

    // Method to recursively find all words in the Trie that start with a given
    // prefix
    private static void findWords(TrieNode node, String prefix, List<String> words) {
        // If the node represents the end of a word, add it to the list of suggestions
        if (node.isEndOfWord) {
            words.add(prefix);
        }
        // Recursively find all words that start with the prefix
        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                char c = (char) (i + 'a');
                findWords(node.children[i], prefix + c, words);
            }
        }
    }
}
