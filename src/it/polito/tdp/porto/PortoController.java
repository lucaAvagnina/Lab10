package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	Author author = boxPrimo.getValue();
    	
    	if(author == null) {
    		txtResult.setText("Devi selezionare un autore!");
    		return; 
    	}
    	 
    	String res = "I co-autori di " + author.getLastname() +" " + author.getFirstname() +" sono:\n";
		for(Author a : model.getCoAutori(author)) {
			res += "- " +  a.getLastname() +" " + a.getFirstname() +"\n";
		}
  
    	txtResult.setText(res.trim());
    }
    

    @FXML
    void doAutori2(ActionEvent event) {
    	boxSecondo.getItems().addAll(model.getAutori());
    	boxSecondo.getItems().removeAll(model.getCoAutori(boxPrimo.getValue()));
    	boxSecondo.getItems().remove(boxPrimo.getValue());
    }

	@FXML
    void handleSequenza(ActionEvent event) {
		Author author1 = boxPrimo.getValue();
		Author author2 = boxSecondo.getValue();
		
		
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxPrimo.getItems().addAll(model.getAutori());
    }
}
