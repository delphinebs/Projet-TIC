package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public abstract class ATournois {
	
	private String nom;
	private ArrayList<Equipe> equipes;	
	

	/*Constructors*/
	//
	//

	public ATournois(){
		nom = "default";//Permet de savoir si une equipe est "null"
		equipes = new ArrayList<>();
	}
	
	public ATournois(String nom){
		this.nom = nom;
		equipes = new ArrayList<>();
	}
	
	public ATournois(String nom, ArrayList<Equipe> equipes){
		this.nom = nom;
		this.equipes = equipes;
	}
	
	
	/*Getter - Setter*/
	//
	//
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(ArrayList<Equipe> equipes) {
		this.equipes = equipes;
	}
}
