package com.java.wordsearch.model;

import java.util.ArrayList;
import java.util.List;

public class Part {
	private int partIndex;
	private int partLineCount;
	private List<LineDetails> lines = new ArrayList<>();

	public Part(int partIndex, int partLineCount) {
		this.partIndex = partIndex;
		this.partLineCount = partLineCount;
	}

	public void addLine(String line, int lineIndex) {
		lines.add(new LineDetails(line, (partLineCount * partIndex) + lineIndex));
	}

	public List<LineDetails> getLines() {
		return lines;
	}

}