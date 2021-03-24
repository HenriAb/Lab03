package it.polito.tdp.spellchecker.model;

public class RichWord {

	private String word;
	private boolean correct;
	
	public RichWord(String word, boolean correct) {
		super();
		this.word = word;
		this.correct = correct;
	}

	public RichWord(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public String toString() {
		return this.word + "\n";
	}
	
	
}
