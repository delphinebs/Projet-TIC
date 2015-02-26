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
import fr.epf.tic1.javaee.projet.controller.TournoisPouleController;
import fr.epf.tic1.javaee.projet.execptions.NomDejaPrisExecption;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.Poule;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;
import fr.epf.tic1.javaee.projet.model.TournoisPoule;

public class PouleController {
	@FXML
	public ComboBox<Integer> listPouleCombo;
	@FXML
	public ComboBox<String> listEquipePouleCombo;
	@FXML
	public ComboBox<String> listMatchCombo;
	@FXML
	public ComboBox<String> listMatchDirectCombo;
	@FXML
	public Button gererEquipeButton;
	@FXML
	public Button validerButton;
	@FXML
	public Button validerDButton;
	@FXML
	public Button statsButton;
	@FXML
	public TextField score1Text;
	@FXML
	public TextField score2Text;
	@FXML
	public TextField score1DText;
	@FXML
	public TextField score2DText;
	@FXML
	public Label scoreLabel;
	@FXML
	public Label scoreTypeLabel;
	@FXML
	public Label scoreDLabel;
	@FXML
	public Label scoreDTypeLabel;
	@FXML
	public Label phaseDirectLabel;
	@FXML
	public Label phasePouleLabel;
	@FXML
	public Label AffichageGlobalLabel;
	@FXML
	public ComboBox<String> listEquipeCombo;
	@FXML
	public Button sauvModifButton;
	@FXML
	public TextField nomEquipeText;
	@FXML
	public TextField nbJoueursText;
	@FXML
	public TextField descriptionText;

	private TournoisPoule tournois;
	private TournoisPouleController tournoisController;

	private TournoisDirect direct;
	private TournoisDirectController tournoisDirectController;

	private Poule pouleCourante;
	private Equipe equipeCourante;
	private Match matchCourant;
	private Stage stage;

	public void init(String nom, ArrayList<Equipe> equipes, Stage primaryStage) {
		stage = primaryStage;

		tournois = new TournoisPoule(nom, equipes);
		tournoisController = new TournoisPouleController();
		tournoisController.start(tournois);

		ArrayList<Poule> poules = tournois.getPoules();

		for (Poule poule : poules) {
			listPouleCombo.getItems().add(poule.getNumero());
		}

		// Reset Combobox
		listEquipeCombo.getItems().clear();
		for (Equipe equipe : tournois.getEquipes()) {
			listEquipeCombo.getItems().add(equipe.getNom());
		}
		listEquipeCombo.valueProperty().set(null);

		miseAjourAffichage();
	}

