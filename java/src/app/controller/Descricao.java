package app.controller;

import java.io.IOException;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Descricao {
	
    @FXML
    void next(ActionEvent event) {
    	try {
			Pane painel = FXMLLoader.load(getClass().getResource("../view/Painel.fxml"));
    		App.tela.setScene(new Scene(painel));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
