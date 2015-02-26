package fr.epf.tic1.javaee.projet.controller;

import fr.epf.tic1.javaee.projet.model.ATournois;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.Poule;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;
import fr.epf.tic1.javaee.projet.model.TournoisPoule;

public interface ITournoisController {

	public void start(ATournois tournois);
	
	//Rajouté pour la mise en place du web service
	public boolean finMatch(TournoisDirect tournois, Match match, int score1,int score2);
	public TournoisDirect finMatch(TournoisPoule tournois, Poule poule, Match match, int score1, int score2);
}
