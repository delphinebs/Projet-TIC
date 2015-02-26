package fr.epf.tic1.javaee.projet.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import fr.epf.tic1.javaee.projet.model.ATournois;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.Poule;
import fr.epf.tic1.javaee.projet.model.ResultatsEquipe;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;
import fr.epf.tic1.javaee.projet.model.TournoisPoule;

public class TournoisPouleController implements ITournoisController {

	@Override
	public void start(ATournois tournois) {
		TournoisPoule tournoisPoule = (TournoisPoule) tournois;

		ArrayList<Equipe> equipes = tournoisPoule.getEquipes();

		int nbEquipe = equipes.size();

		// Cas nbEquipe==6 => avec nombre algorithme on aurait une poule de 6
		// Il est preferable d'avoir 2 poules de 3
		if (nbEquipe == 6) {
			creerPoulesCompletesDe3(tournoisPoule);
		} else {

			switch (nbEquipe % 4) {
			case 0:// poules de 4
				creerPoulesCompletes(tournoisPoule);
				break;
			case 1:
			case 2:
				// poules de 4 puis distribition des 1 ou 2 equipes restantes,
				// Donc
				// poules de 4 à 5 equipes
				equipes = creerPoulesCompletes(tournoisPoule);
				distribEquipes(tournoisPoule, equipes);
				break;
			case 3:
				// poules de 4 et 1 poule de 3
				equipes = creerPoulesCompletes(tournoisPoule);
				Poule poule = new Poule(((int) nbEquipe / 4) + 1);
				poule.setEquipes(equipes);
				creerMatchs(poule);
				ArrayList<Poule> poules = tournoisPoule.getPoules();
				poules.add(poule);
				tournoisPoule.setPoules(poules);
				break;
			}
		}
	}

	// Gere l'entree d'un score et les consequences de la fin de ce match
	@Override
	public TournoisDirect finMatch(TournoisPoule tournois, Poule poule,
			Match match, int score1, int score2) {
		match.setScore(score1, score2);
		Equipe[] equipes = match.getEquipes();
		Equipe equipe1 = equipes[0];
		Equipe equipe2 = equipes[1];
		ArrayList<ResultatsEquipe> classement = poule.getClassement();
		ResultatsEquipe res1 = null, res2 = null;

		Iterator<ResultatsEquipe> it = classement.iterator();
		while (it.hasNext()) {
			ResultatsEquipe res = (ResultatsEquipe) it.next();

			if (res.getEquipe().equals(equipe1)) {
				res1 = res;
			} else if (res.getEquipe().equals(equipe2)) {
				res2 = res;
			}
		}

		if (score1 == score2) {
			res1.setPoints(res1.getPoints() + 1);
			res2.setPoints(res2.getPoints() + 1);
		} else if (score1 > score2) {
			res1.setPoints(res1.getPoints() + 3);
			int dif = score1 - score2;
			res1.setDifferenceDeButs(res1.getDifferenceDeButs() + dif);
			res2.setDifferenceDeButs(res2.getDifferenceDeButs() - dif);
		} else if (score1 < score2) {
			res2.setPoints(res2.getPoints() + 3);
			int dif = score2 - score1;
			res1.setDifferenceDeButs(res1.getDifferenceDeButs() - dif);
			res2.setDifferenceDeButs(res2.getDifferenceDeButs() + dif);
		}

		Collections.sort(classement);

		if (dernierMatch(poule)) {

			ArrayList<Poule> poules = tournois.getPoules();

			if (dernierePoule(poules)) {
				return phaseFinal(tournois);
			}
		}

		return null;// Verif si phase final avec ==null
	}

	// Ditribu les equipes restantes dans les poules
	private void distribEquipes(TournoisPoule tournoisPoule,
			ArrayList<Equipe> equipes) {
		for (int i = 0; i < equipes.size(); i++) {
			tournoisPoule.getPoules().get(i).getEquipes().add(equipes.get(i));
			tournoisPoule.getPoules().get(i).getMatchs().clear();
			creerMatchs(tournoisPoule.getPoules().get(i));
		}
	}

	// Foncions private car simplement utilisé en interne par le controleur
	// Donc utilisation de fonction uniquement dans un but de clarete du code
	//

