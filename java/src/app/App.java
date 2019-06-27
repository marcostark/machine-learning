package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{
	
	public static Stage tela;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane descricao = FXMLLoader.load(getClass().getResource("view/Descricao.fxml"));
			primaryStage.setScene(new Scene(descricao));
			primaryStage.show();
			tela = primaryStage;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
