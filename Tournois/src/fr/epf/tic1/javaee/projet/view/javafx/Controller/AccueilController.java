package fr.epf.tic1.javaee.projet.view.javafx.Controller;

import java.io.IOException;
import java.util.ArrayList;

import fr.epf.tic1.javaee.projet.model.Equipe;
import javafx.application.Platform;
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
	public TextField nbEquipeText;
	@FXML
	public Label confirmLabel;
	@FXML
	public Label typeLabel;
	@FXML
	public Label nbEquipeLabel;
	
	Stage stage = null;
	
	public void creerTournois() {
		
		if(typeTournoisCombo.valueProperty().isNull().get() ||  nomTournoisText.getText().equals("") ||  nbEquipeText.getText().equals("")){
			confirmLabel.setVisible(true);
			typeLabel.setVisible(false);
			nbEquipeLabel.setVisible(false);
		}
		else if(!nbEquipeText.getText().matches("[0-9]*")){
			typeLabel.setVisible(true);
			confirmLabel.setVisible(false);
			nbEquipeLabel.setVisible(false);
		}
		else if(Integer.parseInt(nbEquipeText.getText())<3){
			nbEquipeLabel.setVisible(true);
			confirmLabel.setVisible(false);
			typeLabel.setVisible(false);
		}
		else{
			try {  
				if (typeTournoisCombo.getValue().equals("Poules")) {

					final FXMLLoader loader = new FXMLLoader(getClass().getResource("Poule.fxml"));
					final Parent root = (Parent) loader.load();
					final PouleController controller = loader.<PouleController> getController();

					stage.setScene(new Scene(root,800,400));
					ArrayList<Equipe> equipes = new ArrayList<>();
					for(int i=0;i<Integer.parseInt(nbEquipeText.getText());i++){
						equipes.add(new Equipe("Equipe"+(i+1)));
					}
					controller.init(nomTournoisText.getText(), equipes, stage);
					stage.setTitle(nomTournoisText.getText());
					stage.show();

				} else {
					final FXMLLoader loader = new FXMLLoader(getClass().getResource("Direct.fxml"));
					final Parent root = (Parent) loader.load();
					final DirectController controller = loader.<DirectController> getController();

					stage.setScene(new Scene(root, 800, 400));		
					ArrayList<Equipe> equipes = new ArrayList<>();
					for(int i=0;i<Integer.parseInt(nbEquipeText.getText());i++){
						equipes.add(new Equipe("Equipe"+(i+1)));
					}
					controller.init(nomTournoisText.getText(), equipes,stage);
					
					stage.setTitle(nomTournoisText.getText());
					stage.show();
				}

	        } catch (Exception exc) {
	            System.out.println("Error: " + exc.getMessage());
	            exc.printStackTrace();
	        }
			
		}
	}

	public void setStage(Stage primaryStage) {
		 this.stage = primaryStage;
	}
	
	public void Recommencer() throws IOException {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"Accueil.fxml"));
		final Parent root = (Parent) loader.load();
		final AccueilController controller = loader
				.<AccueilController> getController();

		stage.setTitle("Gestionnaire de tournois");

		Scene scene = new Scene(root, 800, 400);
		stage.setScene(scene);
		Platform.setImplicitExit(false);

		controller.setStage(stage);
		stage.show();
	}
	
	public void Quitter(){
		System.exit(0);
	}
	
}
