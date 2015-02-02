package fr.epf.tic1.javaee.projet.controller;

import java.util.ArrayList;
import java.util.Random;

import fr.epf.tic1.javaee.projet.model.ATournois;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;

public class TournoisDirectController implements ITournoisController {

	@Override
	public void start(ATournois tournois) {
		TournoisDirect tournoisDirect = (TournoisDirect) tournois;
		creerArbre(tournoisDirect);
	}

	public void creerArbre(TournoisDirect tournois) {
		ArrayList<Equipe> equipes = tournois.getEquipes();
		ArrayList<Match[]> arbre = tournois.getArbre();

		Match[] matchs = null;

		int nbEquipe = equipes.size();

		Random rand = new Random();
		int index1, index2;

		switch (nbEquipe % 2) {
		case 0:
			matchs = new Match[nbEquipe / 2];
			for (int i = 0; i < nbEquipe / 2; i++) {

				index1 = rand.nextInt(equipes.size());
				do {
					index2 = rand.nextInt(equipes.size());
				} while (index1 == index2);

				matchs[i] = new Match(equipes.get(index1), equipes.get(index2));

				equipes.remove(index1);
				if(index1<index2) equipes.remove(index2-1);
				else equipes.remove(index2);
			}
			arbre.add(matchs);
			break;
		case 1:
			matchs = new Match[((int) nbEquipe / 2) + 1];
			for (int i = 0; i < (int) nbEquipe / 2; i++) {

				index1 = rand.nextInt(equipes.size());
				do {
					index2 = rand.nextInt(equipes.size());
				} while (index1 == index2);

				matchs[i] = new Match(equipes.get(index1), equipes.get(index2));

				equipes.remove(index1);
				if(index1<index2) equipes.remove(index2-1);
				else equipes.remove(index2);
			}
			matchs[(int) nbEquipe / 2] = new Match(equipes.get(0), new Equipe());
			arbre.add(matchs);
			break;
		}

		// Calcul nombre etage
		int nbEtageFinal;

		double nbEtage = Math.log10(nbEquipe) / Math.log10(2);

		int nbEtageInt = (int) nbEtage;

		if (nbEtage - nbEtageInt == 0.000) nbEtageFinal = nbEtageInt;
		else nbEtageFinal = nbEtageInt + 1;

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

	}

	//Utiliser le boolean pour savoir si le tournois est finis et si il faut appeler fin(tournois)
	public boolean finMatch(TournoisDirect tournois,Match match, int score1, int score2){
		match.setScore(score1,score2);//Verif si score=score => impossible
		ArrayList<Match[]> arbre = tournois.getArbre();
		
		if(finTour(arbre, tournois.getTour())){
			if(finDeTournois(tournois)) return true;
			monterEquipes(arbre, tournois.getTour());
			tournois.setTour(tournois.getTour()+1);
			return false;
		}
		return false;
	}
	
	public void fin(TournoisDirect tournois){
		//TODO fin de partie ou pas, necessaire?
	}
	
	public boolean finDeTournois(TournoisDirect tournois){
		if(tournois.getArbre().size()<=tournois.getTour()+1) return true;
		return false;
	}
	
	public boolean finTour(ArrayList<Match[]> arbre, int tour){
		for(Match match : arbre.get(tour)){
			if(!match.getJoue()) return false;
		}
		return true;
	}
	
	public void monterEquipes(ArrayList<Match[]> arbre, int tour){
		Match[] matchsCourants = arbre.get(tour);
		Match[] matchsSuivants = arbre.get(tour+1);
		
		switch(tour%2){
		case 0:
			for(int i=matchsCourants.length-1 ; i>=0 ; i--){
				mettreAdroite(quiGagne(matchsCourants[i]), matchsSuivants);
			}
			break;
		case 1:
			for(int i=0 ; i<matchsCourants.length ; i++){
				mettreAgauche(quiGagne(matchsCourants[i]), matchsSuivants);
			}
			break;
		}
	}
	
	public Equipe quiGagne(Match match){
		
		Equipe[] equipes = match.getEquipes();
		if(equipes[0].getNom().equals("default")){
			return equipes[1];
		}
		else if(equipes[1].getNom().equals("default")){
			return equipes[0];
		}
		
		
		int[] scores = match.getScore();
		if(scores[0]>scores[1]){
			return equipes[0];
		}
		else if(scores[0]<scores[1]){
			return equipes[1];
		}
		
		return equipes[0];//execption??
	}

	public void mettreAdroite(Equipe equipe, Match[] matchs){
		for(int i=matchs.length-1 ; i>=0 ; i--){
			if(matchs[i]==null) matchs[i] = new Match();
			if(matchs[i].setEquipes(equipe)) return;
		}
	}
	
	public void mettreAgauche(Equipe equipe, Match[] matchs){
		for(int i=0 ; i<matchs.length ; i++){
			if(matchs[i]==null) matchs[i] = new Match();
			if(matchs[i].setEquipes(equipe)) return;
		}
	}
}
