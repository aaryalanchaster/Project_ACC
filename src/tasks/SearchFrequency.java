package tasks;

import java.io.File;
import java.io.IOException;
import java.util.*;

import helper.FrequencyHashMap;
// import helper.FrequencyHashMap.HashMap;

public class SearchFrequency {

    public void processHashMap(String word, FrequencyHashMap frequencyHashMap) {

        if (frequencyHashMap.contains(word)) {
            int frequency = frequencyHashMap.get(word);
            System.out.println("Keyword '" + word + "' found. The word had been searched: " + frequency);
        } else {
            System.out.println("Keyword '" + word + "' not found.");
        }
    }

    public static void main(String args[]) {
        // code
        System.out.print("Enter keywords to search for (comma separated): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] searchwords = input.split(",");

        FrequencyHashMap hashmap1 = new FrequencyHashMap();
        for (String word : searchwords) {
            if (!word.equals("")) {
                int frequency = hashmap1.get(word.toLowerCase());

                if (frequency == -1) {
                    // if not add it into the hashmap
                    hashmap1.insert(word.toLowerCase()); // add the searched word in the hashmap
                } else {
                    hashmap1.insert(word.toLowerCase()); // add the searched word in the hashmap
                }
            }
        }

        System.out.println("Frequency Hash Map:");
        for (FrequencyHashMap.HashMap node : hashmap1.getMap()) {
            while (node != null) {
                System.out.println(node.getKey() + " : " + node.getValue());
                node = node.getNext();
            }
        }

    }
}
