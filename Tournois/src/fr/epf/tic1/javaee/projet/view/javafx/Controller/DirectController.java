package fr.epf.tic1.javaee.projet.view.javafx.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.execptions.NomDejaPrisExecption;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;

public class DirectController {

	@FXML
	public ComboBox<String> listEquipeCombo;
	@FXML
	public ComboBox<String> listMatchCombo;
	@FXML
	public Button gererEquipeButton;
	@FXML
	public Button validerButton;
	@FXML
	public Button statsButton;
	@FXML
	public Button sauvModifButton;
	@FXML
	public TextField score1Text;
	@FXML
	public TextField score2Text;
	@FXML
	public TextField nomEquipeText;
	@FXML
	public TextField nbJoueursText;
	@FXML
	public TextField descriptionText;
	@FXML
	public Label scoreLabel;
	@FXML
	public Label scoreTypeLabel;
	@FXML
	public Label AffichageGlobalLabel;
	@FXML
	public Label editEquipeLabel;

	private TournoisDirect tournois;
	private TournoisDirectController tournoisController;

	private Match matchCourant;
	private Equipe equipeCourante;
	private Stage stage = null;

	public void init(String nom, ArrayList<Equipe> equipes, Stage primaryStage) {
		stage = primaryStage;

		tournois = new TournoisDirect(nom, equipes);
		tournoisController = new TournoisDirectController();

		for (Equipe equipe : equipes) {
			listEquipeCombo.getItems().add(equipe.getNom());
		}

		tournoisController.start(tournois);

		listMatchCombo.setVisible(true);
		miseAjourAffichage();
	}

	private void miseAjourAffichage() {

		// Mise a jour de l'affichage global
		String texte = "";

		for (int i = 0; i < tournois.getArbre().size(); i++) {
			texte += "Tour " + i + "   ";
			for (Match match : tournois.getArbre().get(i)) {
				if (match == null)
					texte += "---   ";
				else if (match.getScore()[0] == -2)
					texte += "Prequalifie : " + match.toString() + "  ";
				else
					texte += match.toString() + "  ";
			}
			texte += "\n";
		}

		AffichageGlobalLabel.setText(texte);

		// Mise a jour des matchs
		listMatchCombo.getItems().clear();
		for (Match match : tournois.getArbre().get(tournois.getTour())) {
			if (match.getScore()[0] != -2)
				listMatchCombo.getItems().add(match.toString());
		}
		listMatchCombo.valueProperty().set(null);
	}

	public void SaisirScore() {
		if (!listMatchCombo.valueProperty().isNull().get()) {

			String matchStr = listMatchCombo.getValue();

			for (Match match : tournois.getArbre().get(tournois.getTour())) {
				if (match.toString().equals(matchStr)) {
					matchCourant = match;
					break;
				}
			}

			validerButton.setVisible(true);
			score1Text.setPromptText("Score de l'equipe "
					+ matchCourant.getEquipes()[0]);
			score2Text.setPromptText("Score de l'equipe "
					+ matchCourant.getEquipes()[1]);
			score1Text.setVisible(true);
			score2Text.setVisible(true);
		}
	}

	public void ValiderScore() {
		if (score1Text.getText().equals("") || score2Text.getText().equals("")) {
			scoreLabel.setVisible(true);
		} else {
			scoreLabel.setVisible(false);

			if (score1Text.getText().matches("[0-9]*")
					&& score2Text.getText().matches("[0-9]*")) {
				scoreTypeLabel.setVisible(false);
				if (Integer.parseInt(score1Text.getText()) == Integer
						.parseInt(score2Text.getText())) {
					Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.initOwner(stage);
					VBox dialogVbox = new VBox(20);
					dialog.setTitle("Information");
					dialogVbox
							.getChildren()
							.add(new Text(
									"L'egalite est impossible en elimination direct"));
					Scene dialogScene = new Scene(dialogVbox, 300, 200);
					dialog.setScene(dialogScene);
					dialog.show();
				} else {
					if (tournoisController.finMatch(tournois, matchCourant,
							Integer.parseInt(score1Text.getText()),
							Integer.parseInt(score2Text.getText()))) {

						// Fin tournois
						Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						dialog.initOwner(stage);
						VBox dialogVbox = new VBox(20);
						dialog.setTitle("Fin du tournois");
						dialogVbox
								.getChildren()
								.add(new Text(
										"L'equipe "+tournois.getGagnant()+" gagne le tournois!!"));
						Scene dialogScene = new Scene(dialogVbox, 300, 200);
						dialog.setScene(dialogScene);
						dialog.show();
						
						score1Text.setDisable(true);
						score2Text.setDisable(true);
						validerButton.setDisable(true);

					}

					score1Text.setText("");
					score2Text.setText("");
					score1Text.setVisible(false);
					score2Text.setVisible(false);
					validerButton.setVisible(false);
					miseAjourAffichage();
				}

			} else {
				scoreTypeLabel.setVisible(true);
			}
		}

	}

