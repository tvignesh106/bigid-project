package com.java.wordsearch.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Results {

	private Map<String, List<String>> results = new TreeMap<String, List<String>>();

	public Map<String, List<String>> getResults() {
		return results;
	}

	public void recordResult(String word, List<String> matches) {
		if (!results.containsKey(word)) {
			results.put(word, new ArrayList<>());
		}
		results.get(word).add("[" + String.join(",", matches) + "]");
	}

}
