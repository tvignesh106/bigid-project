package com.java.wordsearch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.wordsearch.model.Part;
import com.java.wordsearch.util.Aggregator;
import com.java.wordsearch.util.FileReader;
import com.java.wordsearch.util.Matcher;

@Named
public class WordSearch {

	private static final Logger logger = LoggerFactory.getLogger(WordSearch.class);
	private static final String FilePath = "big.txt";
	private static final int ThusandLines = 1000;
	private static Instant start;

	private FileReader wsFileReader;
	private Matcher wsMatcher;
	private Aggregator wsAggregator;

	@Inject
	public WordSearch(FileReader wsFileReader, Matcher wsMatcher, Aggregator wsAggregator) {
		this.wsFileReader = wsFileReader;
		this.wsMatcher = wsMatcher;
		this.wsAggregator = wsAggregator;
	}

	public void run() throws IOException, URISyntaxException, InterruptedException {
		start = Instant.now();
		logger.info("Starting WordSearch Application ...");

		wsFileReader.readFileInParts(FilePath, ThusandLines);
		int partIndex = 0;
		while (wsFileReader.hasParts()) {
			Part wsPart = wsFileReader.getNextPart(partIndex);
			wsAggregator.recordResultsForPart(wsMatcher.getResultForPart(wsPart));
			partIndex++;
		}

		wsAggregator.printResultsToFile();
		long timeElapsed = Duration.between(start, Instant.now()).toMillis();

		logger.info("WordSearch Application took " + timeElapsed + " milliseconds to complete!");
	}

	protected Aggregator getWsAggregator() {
		return wsAggregator;
	}

}