	public void Stats() {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(stage);
		VBox dialogVbox = new VBox(20);
		dialog.setTitle("Statisitqueq de l'equipe " + equipeCourante.getNom());
		dialogVbox.getChildren().add(
				new Text(equipeCourante.stats(direct, tournois)));
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	private void miseAjourAffichage() {

		if (direct != null) {
			listMatchDirectCombo.getItems().clear();
			for (Match match : direct.getArbre().get(direct.getTour())) {
				if (match.getScore()[0] != -2)
					listMatchDirectCombo.getItems().add(match.toString());
			}
			listMatchDirectCombo.valueProperty().set(null);

			String texte = "";

			for (int i = 0; i < direct.getArbre().size(); i++) {
				texte += "Tour " + i + "   ";
				for (Match match : direct.getArbre().get(i)) {
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

		}
		if (pouleCourante != null) {
			listEquipePouleCombo.getItems().clear();
			listMatchCombo.getItems().clear();

			for (Equipe equipe : pouleCourante.getEquipes()) {
				listEquipePouleCombo.getItems().add(equipe.getNom());
			}
			for (Match match : pouleCourante.getMatchs()) {
				listMatchCombo.getItems().add(match.toString());
			}

			listMatchCombo.valueProperty().set(null);
			listEquipePouleCombo.valueProperty().set(null);
		}

	}

	public void AfficherPoule() {
		if (!listPouleCombo.equals(null)) {
			int pouleCouranteNB = listPouleCombo.getValue();

			for (Poule poule : tournois.getPoules()) {
				if (poule.getNumero() == pouleCouranteNB) {
					pouleCourante = poule;
					break;
				}
			}

			miseAjourAffichage();

			listEquipePouleCombo.setVisible(true);
			listMatchCombo.setVisible(true);
			gererEquipeButton.setVisible(true);

		}
	}

	public void SaisirScore() {
		if (!listMatchCombo.equals(null)) {
			int pouleCouranteNB = listPouleCombo.getValue();

			for (Poule poule : tournois.getPoules()) {
				if (poule.getNumero() == pouleCouranteNB) {
					pouleCourante = poule;
					break;
				}
			}

			String matchStr = listMatchCombo.getValue();

			for (Match match : pouleCourante.getMatchs()) {
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

	public void SaisirScoreD() {
		if (!listMatchDirectCombo.equals(null)) {

			String matchStr = listMatchDirectCombo.getValue();

			for (Match match : direct.getArbre().get(direct.getTour())) {
				if (match.toString().equals(matchStr)) {
					matchCourant = match;
					break;
				}
			}

			validerDButton.setVisible(true);
			score1DText.setPromptText("Score de l'equipe "
					+ matchCourant.getEquipes()[0]);
			score2DText.setPromptText("Score de l'equipe "
					+ matchCourant.getEquipes()[1]);
			score1DText.setVisible(true);
			score2DText.setVisible(true);
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
				direct = tournoisController.finMatch(tournois, pouleCourante,
						matchCourant, Integer.parseInt(score1Text.getText()),
						Integer.parseInt(score2Text.getText()));

				if (direct != null) {
					// Debut du tournois en arbre
					tournoisDirectController = new TournoisDirectController();
					tournoisDirectController.start(direct);
					miseAjourAffichage();
					score1Text.setDisable(true);
					score2Text.setDisable(true);
					validerButton.setDisable(true);
					listMatchDirectCombo.setVisible(true);

					phasePouleLabel.setText("Phase de Poule : (terminee)");
					phaseDirectLabel.setVisible(true);
				}

				miseAjourAffichage();
				score1Text.setText("");
				score2Text.setText("");
				score1Text.setVisible(false);
				score2Text.setVisible(false);
				validerButton.setVisible(false);

			} else {
				scoreTypeLabel.setVisible(true);
			}
		}

	}

	public void ValiderScoreD() {
		if (score1DText.getText().equals("")
				|| score2DText.getText().equals("")) {
			scoreDLabel.setVisible(true);
		} else {
			scoreDLabel.setVisible(false);

			if (score1DText.getText().matches("[0-9]*")
					&& score2DText.getText().matches("[0-9]*")) {
				scoreDTypeLabel.setVisible(false);
				if (Integer.parseInt(score1DText.getText()) == Integer
						.parseInt(score2DText.getText())) {
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
					if (tournoisDirectController.finMatch(direct, matchCourant,
							Integer.parseInt(score1DText.getText()),
							Integer.parseInt(score2DText.getText()))) {

						// Fin tournois
						Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						dialog.initOwner(stage);
						VBox dialogVbox = new VBox(20);
						dialog.setTitle("Fin du tournois");
						dialogVbox
								.getChildren()
								.add(new Text(
										"L'equipe "+direct.getGagnant()+" gagne le tournois!!"));
						Scene dialogScene = new Scene(dialogVbox, 300, 200);
						dialog.setScene(dialogScene);
						dialog.show();
						
						score1DText.setDisable(true);
						score2DText.setDisable(true);
						validerDButton.setDisable(true);
						phaseDirectLabel
								.setText("Phase d'elimination : (terminee)");
					}

					score1DText.setText("");
					score2DText.setText("");
					score1DText.setVisible(false);
					score2DText.setVisible(false);
					validerDButton.setVisible(false);
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
				if (direct != null) {
					AffichageGlobalLabel.setVisible(true);
					listMatchDirectCombo.setVisible(true);
					phaseDirectLabel.setVisible(true);
					phaseDirectLabel.setVisible(true);
				}
				phasePouleLabel.setVisible(true);
				listPouleCombo.setVisible(true);
				listEquipePouleCombo.setVisible(true);
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
		miseAjourAffichage();

		listEquipeCombo.setVisible(true);
		gererEquipeButton.setVisible(true);

		listPouleCombo.setVisible(false);
		listEquipePouleCombo.setVisible(false);
		listMatchCombo.setVisible(false);
		listMatchDirectCombo.setVisible(false);
		validerButton.setVisible(false);
		validerDButton.setVisible(false);
		score1Text.setVisible(false);
		score2Text.setVisible(false);
		score1DText.setVisible(false);
		score2DText.setVisible(false);
		scoreLabel.setVisible(false);
		scoreTypeLabel.setVisible(false);
		scoreDLabel.setVisible(false);
		scoreDTypeLabel.setVisible(false);
		phaseDirectLabel.setVisible(false);
		phasePouleLabel.setVisible(false);
		AffichageGlobalLabel.setVisible(false);
	}

	public void Gererletournois() {
		miseAjourAffichage();

		if (direct != null) {
			AffichageGlobalLabel.setVisible(true);
			listMatchDirectCombo.setVisible(true);
			phaseDirectLabel.setVisible(true);
		}
		phasePouleLabel.setVisible(true);
		listPouleCombo.setVisible(true);
		listEquipePouleCombo.setVisible(true);
		listMatchCombo.setVisible(true);

		sauvModifButton.setVisible(false);
		nomEquipeText.setVisible(false);
		nbJoueursText.setVisible(false);
		descriptionText.setVisible(false);
		statsButton.setVisible(false);
		listEquipeCombo.setVisible(false);
		gererEquipeButton.setVisible(false);
	}
}
