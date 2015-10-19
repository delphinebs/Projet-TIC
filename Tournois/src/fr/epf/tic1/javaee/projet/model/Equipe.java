package fr.epf.tic1.javaee.projet.model;

import java.util.ArrayList;
import java.util.Collections;

public class Equipe {

	private String nom;
	private int nbJoueur;// non used in the program, set at 0
	private String description;

	/* Constructors */
	//
	//

	public Equipe() {
		nom = "default";
		nbJoueur = 0;
		description = "a renseigner";
	}

	public Equipe(String nom) {
		this.nom = nom;
		nbJoueur = 0;
		description = "a renseigner";
	}

	/* Getter - Setter */
	//
	//

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbJoueur() {
		return nbJoueur;
	}

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public String stats(TournoisDirect direct, TournoisPoule poule) {
		String texte = "Statistiques\n\n";
		if (direct != null && poule != null) {
			return texte + statPouleDirect(direct, poule);
		} else if (direct != null) {
			return texte + statDirect(direct);
		} else if (poule != null) {
			return texte + statPoule(poule);
		} else
			return "Erreur : données manquantes\nRelancez l'application";
	}

	private String statPoule(TournoisPoule tournois) {
		String texte = "";
		Poule pouleCourante = null;

		// Recuperation de la poule
		for (Poule poule : tournois.getPoules()) {
			for (Equipe equipe : poule.getEquipes()) {
				if (equipe.equals(this)) {
					pouleCourante = poule;
					break;
				}
			}
			if (pouleCourante != null)
				break;
		}

		texte += "Poule " + pouleCourante.getNumero() + "\n";

		// Recuperation du classment
		ArrayList<ResultatsEquipe> classement = pouleCourante.getClassement();
		Collections.sort(classement);
		ResultatsEquipe resultat = null;
		int i;
		for (i = 0; i < classement.size(); i++) {
			if (classement.get(i).getEquipe().equals(this)) {
				resultat = classement.get(i);
				break;
			}
		}

		texte += "Classement :" + (classement.size()-i) + "/" + classement.size() + "\n";
		texte += "Nombre de points :" + resultat.getPoints() + "\n";
		texte += "Difference de but :" + resultat.getDifferenceDeButs() + "\n";

		// Recuperation des matchs joues et a jouer
		ArrayList<Match> matchsJoues = new ArrayList<>();
		ArrayList<Match> matchsAJouer = new ArrayList<>();
		int nbBut = 0;
		int nbButPris = 0;

		for (Match match : pouleCourante.getMatchs()) {
			if (match.getEquipes()[0].equals(this)
					|| match.getEquipes()[1].equals(this)) {
				if (match.getScore()[0] == -1) {
					matchsAJouer.add(match);
				} else {
					matchsJoues.add(match);
					if (match.getEquipes()[0].equals(this)) {
						nbBut += match.getScore()[0];
						nbButPris += match.getScore()[1];
					} else {
						nbBut += match.getScore()[1];
						nbButPris += match.getScore()[0];
					}
				}
			}
		}

		texte += "Nombre de but :" + nbBut + "\n";
		texte += "Nombre de but pris :" + nbButPris + "\n";

		texte += "\nMatchs joues\n";

		if (matchsJoues.isEmpty()) {
			texte += "Aucun\n";
		} else {
			for (Match match : matchsJoues) {
				texte += match.toString() + "\n";
			}
		}

		texte += "\nMatchs a jouer\n";

		if (matchsAJouer.isEmpty()) {
			texte += "Aucun\n";
		} else {
			for (Match match : matchsAJouer) {
				texte += match.toString() + "\n";
				;
			}
		}

		return texte;
	}

	private String statDirect(TournoisDirect direct) {
		String texte = "";

		// Recuperation des matchs joues et a jouer
		ArrayList<Match> tours = new ArrayList<>();
		int nbBut = 0;
		int nbButPris = 0;

		for (int i = 0; i < direct.getTour() + 1; i++) {
			Match[] matchs = direct.getArbre().get(i);
			for (Match match : matchs) {
				if (match.getEquipes()[0].equals(this)
						|| match.getEquipes()[1].equals(this)) {
					tours.add(match);
					if (match.getScore()[0] != -1 && match.getScore()[0] != -2) {
						if (match.getEquipes()[0].equals(this)) {
							nbBut += match.getScore()[0];
							nbButPris += match.getScore()[1];
						} else {
							nbBut += match.getScore()[1];
							nbButPris += match.getScore()[0];
						}
					}
				}
			}
		}

		texte += "Tour actuel :" + (direct.getTour()+1) + "\n";

		if (tours.size() == direct.getTour() + 1) {
			texte += "Equipe encore en jeu" + "\n";
		} else {
			texte += "Equipe eliminee" + "\n";
		}

		texte += "Nombre de but :" + nbBut + "\n";
		texte += "Nombre de but pris :" + nbButPris + "\n";

		texte += "\nMatchs joues\n";

		for (int i=0; i<tours.size() ;i++) {
			Match match = tours.get(i);
			if (match.getScore()[0] != -1 && match.getScore()[0] != -2) texte += "Tour "+(i+1)+" : "+match.toString() + "\n";
			else if(match.getScore()[0] == -2) texte += "Tour "+(i+1)+" : "+"Prequalifie" + "\n";
		}

		return texte;
	}

	private String statPouleDirect(TournoisDirect direct, TournoisPoule poule) {
		String texte;
		
		texte = "\nEn phase de poule\n\n";
		texte += statPoule(poule);
		texte += "\nEn phase finale\n\n";
		texte += statDirect(direct);
		
		return texte;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (getClass() != obj.getClass())
			return false;
		return nom.equals(((Equipe) obj).nom);
	}

	@Override
	public String toString() {
		return nom;
	}
}
