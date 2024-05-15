package com.java.wordsearch.model;

public class LineDetails {
	private String line;
	private int lineIndex;

	public LineDetails(String line, int lineIndex) {
		this.line = line;
		this.lineIndex = lineIndex;
	}

	public String getLine() {
		return line;
	}

	public int getLineIndex() {
		return lineIndex;
	}

}