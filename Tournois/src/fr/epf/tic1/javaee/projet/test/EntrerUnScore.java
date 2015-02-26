package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.controller.TournoisPouleController;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.Poule;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;
import fr.epf.tic1.javaee.projet.model.TournoisPoule;

public class EntrerUnScore {

	@Test
	public void entrerScoreSimple() {
		TournoisPouleController controller = new TournoisPouleController();
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		for(int i=0; i<8 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		
		controller.start(tournoisPoule);
		
		Poule poule1 = tournoisPoule.getPoules().get(0);
		
		Match match = poule1.getMatchs().get(4);
		
//		System.out.println(tournoisPoule);
//		System.out.println(poule1);
//		System.out.println(match);
//		
//		for(ResultatsEquipe res : poule1.getClassement()){
//			System.out.println(res);
//		}
//		System.out.println("-----------------");
//		
		assertNull(controller.finMatch(tournoisPoule, poule1, match, 0, 1));
	
//		for(ResultatsEquipe res : poule1.getClassement()){
//			System.out.println(res);
//		}
//		System.out.println("-----------------");
//		Collections.sort(poule1.getClassement());
//		for(ResultatsEquipe res : poule1.getClassement()){
//			System.out.println(res);
//		}
		
		match = poule1.getMatchs().get(0);
		assertNull(controller.finMatch(tournoisPoule, poule1, match, 0, 1));
		match = poule1.getMatchs().get(1);
		assertNull(controller.finMatch(tournoisPoule, poule1, match, 0, 1));
		match = poule1.getMatchs().get(2);
		assertNull(controller.finMatch(tournoisPoule, poule1, match, 0, 1));
		match = poule1.getMatchs().get(3);
		assertNull(controller.finMatch(tournoisPoule, poule1, match, 0, 1));
		match = poule1.getMatchs().get(5);
		assertNull(controller.finMatch(tournoisPoule, poule1, match, 0, 1));
		
		Poule poule2 = tournoisPoule.getPoules().get(1);
		
//		System.out.println(poule2);
//		System.out.println(poule2.getClassement());
		
		match = poule2.getMatchs().get(0);
		assertNull(controller.finMatch(tournoisPoule, poule2, match, 0, 1));
		match = poule2.getMatchs().get(1);
		assertNull(controller.finMatch(tournoisPoule, poule2, match, 0, 1));
		match = poule2.getMatchs().get(2);
		assertNull(controller.finMatch(tournoisPoule, poule2, match, 0, 1));
		match = poule2.getMatchs().get(3);
		assertNull(controller.finMatch(tournoisPoule, poule2, match, 0, 1));
		match = poule2.getMatchs().get(4);
		assertNull(controller.finMatch(tournoisPoule, poule2, match, 0, 1));
		match = poule2.getMatchs().get(5);
		TournoisDirect tournoisDirect = controller.finMatch(tournoisPoule, poule2, match, 0, 1);
		
		TournoisDirectController controller2 = new TournoisDirectController();
		controller2.start(tournoisDirect);
		
//		System.out.println(poule1.getClassement());
//		System.out.println("-----------------");
//		System.out.println(poule2.getClassement());
//		System.out.println("-----------------");
//		System.out.println("-----------------");
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[0];
		assertFalse(controller2.finMatch(tournoisDirect, match, 0, 1));//verfif pas fin de tournois
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[1];
		assertFalse(controller2.finMatch(tournoisDirect, match, 0, 1));
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(1)[0];
		assertTrue(controller2.finMatch(tournoisDirect, match, 0, 1));
		
		//System.out.println(tournoisDirect);
		
		
		equipes = new ArrayList<>();
		
		for(int i=0; i<5 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
	
		tournoisDirect = new TournoisDirect("Nom", equipes);
		
		controller2.start(tournoisDirect);
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[0];
		assertFalse(controller2.finMatch(tournoisDirect, match, 0, 1));//verfif pas fin de tournois
		
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[1];
		assertFalse(controller2.finMatch(tournoisDirect, match, 0, 1));
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(1)[1];
		assertFalse(controller2.finMatch(tournoisDirect, match, 0, 1));

//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(2)[0];
		assertTrue(controller2.finMatch(tournoisDirect, match, 0, 1));
		
//		System.out.println(tournoisDirect);

	}

}
