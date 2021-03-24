package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
		
//		int i = 0; 		// that's really really slow
//		for(String si : this.dizionario) {
//			if(si.contains(inputTextList.get(i))) {
//				rw.setWord(si);
//				rw.setCorrect(true);
//				res.add(rw);
//			}
//			i++;
//		}
		
		return res;
	}
}