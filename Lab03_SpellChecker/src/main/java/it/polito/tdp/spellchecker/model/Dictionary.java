package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dictionary {

	private List<String> dizionario;
	
	public Dictionary() {
		this.dizionario = new ArrayList<>();
	}
	
	public List<String> getDizionario(){
		return this.dizionario;
	}
	
	public void loadDictionary(String language) {
		
		try {
			FileReader fr = new FileReader("src/main/resources/" + language + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word; 
			while((word = br.readLine()) != null) {
				dizionario.add(word);
			}
			
			Collections.sort(dizionario); // ordino il dizionario per avere tutte le parole in ordine (lo sono gia)
			br.close();
			System.out.println("Dizionario di lingua " + language + " caricato correttamente. Il dizionario contiene:" + this.dizionario.size() + " parole.");
		}catch(IOException ioe) {
			System.out.println("Errore nella lettura del file");
		}
		
	}
	
	
	public List<RichWord> spellTextCheck(List<String> inputTextList){
		
		List<RichWord> res = new ArrayList<>();	

		for(String si : inputTextList) {
			RichWord rw = new RichWord(si);
			if(this.dizionario.contains(si.toLowerCase())) {
				rw.setCorrect(true);
			}
			else {
				rw.setCorrect(false);
				res.add(rw);
			}
		}
		
		return res;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		
		List<RichWord> res = new ArrayList<>();
		
		for(String si : inputTextList) {
			RichWord rw = new RichWord(si);
			
			boolean found = false;
			
			for(String word : this.dizionario) {
				if(word.equalsIgnoreCase(si)) {
					found = true;
					break;
				}
			}
			if(found) {
				rw.setCorrect(true);
			}
			else {
				rw.setCorrect(false);
				res.add(rw);
			}
		}
		
		return res;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		List<RichWord> res = new ArrayList<>();
		
		for(String si : inputTextList) {
			RichWord rw = new RichWord(si);
			if(binarySearch(si.toLowerCase())) {
				rw.setCorrect(true);
			}
			else {
				rw.setCorrect(false);
				res.add(rw);
			}
		}
		
		return res;
	}

	private boolean binarySearch(String str) {
		
		int inizio = 0;
		int fine = this.dizionario.size();
		
		while(inizio != fine) {
			int medio = inizio + (fine-inizio)/2;
			if(str.compareToIgnoreCase(this.dizionario.get(medio)) ==0 ) {
				return true;
			}
			else if(str.compareToIgnoreCase(this.dizionario.get(medio)) >0 ){
				inizio = medio + 1;
			}
			else {
				fine = medio;
			}
		}
		return false;
	}
}