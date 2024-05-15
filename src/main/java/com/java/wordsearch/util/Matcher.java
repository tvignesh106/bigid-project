package com.java.wordsearch.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import com.java.wordsearch.model.LineDetails;
import com.java.wordsearch.model.Part;

@Named
public class Matcher {

	private Set<String> dictionaryWords;

	@Inject
	public Matcher(InputDictionary dictionary) {
		dictionaryWords = dictionary.getDictionaryWords();
	}

	public Results getResultForPart(Part wsPart) {
		Results results = new Results();
		wsPart.getLines().forEach(line -> {
			dictionaryWords.forEach(word -> {
				List<String> matches = getMatchesByLineAndWord(line, word);
				if (!matches.isEmpty()) {
					results.recordResult(word, matches);
				}
			});
		});
		return results;
	}

	private List<String> getMatchesByLineAndWord(LineDetails line, String word) {
		int charOffset = 0;
		int wordLength = 0;
		List<String> matches = new ArrayList<>();
		while (charOffset != -1) {
			charOffset = line.getLine().indexOf(word, charOffset + wordLength);
			if (charOffset != -1) {
				matches.add("[lineOffset=" + line.getLineIndex() + ", charOffset=" + (charOffset + 1) + "]");
			}
			wordLength = word.length();
		}
		
		return matches;
	}

}
