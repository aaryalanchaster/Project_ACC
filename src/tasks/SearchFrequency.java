package tasks;

import java.util.*;
import helper.FrequencyHashMap;;

public class SearchFrequency {

    public static Map<String, Integer> processHashMap(Set<String> input, FrequencyHashMap frequencyHashMap) {
        Map<String, Integer> wordFrequencies = new HashMap<>();
        for (String word : input) {
            if (frequencyHashMap.contains(word)) {
                int frequency = frequencyHashMap.get(word);
                // System.out.println("Keyword '" + word + "' found. The word had been searched:
                // " + frequency);
                wordFrequencies.put(word, frequency);
            } else {
                // System.out.println("Keyword '" + word + "' not found.");
                wordFrequencies.put(word, 0);
            }
        }
        return wordFrequencies;
    }

    public static Map<String, Integer> recordSearchFrequency(Set<String> input) {
        FrequencyHashMap frequencyHashMap = new FrequencyHashMap();
        for (String word : input) {
            if (!word.equals("")) {
                int frequency = frequencyHashMap.get(word.toLowerCase());
                if (frequency == -1) {
                    // if not add it into the hashmap
                    frequencyHashMap.insert(word.toLowerCase()); // add the searched word in the hashmap
                } else {
                    frequencyHashMap.insert(word.toLowerCase()); // add the searched word in the hashmap
                }
            }
        }

        Map<String, Integer> wordFrequencies = processHashMap(input, frequencyHashMap);

        // for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
        // System.out.println(entry.getKey() + ": " + entry.getValue());
        // }

        return wordFrequencies;
    }

    public static void main(String args[]) {
        Set<String> queryTokens = new HashSet<>();
        queryTokens.add("tree");
        queryTokens.add("search");
        queryTokens.add("try");
        recordSearchFrequency(queryTokens);
    }
}
