package fr.epf.tic1.javaee.projet.view.javafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AccueilController {

	@FXML
	public ComboBox<String> typeTournoisCombo;
	@FXML
	public TextField nomTournoisText;
	@FXML
	public Label confirmLabel;
	
	Stage stage = null;
	
	public void creerTournois() {
		
		if(typeTournoisCombo.valueProperty().isNull().get() ||  nomTournoisText.getText().equals("")){
			confirmLabel.setVisible(true);
		}
		else{
			try {  

	            final FXMLLoader loader = new FXMLLoader(getClass().getResource("Tournois.fxml"));
	    		final Parent root = (Parent) loader.load();
	    		final TournoisController controller = loader.<TournoisController> getController();
	    		
	            //stage.initStyle(StageStyle.TRANSPARENT);
	            //stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
	            stage.setScene(new Scene(root,800,400));
	            
	            controller.setInfo(nomTournoisText.getText(), typeTournoisCombo.getValue());

	            controller.setStage(stage);
	            stage.show();
	            
	            // Hide the current screen
	            //confirmLabel.getScene().getWindow().hide();

	        } catch (Exception exc) {
	            System.out.println("Error: " + exc.getMessage());
	            exc.printStackTrace();
	        }
			
		}
	}

	public void setStage(Stage primaryStage) {
		 this.stage = primaryStage;
	}
	
}
