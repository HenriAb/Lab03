package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Dictionary model;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbSceltaLingua;

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label txtPerformance;

    @FXML
    private Label txtErrors;
    
    @FXML
    private Button btnClearText;
    
    @FXML
    private Button btnSpellCheck;

    @FXML
    void doClearText(ActionEvent event) {

    	this.model.getDizionario().clear();
    	this.cbSceltaLingua.setValue("");
    	this.txtErrors.setText("");
    	this.txtInput.clear();
    	this.txtPerformance.setText("");
    	this.txtResult.clear();
    	this.btnSpellCheck.setDisable(true);
    	this.txtInput.setDisable(true);
    	
    }
    
    @FXML
    void doSelectLingua(ActionEvent event) {
    	this.txtInput.setDisable(false);
    	this.btnSpellCheck.setDisable(false);
    	this.ripulisci();
    }

    private void ripulisci() {
		this.txtErrors.setText("");
		this.txtInput.clear();
		this.txtPerformance.setText("");
		this.txtResult.clear();
		
	}

	@FXML
    void doSpellCheck(ActionEvent event) {
		
		String language;
		if(this.cbSceltaLingua.getValue() == null) {
			this.txtResult.setText("ERRORE: Non hai inserito la lingua");
			System.out.println("Non hai scelto la lingua");
			return;
		}
		language = this.cbSceltaLingua.getValue();
		this.model.loadDictionary(language);
		
    	String input = this.txtInput.getText();
    	if(input.equals("")) {
    		System.out.println("Devi inserire un testo");
    		this.txtResult.setText("ERRORE: Devi inserire un testo");
    		return;
    	}
    	input = input.toLowerCase().replaceAll("[.,\\/#!$%\\^\\*;:{}=\\-_'()\\[\\]\"]", "");
    	
    	List<String> lparole = new ArrayList<>();
    	String parole[] = input.split(" ");
    	for(int i=0; i<parole.length; i++) {
    		lparole.add(parole[i]);
    	}
    	long start = System.nanoTime();
    	List<RichWord> errate = this.model.spellTextCheck(lparole);
    	long stop = System.nanoTime();
    	String res = "Le parole errate sono:\n";
    	for(RichWord rwi : errate) {
    		res += rwi.getWord() + "\n";
    	}
    	this.txtResult.setText(res);
    	this.txtErrors.setText("The text contains " + errate.size());
    	this.txtPerformance.setText("Spell check completed in: " + (stop-start)*1e9 + " s");
    	
    	this.btnSpellCheck.setDisable(true);
    	this.txtInput.setDisable(true);
    }

    public void setModel(Dictionary model) {
    	this.model = model;
    }
    
    @FXML
    void initialize() {
        assert cbSceltaLingua != null : "fx:id=\"cbSceltaLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtPerformance != null : "fx:id=\"txtPerformance\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrors != null : "fx:id=\"txtErrors\" was not injected: check your FXML file 'Scene.fxml'.";

        this.cbSceltaLingua.getItems().addAll("English", "Italian");
        this.btnSpellCheck.setDisable(true);
        this.txtInput.setDisable(true);
    }
}
