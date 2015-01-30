package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public class TournoisDirect extends ATournois {

	private ArrayList<Match[]> arbre;
	private int tour;
	
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

	public ArrayList<Match[]> getArbre() {
		return arbre;
	}

	public void setArbre(ArrayList<Match[]> arbre) {
		this.arbre = arbre;
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

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}
	
	

}
