package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public class TournoisPoule extends ATournois {

	private ArrayList<Poule> poules;
	private ArrayList<Equipe> qualifiés;
	
	public ArrayList<Poule> getPoules() {
		return poules;
	}

	public void setPoules(ArrayList<Poule> poules) {
		this.poules = poules;
		qualifiés = new ArrayList<>();
	}

	public TournoisPoule() {
		super();
		poules = new ArrayList<>();
		qualifiés = new ArrayList<>();
	}

	public TournoisPoule(String nom) {
		super(nom);
		poules = new ArrayList<>();
		qualifiés = new ArrayList<>();
	}

	public TournoisPoule(String nom, ArrayList<Equipe> equipes) {
		super(nom, equipes);
		poules = new ArrayList<>();
		qualifiés = new ArrayList<>();
	}

	public ArrayList<Equipe> getQualifiés() {
		return qualifiés;
	}

	public void setQualifiés(ArrayList<Equipe> qualifiés) {
		this.qualifiés = qualifiés;
	}
	
	@Override
	public String toString() {
		String retour;
		retour = "Ensemble des poules:\n";
		for(Poule poule : poules){
			retour += "Poule " + poule.getNumero() + "\n";
		}
		return retour;
	}

}
