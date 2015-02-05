package fr.epf.tic1.javaee.projet.view.javafx.Controller;

import java.util.ArrayList;

import fr.epf.tic1.javaee.projet.model.Equipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TournoisController {
	@FXML
	public Label nomTournoisLabel;
	@FXML
	public Label typeTournoisLabel;
	@FXML
	public Label compteurLabel;
	@FXML
	public Label confirmLabel;
	@FXML
	public TextField nomEquipeText;
	@FXML
	public ComboBox<String> listEquipeCombo;

	Stage stage = null;

	public void setInfo(String nom, String type) {
		nomTournoisLabel.setText(nom);
		typeTournoisLabel.setText(type);
		nomTournoisLabel.setFocusTraversable(true);
	}

	public void AjouterEquipe() {
		if (!nomEquipeText.getText().equals("")) {
			if (listEquipeCombo.promptTextProperty().getValue()
					.equals("Aucune equipe")) {
				listEquipeCombo.setDisable(false);
				listEquipeCombo.promptTextProperty().setValue(
						"Liste des equipes");
			}
			listEquipeCombo.getItems().add(nomEquipeText.getText());
			nomEquipeText.setText("");
			int i = Integer.parseInt(compteurLabel.getText()) + 1;
			compteurLabel.setText(String.valueOf(i));
		}
	}

	public void SupprimerEquipe() {
		if (!listEquipeCombo.valueProperty().isNull().get()) {
			listEquipeCombo.getItems().remove(listEquipeCombo.getValue());
			if (listEquipeCombo.getItems().isEmpty()) {
				listEquipeCombo.promptTextProperty().setValue("Aucune equipe");
				listEquipeCombo.setDisable(true);
			}
			int i = Integer.parseInt(compteurLabel.getText()) - 1;
			compteurLabel.setText(String.valueOf(i));
		}
	}

	public void LancerTournois() {
		if (listEquipeCombo.getItems().size() < 3) {
			confirmLabel.setVisible(true);
		} else {
			try {

				if (typeTournoisLabel.getText().equals("Poules")) {

					final FXMLLoader loader = new FXMLLoader(getClass().getResource("Poule.fxml"));
					final Parent root = (Parent) loader.load();
					final PouleController controller = loader.<PouleController> getController();

					// stage.initStyle(StageStyle.TRANSPARENT);
					// stage.getIcons().add(new
					// Image(getClass().getResourceAsStream("icon.png")));
					stage.setScene(new Scene(root, 800, 400));

					// /controller.setInfo(nomTournoisText.getText(),
					// typeTournoisCombo.getValue());

					ArrayList<Equipe> equipes = new ArrayList<>();
					for(String nom : listEquipeCombo.getItems()){
						equipes.add(new Equipe(nom));
					}
					controller.init(nomTournoisLabel.getText(), equipes);
					
					stage.show();

					// Hide the current screen
					// confirmLabel.getScene().getWindow().hide();
				} else {
					final FXMLLoader loader = new FXMLLoader(getClass().getResource("Poule.fxml"));
					final Parent root = (Parent) loader.load();
					final TournoisController controller = loader.<TournoisController> getController();

					// stage.initStyle(StageStyle.TRANSPARENT);
					// stage.getIcons().add(new
					// Image(getClass().getResourceAsStream("icon.png")));
					stage.setScene(new Scene(root, 800, 400));

					// /controller.setInfo(nomTournoisText.getText(),
					// typeTournoisCombo.getValue());

					stage.show();

					// Hide the current screen
					// confirmLabel.getScene().getWindow().hide();
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

}
