package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import helper.Parser;

public class SpellChecker {
    // The root node of the trie
    private static Node root = new Node();

    // The maximum edit distance to be considered
    private static final int MAX_DISTANCE = 1;

    // A TrieNode represents a node in the trie
    private static class Node {
        // Maps each character to the child node
        public Map<Character, Node> child;
        // Indicates whether this node is the end of a word
        public boolean isWord;

        public Node() {
            this.child = new HashMap<>();
            this.isWord = false;
        }
    }

    public SpellChecker() {
    }

    // Adds a word to the trie
    public static void addWord(String word) {
        word = word.toUpperCase();
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!current.child.containsKey(c)) {
                current.child.put(c, new Node());
            }
            current = current.child.get(c);
        }
        current.isWord = true;
    }

    public static void makeDictionary() {
        ArrayList<String> words = Parser.getWords();
        // System.out.println(words);
        for (String word : words) {
            addWord(word);
        }
    }

    // Returns a list of suggestions for the given word
    public static List<String> suggest(String word) {
        word = word.toUpperCase();
        List<String> suggestions = new ArrayList<>();
        Set<String> suggestedWords = new HashSet<>(); // to keep track of suggested words
        // Check if the word is already in the trie
        if (search(root, word)) {
            return suggestions;
        }
        search(root, word, suggestions, "", 0, suggestedWords);
        return suggestions;
    }

    // Recursively searches the trie for suggestions for the given word
    private static boolean search(Node node, String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!node.child.containsKey(c)) {
                return false;
            }
            node = node.child.get(c);
        }
        return node.isWord;
    }

    // Recursively searches the trie for suggestions for the given word
    private static void search(Node node, String word, List<String> suggestions, String candidate, int distance,
            Set<String> suggestedWords) {
        if (distance > MAX_DISTANCE) {
            return;
        }
        if (node.isWord && distance <= MAX_DISTANCE && !suggestedWords.contains(candidate)) {
            suggestions.add(candidate);
            suggestedWords.add(candidate); // add the word to the set of suggested words
        }
        for (Map.Entry<Character, Node> entry : node.child.entrySet()) {
            char c = entry.getKey();
            Node child = entry.getValue();
            // deletion
            search(child, word, suggestions, candidate + c, distance + 1, suggestedWords);
            // substitution
            search(child, word, suggestions, candidate + c, distance + 1, suggestedWords);
            // insertion
            search(child, word, suggestions, candidate + c, distance + 1, suggestedWords);
            if (word.length() > 1) {
                // transposition
                char[] chars = candidate.toCharArray();
                if (chars.length > 1 && chars[chars.length - 1] == word.charAt(word.length() - 2)
                        && chars[chars.length - 2] == word.charAt(word.length() - 1)) {
                    chars[chars.length - 1] = word.charAt(word.length() - 1);
                    chars[chars.length - 2] = word.charAt(word.length() - 2);
                    search(child, word, suggestions, new String(chars), distance + 1, suggestedWords);
                }
            }
            // matching
            if (!word.isEmpty() && c == word.charAt(0)) {
                search(child, word.substring(1), suggestions, candidate + c, distance, suggestedWords);
            }
            //
        }
    }

    public static Set<String> extrapolateWords(Set<String> keywords) {
        makeDictionary();
        Set<String> result = new HashSet<String>();
        for (String keyword : keywords) {
            List<String> lst = suggest(keyword);
            // System.out.println(keyword + lst);
            Map<String, List<String>> wc = WordCompletion.sendWordCompletion(keyword);
            List<String> wclst = wc.get(keyword);
            result.addAll(lst);
            if (wclst != null)

                result.addAll(wclst);

        }
        // System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        // SpellChecker checker = new SpellChecker();
        // List<String> words = Arrays.asList("apple", "banana", "cherry", "dog", "cat",
        // "elephant", "friend", "hello");
        // for (String word : words) {
        // checker.addWord(word);
        // }
        SpellChecker.makeDictionary();
        // List<String> inputWords = Arrays.asList("canda");
        Set<String> s = new HashSet<String>();
        s.add("canda");
        s.add("fit");
        s.add("fitn");
        s.add("fat");
        Set<String> s1 = SpellChecker.extrapolateWords(s);
        System.out.println(s1);
        // for (String inputWord : inputWords) {
        // List<String> wordSuggestions = suggest(inputWord);
        // if (wordSuggestions.isEmpty()) {
        // continue;
        // } else {
        // System.out.println("Suggestions for " + inputWord + ":");
        // for (String suggestion : wordSuggestions) {
        // System.out.println("- " + suggestion);
        // }
        // System.out.println();
        // }
        // }
    }
}