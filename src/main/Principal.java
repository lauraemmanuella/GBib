package main;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Principal extends Application {

	
	public static void main(String[] args) {
		launch(args);
	
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/visao/Principal.fxml"));
        Scene scene = new Scene(root);
       
        //Ocupa a tela toda
        stage.centerOnScreen();
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
       
        stage.setScene(scene);
        stage.setTitle("Gerenciar Biblioteca");
        stage.show();
		
	}

}

