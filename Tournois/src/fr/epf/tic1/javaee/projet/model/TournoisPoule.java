package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public class TournoisPoule extends ATournois {

	private ArrayList<Poule> poules;
	private ArrayList<Equipe> qualifies;
	

	/*Constructors*/
	//
	//

	public TournoisPoule() {
		super();
		poules = new ArrayList<>();
		qualifies = new ArrayList<>();
	}

	public TournoisPoule(String nom) {
		super(nom);
		poules = new ArrayList<>();
		qualifies = new ArrayList<>();
	}

	public TournoisPoule(String nom, ArrayList<Equipe> equipes) {
		super(nom, equipes);
		poules = new ArrayList<>();
		qualifies = new ArrayList<>();
	}
	
	
	/*Getter - Setter*/
	//
	//

	public ArrayList<Poule> getPoules() {
		return poules;
	}

	public void setPoules(ArrayList<Poule> poules) {
		this.poules = poules;
		qualifies = new ArrayList<>();
	}
	
	public ArrayList<Equipe> getQualifies() {
		return qualifies;
	}

	public void setQualifies(ArrayList<Equipe> qualifiés) {
		this.qualifies = qualifiés;
	}
	
	@Override
	public String toString() {
		String retour;
		retour = "Tournois: \n"+this.getNom();
		for(Poule poule : poules){
			retour +=  poule;
		}
		return retour;
	}

}
