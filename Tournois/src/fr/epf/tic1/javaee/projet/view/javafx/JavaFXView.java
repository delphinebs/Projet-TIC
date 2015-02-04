package fr.epf.tic1.javaee.projet.view.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("Controller/Test.fxml"));
		 	primaryStage.setTitle("Gestionnaire de tournois");
			primaryStage.setScene(new Scene(root,400,400));
			primaryStage.show();
	}
	
}
