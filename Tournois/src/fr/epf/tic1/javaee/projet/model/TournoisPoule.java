package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public class TournoisPoule extends ATournois {

	private ArrayList<Poule> poules;
	private ArrayList<Equipe> qualifi�s;
	
	public ArrayList<Poule> getPoules() {
		return poules;
	}

	public void setPoules(ArrayList<Poule> poules) {
		this.poules = poules;
		qualifi�s = new ArrayList<>();
	}

	public TournoisPoule() {
		super();
		poules = new ArrayList<>();
		qualifi�s = new ArrayList<>();
	}

	public TournoisPoule(String nom) {
		super(nom);
		poules = new ArrayList<>();
		qualifi�s = new ArrayList<>();
	}

	public TournoisPoule(String nom, ArrayList<Equipe> equipes) {
		super(nom, equipes);
		poules = new ArrayList<>();
		qualifi�s = new ArrayList<>();
	}

	public ArrayList<Equipe> getQualifi�s() {
		return qualifi�s;
	}

	public void setQualifi�s(ArrayList<Equipe> qualifi�s) {
		this.qualifi�s = qualifi�s;
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
