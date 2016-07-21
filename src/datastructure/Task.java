package datastructure;

import java.util.ArrayList;
import java.util.List;

public class Task implements Comparable<Task>{
	private String word;
	private List<String> sentenceList;

	public Task(String word) {
		this.word = word;
		sentenceList = new ArrayList<String>();
	}
	
	public String getWord() {
		return word;
	}

	public List<String> getSentenceList() {
		return sentenceList;
	}

	public void addSentence(String sentence) {
		sentenceList.add(sentence);
	}
	
	public int compareTo(Task o) {
		return word.compareTo(o.getWord());
	}
}