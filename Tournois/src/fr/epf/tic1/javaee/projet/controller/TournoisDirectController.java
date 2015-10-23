package fr.epf.tic1.javaee.projet.controller;

import java.util.ArrayList;
import java.util.Random;

import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;

public class TournoisDirectController implements ITournoisController {

	private TournoisDirect tournois;
	
	public TournoisDirectController(TournoisDirect tournois) {
		this.tournois=tournois;
	}
	
	public TournoisDirect getTournois() {
		return tournois;
	}
	
	// Cree l'arbre du tournois ainsi que les matchs du premier tour
	@Override
	public void start() {
		ArrayList<Equipe> equipes = tournois.getEquipes();
		ArrayList<Equipe> equipesTampon = new ArrayList<>();
		ArrayList<Match[]> arbre = tournois.getArbre();

		Match[] matchs = null;

		int nbEquipe = equipes.size();

		Random rand = new Random();
		int index1, index2;

		switch (nbEquipe % 2) {
		case 0:// Cas nombre d'equipe paire => aucun prequalifie
			matchs = new Match[nbEquipe / 2];
			for (int i = 0; i < nbEquipe / 2; i++) {

				index1 = rand.nextInt(equipes.size());
				do {
					index2 = rand.nextInt(equipes.size());
				} while (index1 == index2);

				matchs[i] = new Match(equipes.get(index1), equipes.get(index2));

				equipesTampon.add(equipes.remove(index1));
				if (index1 < index2)
					equipesTampon.add(equipes.remove(index2 - 1));
				else
					equipesTampon.add(equipes.remove(index2));
			}
			arbre.add(matchs);
			break;
		case 1:// Cas nombre d'equipe impaire => 1 prequalifie
			matchs = new Match[((int) nbEquipe / 2) + 1];
			for (int i = 0; i < (int) nbEquipe / 2; i++) {

				index1 = rand.nextInt(equipes.size());
				do {
					index2 = rand.nextInt(equipes.size());
				} while (index1 == index2);

				matchs[i] = new Match(equipes.get(index1), equipes.get(index2));

				equipesTampon.add(equipes.remove(index1));
				if (index1 < index2)
					equipesTampon.add(equipes.remove(index2 - 1));
				else
					equipesTampon.add(equipes.remove(index2));
			}
			equipesTampon.add(equipes.get(0));
			matchs[(int) nbEquipe / 2] = new Match(equipes.get(0), new Equipe());
			matchs[(int) nbEquipe / 2].setScore(-2, -2);// score -2 indique non
														// match mais
														// prequalifie
			arbre.add(matchs);
			break;
		}

		// Calcul nombre etage
		int nbEtageFinal;

		double nbEtage = Math.log10(nbEquipe) / Math.log10(2);

		int nbEtageInt = (int) nbEtage;

		if (nbEtage - nbEtageInt == 0.000)
			nbEtageFinal = nbEtageInt;
		else
			nbEtageFinal = nbEtageInt + 1;

		for (int i = 1; i < (nbEtageFinal); i++) {

			int nbMatch = arbre.get(arbre.size() - 1).length;

			switch (nbMatch % 2) {
			case 0:
				matchs = new Match[nbMatch / 2];
				break;
			case 1:
				matchs = new Match[(int) nbMatch / 2 + 1];
				break;
			}

			arbre.add(matchs);

		}

		tournois.setArbre(arbre);
		tournois.setEquipes(equipesTampon);
	}

	// Utiliser le boolean pour savoir si le tournois est finis
	public boolean finMatch(Match match, int score1,
			int score2) {//return true f the match is finished
		match.setScore(score1, score2);
		if (finTour()) {
			if (finDeTournois())
				return true;
			monterEquipes();
			tournois.setTour(tournois.getTour() + 1);
			return false;
		}
		return false;
	}

	// Verif si le tournois est fini
	private boolean finDeTournois() {//return true if tournament s finished
		if (tournois.getArbre().size() <= tournois.getTour() + 1)
			return true;
		return false;
	}

	// Verif si le tour actuel est fini
	private boolean finTour() {
		ArrayList<Match[]> arbre = tournois.getArbre();
		for (Match match : arbre.get(tournois.getTour())) {
			if (!match.getJoue())
				return false;
		}
		return true;
	}

	// Fais monter les equipes en fonction des resultats du tours
	private void monterEquipes() {
		ArrayList<Match[]> arbre = tournois.getArbre();
		int tour = tournois.getTour();
		
		Match[] matchsCourants = arbre.get(tour);
		Match[] matchsSuivants = arbre.get(tour + 1);

		switch (tour % 2) {
		case 0:
			for (int i = matchsCourants.length - 1; i >= 0; i--) {
				mettreAdroite(quiGagne(matchsCourants[i]), matchsSuivants);
			}
			setDefaultScore(matchsSuivants[0]);
			break;
		case 1:
			for (int i = 0; i < matchsCourants.length; i++) {
				mettreAgauche(quiGagne(matchsCourants[i]), matchsSuivants);
			}
			setDefaultScore(matchsSuivants[matchsSuivants.length - 1]);
			break;
		}
	}

	// Permet de mettre les scores à -2 pour les "faux matchs" (qui permettent
	// de contenir les prequalifies )
	private void setDefaultScore(Match match) {
		if (match.getEquipes()[0].getNom().equals("default")
				|| match.getEquipes()[1].getNom().equals("default")) {
			match.setScore(-2, -2);
		}
	}

	// Verif qui gane le match
	private Equipe quiGagne(Match match) {

		Equipe[] equipes = match.getEquipes();
		if (equipes[0].getNom().equals("default")) {
			return equipes[1];
		} else if (equipes[1].getNom().equals("default")) {
			return equipes[0];
		}

		int[] scores = match.getScore();
		if (scores[0] > scores[1]) {
			return equipes[0];
		} else if (scores[0] < scores[1]) {
			return equipes[1];
		}

		return equipes[0];// execption??
	}

	// Met l'equipe le plus a droite possible (permet de ne pas avoir un meme
	// prequalifie deux fois de suite)
	private void mettreAdroite(Equipe equipe, Match[] matchs) {
		for (int i = matchs.length - 1; i >= 0; i--) {
			if (matchs[i] == null)
				matchs[i] = new Match();
			if (matchs[i].setEquipes(equipe))
				return;
		}
	}

	// Met l'equipe le plus a gauche possible
	private void mettreAgauche(Equipe equipe, Match[] matchs) {
		for (int i = 0; i < matchs.length; i++) {
			if (matchs[i] == null)
				matchs[i] = new Match();
			if (matchs[i].setEquipes(equipe))
				return;
		}
	}

}
