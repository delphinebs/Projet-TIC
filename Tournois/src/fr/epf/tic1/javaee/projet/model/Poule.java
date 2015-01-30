package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;

public class Poule {

	private int numero;
	private ArrayList<Match> matchs;
	private ArrayList<Equipe> equipes;
	private ArrayList<ResultatsEquipe> classement;
	
	public Poule(int i) {
		numero = i;
		matchs = new ArrayList<>();
		equipes = new ArrayList<>();
		classement = new ArrayList<>();
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public ArrayList<Match> getMatchs() {
		return matchs;
	}

	public void setMatchs(ArrayList<Match> matchs) {
		this.matchs = matchs;
	}

	public ArrayList<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(ArrayList<Equipe> equipes) {
		this.equipes = equipes;
		for(Equipe equipe : equipes){
			classement.add(new ResultatsEquipe(equipe));
		}
	}

	public ArrayList<ResultatsEquipe> getClassement() {
		return classement;
	}

	public void setClassement(ArrayList<ResultatsEquipe> classement) {
		this.classement = classement;
	}

	@Override
	public String toString() {
		
		String retour;
		
		retour = "__________________________________ \n";
		retour += "           Poule " + numero + "\n";
		retour += "---------------------------------- \n";
		retour += "          ~Equipes~\n";
		retour += "\n";
		for(Equipe equipe : equipes){
			retour += "           "+equipe+"\n";
		}
		retour += "---------------------------------- \n";
		retour += "          ~Match~\n";
		retour += "\n";
		for(Match match : matchs){
			retour += "   "+match+"\n";
		}
		
		
		return retour;
	}
}
