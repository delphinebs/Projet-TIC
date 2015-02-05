package fr.epf.tic1.javaee.projet.view.javafx.Controller;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.GridPane;
import fr.epf.tic1.javaee.projet.controller.TournoisPouleController;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Poule;
import fr.epf.tic1.javaee.projet.model.TournoisPoule;

public class PouleController {
	@FXML
	public Label tournoisLabel;
	@FXML
	public GridPane grid;
	
	private TournoisPoule tournois;
	private TournoisPouleController tournoisController;
	private ArrayList<Label> labels = new ArrayList<>();
	
	public void init(String nom, ArrayList<Equipe> equipes){
		tournois = new TournoisPoule(nom, equipes);
		tournoisController = new TournoisPouleController();
		tournoisController.start(tournois);
		
		Label label = new Label(tournois.getNom());
		label.setUnderline(true);
		grid.add(label,1,1);
		
		ArrayList<Poule> poules = tournois.getPoules();
		String pouleStrg;
		for(int i=0; i<poules.size() ; i++){
			final Poule poule = poules.get(i);
			pouleStrg = "Poule "+poule.getNumero() +"\n\n";
			for(Equipe equipe : poule.getEquipes()){
				pouleStrg += equipe.getNom()+"\n";
			}
			final Label label2 = new Label(pouleStrg);
			label2.setStyle("-fx-border-color: black;");
			grid.add(label2,i+1,2);
			labels.add(label2);
			Button button = new Button("Ouvrir");
			button.setOnMouseClicked(new EventHandler<InputEvent>(){

				@Override
				public void handle(InputEvent event) {
					for(Label label : labels){
							label.setVisible(false);
					}
					//poule;
				}
				
			});
			//Plutot combobox??

			grid.add(button,i+1, 3);
		}
		
		
	}
	
	public void SaisirScore(){
		
	}
}
