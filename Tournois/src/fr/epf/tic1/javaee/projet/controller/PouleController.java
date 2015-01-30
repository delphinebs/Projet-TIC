package fr.epf.tic1.javaee.projet.controller;

import java.util.ArrayList;

import fr.epf.tic1.javaee.projet.model.*;

public class PouleController {

	public static void creerMatchs(Poule poule){
		
		ArrayList<Equipe> equipes = poule.getEquipes();
		ArrayList<Match> matchs = poule.getMatchs();
		Equipe equipeCouranteI, equipeCouranteJ;
		Match match;
		
		
		for(int i=0; i<(equipes.size()-1) ; i++){
			equipeCouranteI = equipes.get(i);
			for(int j=i+1; j<equipes.size() ; j++){
				equipeCouranteJ = equipes.get(j);
				match = new Match(equipeCouranteI, equipeCouranteJ);
				matchs.add(match);
			}
		}
		
		poule.setMatchs(matchs);
	}
}