	public void GererEquipe() {
		if (!listEquipeCombo.valueProperty().isNull().get()) {
			String equipeStr = listEquipeCombo.getValue();

			for (Equipe equipe : tournois.getEquipes()) {
				if (equipe.getNom().equals(equipeStr)) {
					equipeCourante = equipe;
					break;
				}
			}

			nomEquipeText.setText(equipeStr);
			nbJoueursText.setText(String.valueOf(equipeCourante.getNbJoueur()));
			descriptionText.setText(equipeCourante.getDescription());
			sauvModifButton.setVisible(true);
			nomEquipeText.setVisible(true);
			nbJoueursText.setVisible(true);
			descriptionText.setVisible(true);
			statsButton.setVisible(true);
		}
	}

	public void Stats() {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(stage);
		VBox dialogVbox = new VBox(20);
		dialog.setTitle("Statisitqueq de l'equipe " + equipeCourante.getNom());
		dialogVbox.getChildren().add(
				new Text(equipeCourante.stats(tournois, null)));
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	public void SauvModif() {
		if (nomEquipeText.getText().equals("")
				|| nomEquipeText.getText().equals("default")
				|| !nbJoueursText.getText().matches("[0-9]*")) {
			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(stage);
			VBox dialogVbox = new VBox(20);
			dialog.setTitle("Information");
			dialogVbox
					.getChildren()
					.add(new Text(
							"Le nom de l'equipe doit etre different de  'default'. \nLe nom de l'equipe ne doit pas etre vide. \nLe nombre de joueur doit etre indique en chiffre."));
			Scene dialogScene = new Scene(dialogVbox, 300, 200);
			dialog.setScene(dialogScene);
			dialog.show();
		} else {
			try {
				for (Equipe equipe : tournois.getEquipes()) {
					if (equipe.getNom().equals(nomEquipeText.getText())
							&& !equipe.equals(equipeCourante)) {
						throw new NomDejaPrisExecption();
					}
				}

				equipeCourante.setDescription(descriptionText.getText());
				equipeCourante.setNom(nomEquipeText.getText());
				equipeCourante.setNbJoueur(Integer.parseInt((nbJoueursText
						.getText())));

				sauvModifButton.setVisible(false);
				nomEquipeText.setVisible(false);
				nbJoueursText.setVisible(false);
				descriptionText.setVisible(false);
				statsButton.setVisible(false);

				miseAjourAffichage();
				AffichageGlobalLabel.setVisible(true);
				listMatchCombo.setVisible(true);

				// Reset Combobox
				listEquipeCombo.getItems().clear();
				for (Equipe equipe : tournois.getEquipes()) {
					listEquipeCombo.getItems().add(equipe.getNom());
				}
			} catch (NomDejaPrisExecption ndpe) {
				Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(stage);
				VBox dialogVbox = new VBox(20);
				dialog.setTitle("Information");
				dialogVbox.getChildren().add(new Text("Nom deja pris"));
				Scene dialogScene = new Scene(dialogVbox, 300, 200);
				dialog.setScene(dialogScene);
				dialog.show();
			}
		}
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

	public void Quitter() {
		System.exit(0);
	}

	public void GererUneEquipe() {
		listEquipeCombo.setVisible(true);
		gererEquipeButton.setVisible(true);

		listMatchCombo.setVisible(false);
		validerButton.setVisible(false);
		score1Text.setVisible(false);
		score2Text.setVisible(false);
		scoreLabel.setVisible(false);
		scoreTypeLabel.setVisible(false);
		AffichageGlobalLabel.setVisible(false);

		miseAjourAffichage();
	}

	public void Gererletournois() {
		AffichageGlobalLabel.setVisible(true);
		listMatchCombo.setVisible(true);

		sauvModifButton.setVisible(false);
		nomEquipeText.setVisible(false);
		nbJoueursText.setVisible(false);
		descriptionText.setVisible(false);
		statsButton.setVisible(false);
		listEquipeCombo.setVisible(false);
		gererEquipeButton.setVisible(false);
		editEquipeLabel.setVisible(false);

		miseAjourAffichage();
	}
}
