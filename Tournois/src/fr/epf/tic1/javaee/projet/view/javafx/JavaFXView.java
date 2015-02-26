package fr.epf.tic1.javaee.projet.view.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fr.epf.tic1.javaee.projet.view.javafx.Controller.AccueilController;

public class JavaFXView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("Controller/Accueil.fxml"));
		final Parent root = (Parent) loader.load();
		final AccueilController controller = loader.<AccueilController> getController();

		//primaryStage.initStyle(StageStyle.DECORATED);
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.setTitle("Gestionnaire de tournois");

        Scene scene = new Scene(root,800,400);
        primaryStage.setScene(scene);
        Platform.setImplicitExit(false);

        controller.setStage(primaryStage);
        //primaryStage.setFullScreen(true);
        primaryStage.show();   
	}


}
