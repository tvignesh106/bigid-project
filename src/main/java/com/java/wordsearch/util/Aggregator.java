package com.java.wordsearch.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class Aggregator {
	private static final Logger logger = LoggerFactory.getLogger(Aggregator.class);

	private Map<String, List<String>> results = new TreeMap<String, List<String>>();
	ExecutorService executor = Executors.newFixedThreadPool(5);

	public void recordResultsForPart(Results wsResults) throws InterruptedException {
		Map<String, List<String>> partResults = wsResults.getResults();
		executor.execute(() -> partResults.forEach((word, result) -> {
			if (results.containsKey(word)) {
				List<String> wordResult = results.get(word);
				wordResult.addAll(result);
			} else {
				results.put(word, result);
			}
		}));
	}

	public void printResultsToFile() throws IOException, InterruptedException {
		FileWriter fileWriter = new FileWriter("word-search-result.txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		results.forEach((word, result) -> {
			result.forEach(resultLine -> {
				printWriter.printf("%s --> %s\n", word, resultLine);
			});
		});
		printWriter.close();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.HOURS);
		logger.info("==============> Search result have been added to file: word-search-result.txt");
	}

	public String getResultsByWord(String word, int lineIndex) {
		List<String> result = results.get(word);
		return result.stream().filter(match -> match.contains("[[lineOffset=" + lineIndex)).findAny().orElse("");
	}

}