	// Cree les matchs pour une poule
	private void creerMatchs(Poule poule) {

		ArrayList<Equipe> equipes = poule.getEquipes();
		ArrayList<Match> matchs = poule.getMatchs();
		Equipe equipeCouranteI, equipeCouranteJ;
		Match match;

		for (int i = 0; i < (equipes.size() - 1); i++) {
			equipeCouranteI = equipes.get(i);
			for (int j = i + 1; j < equipes.size(); j++) {
				equipeCouranteJ = equipes.get(j);
				match = new Match(equipeCouranteI, equipeCouranteJ);
				matchs.add(match);
			}
		}

		poule.setMatchs(matchs);
	}

	// Cree des poules de 3
	private ArrayList<Equipe> creerPoulesCompletesDe3(
			TournoisPoule tournoisPoule) {

		ArrayList<Equipe> equipes = tournoisPoule.getEquipes();
		ArrayList<Equipe> equipesTampon = new ArrayList<>();// on veut pouvoir
															// garder la liste
															// des equipes apres
															// le traitement
		int nbEquipe = equipes.size();
		int nbPoules = (int) nbEquipe / 3;

		ArrayList<Poule> poules = new ArrayList<>();
		ArrayList<Equipe> equipesPoule;
		Random rand = new Random();
		int index;

		for (int i = 0; i < nbPoules; i++) {
			equipesPoule = new ArrayList<>();
			Poule poule = new Poule(i + 1);
			for (int j = 0; j < 3; j++) {
				index = rand.nextInt(equipes.size());
				equipesPoule.add(equipes.get(index));
				equipesTampon.add(equipes.remove(index));
			}
			poule.setEquipes(equipesPoule);
			creerMatchs(poule);
			poules.add(poule);
		}
		tournoisPoule.setPoules(poules);

		equipesTampon.addAll(equipes);
		tournoisPoule.setEquipes(equipesTampon);
		return equipes;
	}

	// Cree des poules de 4
	private ArrayList<Equipe> creerPoulesCompletes(TournoisPoule tournoisPoule) {

		ArrayList<Equipe> equipes = tournoisPoule.getEquipes();
		ArrayList<Equipe> equipesTampon = new ArrayList<>();// on veut pouvoir
															// garder la liste
															// des equipes apres
															// le traitement
		int nbEquipe = equipes.size();
		int nbPoules = (int) nbEquipe / 4;

		ArrayList<Poule> poules = new ArrayList<>();
		ArrayList<Equipe> equipesPoule;
		Random rand = new Random();
		int index;

		for (int i = 0; i < nbPoules; i++) {
			equipesPoule = new ArrayList<>();
			Poule poule = new Poule(i + 1);
			for (int j = 0; j < 4; j++) {
				index = rand.nextInt(equipes.size());
				equipesPoule.add(equipes.get(index));
				equipesTampon.add(equipes.remove(index));
			}
			poule.setEquipes(equipesPoule);
			creerMatchs(poule);
			poules.add(poule);
		}
		tournoisPoule.setPoules(poules);

		equipesTampon.addAll(equipes);
		tournoisPoule.setEquipes(equipesTampon);
		return equipes;
	}

	// Passage en phase d'elimination directe
	private TournoisDirect phaseFinal(TournoisPoule tournois) {

		ArrayList<Equipe> qualifies = tournois.getQualifies();
		ArrayList<Poule> poules = tournois.getPoules();
		ArrayList<ResultatsEquipe> classement;

		for (Poule poule : poules) {
			classement = poule.getClassement();
			Collections.sort(classement);
			qualifies.add(classement.get(classement.size() - 1).getEquipe());
			qualifies.add(classement.get(classement.size() - 2).getEquipe());

		}
		TournoisDirect tournoisDirect = new TournoisDirect("Phase final "
				+ tournois.getNom(), qualifies);
		return tournoisDirect;
	}

	// Dis si le dernier match de cette poule a ete joue
	private boolean dernierMatch(Poule poule) {
		for (Match match : poule.getMatchs()) {
			if (!match.getJoue())
				return false;
		}
		return true;
	}

	// Dis si toute les poules sont terminees
	private boolean dernierePoule(ArrayList<Poule> poules) {
		for (Poule poule : poules) {
			if (!dernierMatch(poule))
				return false;
		}
		return true;
	}

	@Override
	public boolean finMatch(TournoisDirect tournois, Match match, int score1, int score2) {
		// do nothing
		return false;
	}
}
