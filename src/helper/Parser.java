package helper;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Parser {
	private static final String htmlFilesDirectory = "C:\\Java\\Project_ACC\\Webpages_project\\Webpages_project";
	private static final String textFilesDirectory = "C:\\Java\\Project_ACC\\Text";
	final static ArrayList<String> filterWords = new ArrayList<>(Arrays.asList("THE", "FOR", "AND", "HAS", "THAT", "IN",
			"A", "AN", "HE", "SHE", "IT", "HAVE", "COULD", "WOULD",
			"THIS", "ARE", "WHICH", "HOW", "CAN", "THEN", "THAN", "WHEN", "ITS", "FROM", "WITH", "WILL", "NOT"));

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
			System.out.println("Parsing File: " + fileName);
			// !fileExtension.equalsIgnoreCase("htm") &&
			if (!fileExtension.equalsIgnoreCase("html")) {

				// If the file is not of html or htm type, then return without processing
				// further and prints the error message
				System.out.println("The added files are not html/htm files.Please add files with proper extention");
				return;
			}
			String txtFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".txt";

			File txtFile = new File(textFilesDirectory, txtFileName);
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
	public static void getfilesfromfolder() {
		File path = new File(htmlFilesDirectory);
		// takes all the files and adds them in the files array
		File[] files = path.listFiles();
		// traverses through all the files and calls getfiles method
		for (File file : files) {
			getfiles(file);
		}
	}

	public static HashMap<String, Integer> getWordsWithFrequency() {

		File directory = new File(textFilesDirectory);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		File[] htmlFiles = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		// checks if the File array is not null
		if (htmlFiles != null) {
			// loops over the length of the File array
			for (int i = 0; i < htmlFiles.length; i++) {
				Document doc;
				try {
					// parsing the HTML file using Jsoup
					doc = Jsoup.parse(htmlFiles[i], "UTF-8");
					String text = doc.body().text();
					// tokenizing the 'text' by splitting it based on spaces
					StringTokenizer sz = new StringTokenizer(text, " ");

					while (sz.hasMoreTokens()) {
						// gets the next word
						String token = sz.nextToken();

						// replace all characters that are not letters or numbers with a space
						String extract = token.replaceAll("[^a-zA-Z0-9]+", "");

						// the words are converted to uppercase
						extract = extract.toUpperCase();

						if (extract.matches("(?=.{3,16}$)[A-Za-z]+$")
								&& !filterWords.contains(extract)) {
							// adding words into map
							// checking if the map already contains the word
							if (map.containsKey(extract)) {
								// if map already contains the word incrementing its value by 1
								map.put(extract, map.get(extract) + 1);
							} else {
								// else adding the frequency as 1 if its a new word in the map
								map.put(extract, 1);
							}
						}

					}

				}

				catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
		return map;

	}

	public static ArrayList<String> getWords() {
		File directory = new File(textFilesDirectory);
		ArrayList<String> arr = new ArrayList<String>();
		File[] htmlFiles = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});

		// checks if the File array is not null
		if (htmlFiles != null) {
			// loops over the length of the File array
			for (int i = 0; i < htmlFiles.length; i++) {
				Document doc;
				try {
					// parsing the HTML file using Jsoup
					doc = Jsoup.parse(htmlFiles[i], "UTF-8");
					String text = doc.body().text();
					// tokenizing the 'text' by splitting it based on spaces
					StringTokenizer sz = new StringTokenizer(text, " ");

					while (sz.hasMoreTokens()) {
						// gets the next word
						String token = sz.nextToken();

						// replace all characters that are not letters or numbers with a space
						String extract = token.replaceAll("[^a-zA-Z0-9]+", "");

						// the words are converted to uppercase
						extract = extract.toUpperCase();

						if (extract.matches("(?=.{3,16}$)[A-Za-z]+$")
								&& !filterWords.contains(extract)) {

							if (!arr.contains(extract)) {

								arr.add(extract);
							}
						}

					}

				}

				catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
		return arr;

	}

	// filters out the filterwords and creates a set
	public static Set<String> filterStopWords(String query) {
		String[] words = query.split(" ");
		Set<String> keywords = new HashSet<String>();
		for (String word : words)
			if (!filterWords.contains(word.toUpperCase()))
				keywords.add(word);
		return keywords;
	}

	public static void main(String[] args) {
		ArrayList<String> map = getWords();
		System.out.println(map);
	}
}
