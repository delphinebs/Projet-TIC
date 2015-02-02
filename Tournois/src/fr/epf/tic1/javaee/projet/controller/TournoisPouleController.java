package fr.epf.tic1.javaee.projet.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import fr.epf.tic1.javaee.projet.model.*;

public class TournoisPouleController implements ITournoisController {

	public TournoisPouleController(){
		
	}
	
	@Override
	public void start(ATournois tournois) {
		TournoisPoule tournoisPoule = (TournoisPoule) tournois;

		ArrayList<Equipe> equipes = tournoisPoule.getEquipes();

		int nbEquipe = equipes.size();

		switch (nbEquipe % 4) {
		case 0:
			// poules de 4
			creerPoulesCompletes(tournoisPoule);
			break;
		case 1:		
		case 2:
			// 1 ou 2 equipes passages auto => a ameliorer plus tard : poules de
			// 5,6...
			equipes = creerPoulesCompletes(tournoisPoule);
			tournoisPoule.setQualifiés(equipes);
			break;
		case 3:
			// 1 poule de 3
			equipes = creerPoulesCompletes(tournoisPoule);
			Poule poule = new Poule( ((int)nbEquipe/4) + 1);
			poule.setEquipes(equipes);
			PouleController.creerMatchs(poule);
			ArrayList<Poule> poules = tournoisPoule.getPoules();
			poules.add(poule);
			tournoisPoule.setPoules(poules);
			break;
		}
	}

	public static ArrayList<Equipe> creerPoulesCompletes(TournoisPoule tournoisPoule) {
		
		ArrayList<Equipe> equipes = tournoisPoule.getEquipes();
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
				equipes.remove(index);
			}
			poule.setEquipes(equipesPoule);
			PouleController.creerMatchs(poule);
			poules.add(poule);
		}
		tournoisPoule.setPoules(poules);
		return equipes;
	}

	public TournoisDirect finMatch(TournoisPoule tournois, Poule poule, Match match, int score1, int score2){
		match.setScore(score1, score2);
		Equipe[] equipes = match.getEquipes();
		Equipe equipe1 = equipes[0];
		Equipe equipe2 = equipes[1];
		ArrayList<ResultatsEquipe> classement = poule.getClassement();
		ResultatsEquipe res1 = null, res2 = null;

		Iterator<ResultatsEquipe> it = classement.iterator();
		while(it.hasNext()){
			ResultatsEquipe res = (ResultatsEquipe) it.next();

			if(res.getEquipe().equals(equipe1)){
				res1= res;
			}
			else if(res.getEquipe().equals(equipe2)){
				res2 = res;
			}
		}
		
		if(score1==score2){
			res1.setPoints(res1.getPoints()+1);
			res2.setPoints(res2.getPoints()+1);
		}
		else if(score1>score2){
			res1.setPoints(res1.getPoints()+3);
			int dif = score1 - score2;
			res1.setDifferenceDeButs(res1.getDifferenceDeButs()+dif);
			res2.setDifferenceDeButs(res2.getDifferenceDeButs()-dif);
		}
		else if(score1<score2){
			res2.setPoints(res2.getPoints()+3);
			int dif = score2 - score1;
			res1.setDifferenceDeButs(res1.getDifferenceDeButs()-dif);
			res2.setDifferenceDeButs(res2.getDifferenceDeButs()+dif);
		}
		
		if(dernierMatch(poule)){
			ArrayList<Equipe> qualifies = tournois.getQualifiés();
			Collections.sort(classement);
			qualifies.add(classement.get(classement.size()-1).getEquipe());
			qualifies.add(classement.get(classement.size()-2).getEquipe());
			
			ArrayList<Poule> poules = tournois.getPoules();
			
			if(dernierePoule(poules)){
				return phaseFinal(qualifies, tournois.getNom());
			}
		}
		
		return null;//Verif si phase final avec ==null
	}
	
	public TournoisDirect phaseFinal(ArrayList<Equipe> qualifies, String nom){
		TournoisDirect tournois = new TournoisDirect("Phase final "+nom, qualifies);
		return tournois;
	}
	
	public boolean dernierMatch(Poule poule){
		for(Match match : poule.getMatchs()){
			if(!match.getJoue()) return false;
		}
		return true;
	}
	
	public boolean dernierePoule(ArrayList<Poule> poules){
		for(Poule poule : poules){
			if(!dernierMatch(poule)) return false;
		}
		return true;
	}
}
