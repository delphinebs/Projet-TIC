package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public class TournoisDirect extends ATournois {

	private ArrayList<Match[]> arbre;
	private int tour;
	
	
	/*Constructors*/
	//
	//
	
	public TournoisDirect() {
		super();
		arbre = new ArrayList<>();
		tour=0;
	}
	
	public TournoisDirect(String nom) {
		super(nom);
		arbre = new ArrayList<>();
		tour=0;
	}
	
	public TournoisDirect(String nom, ArrayList<Equipe> equipes) {
		super(nom, equipes);
		arbre = new ArrayList<>();
		tour=0;
	}

	
	/*Getter - Setter*/
	//
	//
	
	public Equipe getGagnant(){//fonction to find the winner test TODO
		Match[] match = arbre.get(arbre.size()-1);
		if(match[0].getScore()[0]>match[0].getScore()[1]){
			return match[0].getEquipes()[0];
		}
		else{
			return match[0].getEquipes()[1];
		}
	}
	
	public ArrayList<Match[]> getArbre() {
		return arbre;
	}

	public void setArbre(ArrayList<Match[]> arbre) {
		this.arbre = arbre;
	}
	
	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}
	
	@Override
	public String toString() {
		String retour;
		
		retour = "Tournois\n\n";
		
		for(Match[] matchs : arbre){
			for(Match match : matchs){
				if(match==null) retour += "  "+(char) 164+"  ";
				else retour += " | "+match+" | ";
			}
			retour += "\n";
		}
		
		return retour;
		
	}	

}
