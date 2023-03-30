package helper;

public class TrieNode {
    // An array to store the TrieNode children for each character in the alphabet
    public TrieNode[] children;
    // A boolean flag to indicate whether the node represents the end of a word
    public boolean isEndOfWord;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray(); // Array of characters

    // Constructor to initialize the TrieNode with an empty children array and
    // isEndOfWord set to false
    public TrieNode() {
        this.children = new TrieNode[CHARACTERS.length];
        this.isEndOfWord = false;
    }

    // Method to insert a word into the Trie
    public void insert(String word) {
        TrieNode currentNode = this;
        // Iterate over each character in the word and create a new TrieNode if
        // necessary
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a'; // Determine the index in the children array based on the character's Unicode
                                 // value
            // System.out.println("c= " +c);
            if (index < 0 || index >= currentNode.children.length) {
                // Handle invalid index values
                throw new IllegalArgumentException("Invalid index value: " + index);
            }
            if (currentNode.children[index] == null) {
                currentNode.children[index] = new TrieNode();
            }

            // Move to the next node in the Trie
            currentNode = currentNode.children[index];
        }
        // Mark the last node as the end of a word
        currentNode.isEndOfWord = true;
    }

    // Method to search for a word in the Trie
    public boolean search(String word) {
        TrieNode currentNode = this;
        // Iterate over each character in the word and check if it exists in the Trie
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a'; // Determine the index in the children array based on the character
            if (currentNode.children[index] == null) {
                return false;
            }
            // Move to the next node in the Trie
            currentNode = currentNode.children[index];
        }
        // If the last node represents the end of a word, the word is found
        return currentNode.isEndOfWord;
    }

    // Method to check if any word in the Trie starts with a given prefix
    public boolean startsWith(String prefix) {
        TrieNode currentNode = this;
        // Iterate over each character in the prefix and check if it exists in the Trie
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int index = c - 'a'; // Determine the index in the children array based on the character
            if (currentNode.children[index] == null) {
                return false;
            }
            // Move to the next node in the Trie
            currentNode = currentNode.children[index];
        }
        // If we've reached this point, the prefix exists in the Trie
        return true;
    }

    public void printTrie() {
        printTrieHelper(this, "");
    }

    private void printTrieHelper(TrieNode currentNode, String prefix) {
        // If the current node represents the end of a word, print the word
        if (currentNode.isEndOfWord) {
            System.out.println(prefix);
        }
        // Iterate over all possible child nodes and recursively call the
        // printTrieHelper method
        for (int i = 0; i < currentNode.children.length; i++) {
            if (currentNode.children[i] != null) {
                char c = (char) (i + 'a');
                printTrieHelper(currentNode.children[i], prefix + c);
            }
        }
    }

}
