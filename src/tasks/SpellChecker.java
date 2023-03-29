package Task;

import java.util.*;

public class SpellChecker {
    // A TrieNode represents a node in the Trie
    static class TrieNode {
        public Map<Character, TrieNode> child;
        public boolean isWord;

        public TrieNode() {
            this.child = new HashMap<>();
            this.isWord = false;
        }
    }

    private TrieNode root;

    public SpellChecker() {
        this.root = new TrieNode();
    }

    // Adds a word to the Trie
    public void addWord(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!current.child.containsKey(c)) {
                current.child.put(c, new TrieNode());
            }
            current = current.child.get(c);
        }
        current.isWord = true;
    }

    // Checks if a word is spelled correctly
    public boolean checkSpelling(String word) {
        word = word.toLowerCase();
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!current.child.containsKey(c)) {
                // Check if the word can be corrected with a single edit
                return checkSingleEdit(word.substring(0, i) + word.substring(i + 1)) ||
                        checkSingleEdit(word.substring(0, i) + c + word.substring(i + 1)) ||
                        checkSingleEdit(word.substring(0, i + 1) + word.substring(i + 2));
            }
            current = current.child.get(c);
        }
        return current.isWord;
    }

    // Checks if a word can be corrected with a single edit
    private boolean checkSingleEdit(String word) {
        // Check if the word is in the Trie
        if (checkSpelling(word)) {
            return true;
        }
        // Check if the word can be corrected with a transposition
        for (int i = 0; i < word.length() - 1; i++) {
            String transposedWord = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2);
            if (checkSpelling(transposedWord)) {
                return true;
            }
        }
        // Check if the word can be corrected with a deletion, insertion, or
        // substitution
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String editedWord = word.substring(0, i) + c + word.substring(i + 1);
                if (checkSpelling(editedWord)) {
                    return true;
                }
            }
            String editedWord = word.substring(0, i) + word.substring(i + 1);
            if (checkSpelling(editedWord)) {
                return true;
            }
        }
        return false;
    }
}
