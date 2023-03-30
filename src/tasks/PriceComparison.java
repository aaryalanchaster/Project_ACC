package tasks;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
    @Author Dhrumil Limbad
 */

public class PriceComparison {

    public static void comparator() {
        // Create a HashMap to store the prices
        Map<String, BigDecimal> prices = new HashMap<>();

        // Add the prices from HTML files to the HashMap
        File[] htmlFiles = new File("C:\\Java\\Project_ACC\\membershippages").listFiles();
        for (File file : htmlFiles) {
            try {
                Document doc = Jsoup.parse(file, "UTF-8");
                Elements priceElement = doc.select(".PRICE");
                if (priceElement != null) {
                    String priceString = priceElement.text().replaceAll("[^\\d.]+", "");
                    BigDecimal price = new BigDecimal(priceString);
                    prices.put(file.getName(), price);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Sort the prices in ascending order
        prices.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEachOrdered(entry -> System.out.println("$" + entry.getValue() + " - " + entry.getKey()));
    }
}
