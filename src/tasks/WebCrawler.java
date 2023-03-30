// package tasks;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.ie.InternetExplorerDriver;

// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayDeque;
// import java.util.List;
// import java.util.Queue;
// import java.util.concurrent.TimeUnit;

// public class WebCrawler {

// public static void main(String[] args) throws InterruptedException {
// System.setProperty("webdriver.ie.driver",
// "C:\\Users\\johna\\IEDriverServer.exe"); // Set the path to the
// // InternetExplorer driver
// // .exe file
// InternetExplorerDriver drv = new InternetExplorerDriver(); // Create a new
// InternetExplorerDriver instance

// drv.get("https://www.fitnessavenue.ca"); // launch the given website
// System.out.println("Opened website: " + drv.getCurrentUrl()); // connect to
// the url
// drv.manage().window().maximize(); // Wait for the page to load

// Queue<String> pageUrls = new ArrayDeque<>(); // create a queue to store all
// the links found on the website
// pageUrls.offer(drv.getCurrentUrl()); // add to the queue the current URL of
// the webpage
// System.out.println("Added " + drv.getCurrentUrl() + " to pageUrls");

// // Specify the path to save the HTML files
// String fp = "C:\\Users\\johna\\eclipse-workspace\\ACC_Assignment_1\\src"; //
// ouput file path to store the
// // downloaded HTML pages
// System.out.println("Using path: " + fp);

// // Create the directory if it doesn't exist
// File directory = new File(fp);
// if (!directory.exists()) {
// directory.mkdirs();
// }

// // BFS algorithm to visit all pages on the website
// while (!pageUrls.isEmpty()) { // checks if the queue is not empty
// String url = pageUrls.poll(); // removes element at the head of the queue
// if (!url.startsWith("https://www.fitnessavenue.ca")) {
// continue; // skip external links
// }
// drv.get(url);
// System.out.println("Visited: " + url);

// // Check if the page contains price information
// List<WebElement> prices = drv.findElements(By.xpath(
// "//*[contains(text(),'$') or contains(@class,'price') or
// contains(@class,'Price') or contains(@class,'PRICE')]"));
// if (prices.isEmpty()) {
// continue; // skip pages without price information
// }

// String pageTitle = drv.getTitle(); // gets title of current webpage
// String filename = pageTitle.toLowerCase().replaceAll("[^a-zA-Z0-9.-]", "_") +
// ".html"; // output filename
// try (FileWriter fw = new FileWriter(fp + filename)) { // filewriter for
// writing into a file
// fw.write(drv.getPageSource()); // returns source code of the current webpage
// as string
// System.out.println("Saved " + filename);
// } catch (IOException e) {
// e.printStackTrace();
// }

// List<WebElement> links = drv.findElements(By.tagName("a")); // find all links
// on the webpage by following
// // the anchor tag
// for (WebElement i : links) { // iterate over the list of links
// String linkUrl = i.getAttribute("href"); // obtain the URL of each link
// if (linkUrl != null && !pageUrls.contains(linkUrl)) {
// pageUrls.offer(linkUrl); // add the link URL to the queue if it hasn't been
// visited yet
// System.out.println("Added " + linkUrl + " to pageUrls");
// }
// }
// }
// drv.quit(); // Quit the driver
// }
// }
