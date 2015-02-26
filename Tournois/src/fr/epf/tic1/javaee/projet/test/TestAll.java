package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.assertEquals;
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

public class TestAll {

	@Test
	public void creationPouleMultiple4() {

		
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		//Creation de 8 equipes => 2 poules de 4 equipes
		for(int i=0; i<8 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		TournoisPouleController controller = new TournoisPouleController(tournoisPoule);
		controller.start();
		
		ArrayList<Poule> poules = tournoisPoule.getPoules();
		
		assertEquals("Mauvais nombre de poule",poules.size(),2);
		
		for(Poule poule : poules){
			
//			System.out.println("Poule : "+ poule.getNumero());
			
			assertEquals("Mauvais nombre d'equipe par poule",poule.getEquipes().size(),4);
			
//			for(Equipe equipe : poule.getEquipes()){
//				System.out.println(" Equipe : "+ equipe);
//			}
			
			assertEquals("Mauvais nombre d'equipe par poule",poule.getMatchs().size(),6);
			
//			System.out.println(" Match : ");
//			for(Match match : poule.getMatchs()){
//				System.out.println("  "+ match);
//			}
//			System.out.println(poule);
		
		}
	}

	@Test
	public void creationPoule3Trop() {
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		//11 equipes => 2 poules, une de 3 equipes et 2 de 4 equipes
		for(int i=0; i<11 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		TournoisPouleController controller = new TournoisPouleController(tournoisPoule);
		controller.start();
		
		ArrayList<Poule> poules = tournoisPoule.getPoules();
		
		assertEquals("Mauvais nombre de poules",poules.size(),3);
		
		for(Poule poule : poules){
			
//			System.out.println("Poule : "+ poule.getNumero());
			
			if(poule.getNumero()==3){
				assertEquals("Mauvais nombre d'equipe par poule",poule.getEquipes().size(),3);
				assertEquals("Mauvais nombre de match",poule.getMatchs().size(),3);
			}else{
				assertEquals("Mauvais nombre d'equipe par poule",poule.getEquipes().size(),4);
				assertEquals("Mauvais nombre de match",poule.getMatchs().size(),6);
			}
			
//			for(Equipe equipe : poule.getEquipes()){
//				System.out.println(" Equipe : "+ equipe);
//			}
			
//			System.out.println(" Match : ");
//			for(Match match : poule.getMatchs()){
//				System.out.println("  "+ match);
//			}
			
		}
}

	@Test
	public void creationPoule2Trop() {
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		//10 equipes => 2 poules de 5
		for(int i=0; i<10 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		TournoisPouleController controller = new TournoisPouleController(tournoisPoule);
		controller.start();
		
		ArrayList<Poule> poules = tournoisPoule.getPoules();
		
		assertEquals("Mauvais nombre de poules",poules.size(),2);
		
		for(Poule poule : poules){
			
//			System.out.println("Poule : "+ poule.getNumero());
			
			assertEquals("Mauvais nombre d'equipe par poule",poule.getEquipes().size(),5);
			
//			for(Equipe equipe : poule.getEquipes()){
//				System.out.println(" Equipe : "+ equipe);
//			}
			
			assertEquals("Mauvais nombre de match",poule.getMatchs().size(),10);
			
//			System.out.println(" Match : ");
//			for(Match match : poule.getMatchs()){
//				System.out.println("  "+ match);
//			}
			
		}
	}

	@Test
	public void creationPoule1Trop() {
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		//9 equipes => 1 poule de 4 et 1 poule de 5
		for(int i=0; i<9 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		TournoisPouleController controller = new TournoisPouleController(tournoisPoule);
		controller.start();
		
		ArrayList<Poule> poules = tournoisPoule.getPoules();
		
		assertEquals("Mauvais nombre de poules",poules.size(),2);
		assertEquals("Mauvais nombre d'equipe par poule",poules.get(0).getEquipes().size(),5);
		assertEquals("Mauvais nombre de match",poules.get(0).getMatchs().size(),10);
		assertEquals("Mauvais nombre d'equipe par poule",poules.get(1).getEquipes().size(),4);
		assertEquals("Mauvais nombre de match",poules.get(1).getMatchs().size(),6);
		
//		for(Poule poule : poules){
//			System.out.println("Poule : "+ poule.getNumero());
//			for(Equipe equipe : poule.getEquipes()){
//				System.out.println(" Equipe : "+ equipe);
//			}
//			System.out.println(" Match : ");
//			for(Match match : poule.getMatchs()){
//				System.out.println("  "+ match);
//			}
//		}
		
	}
	
	@Test
	public void creationPoule6Equipes() {
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		//6 equipes => 2 poules de 3
		for(int i=0; i<6 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		TournoisPouleController controller = new TournoisPouleController(tournoisPoule);
		controller.start();
		
		ArrayList<Poule> poules = tournoisPoule.getPoules();

		
		for(Poule poule : poules){
			
//			System.out.println("Poule : "+ poule.getNumero());
			
			assertEquals("Mauvais nombre d'equipe par poule",poule.getEquipes().size(),3);
			
//			for(Equipe equipe : poule.getEquipes()){
//				System.out.println(" Equipe : "+ equipe);
//			}
			
			assertEquals("Mauvais nombre de match",poule.getMatchs().size(),3);
			
//			System.out.println(" Match : ");
//			for(Match match : poule.getMatchs()){
//				System.out.println("  "+ match);
//			}
		}		
	}
	
	@Test
	public void creationArbre() {
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		//Creation de 13 equipes
		for(int i=0; i<13 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisDirect tournoisDirect= new TournoisDirect("TournoisTest", equipes);
		TournoisDirectController controller = new TournoisDirectController(tournoisDirect);
		controller.start();
		
		ArrayList<Match[]> arbre = tournoisDirect.getArbre();
		
		//Tests
		assertEquals("Mauvais nombre d'etage",4,arbre.size());
		assertEquals("Mauvais nombre de matchs au premier tour",7,arbre.get(0).length);
		assertEquals("Pas d'equipe prequalifie (score different de -2)",-2,arbre.get(0)[6].getScore()[0]);
		assertEquals("Pas d'equipe prequalifie (deux equipes dans un 'faux match')","default",arbre.get(0)[6].getEquipes()[1].getNom());
		
		//
		///Verif visuelle
		
		/*System.out.println("Nombre d'etage: " +arbre.size());
		for(Match[] matchs : arbre){
			System.out.println("Etage");
			for(Match match : matchs){
				System.out.println(match);
			}
		}
		
		System.out.println(tournoisDirect);
		*/
		
	}

	@Test
	public void entrerScoreSimple() {
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		for(int i=0; i<8 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		TournoisPouleController controller = new TournoisPouleController(tournoisPoule);
		controller.start();
		
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
		assertNull(controller.finMatch(poule1, match, 0, 1));
	
//		for(ResultatsEquipe res : poule1.getClassement()){
//			System.out.println(res);
//		}
//		System.out.println("-----------------");
//		Collections.sort(poule1.getClassement());
//		for(ResultatsEquipe res : poule1.getClassement()){
//			System.out.println(res);
//		}
		
		match = poule1.getMatchs().get(0);
		assertNull(controller.finMatch(poule1, match, 0, 1));
		match = poule1.getMatchs().get(1);
		assertNull(controller.finMatch(poule1, match, 0, 1));
		match = poule1.getMatchs().get(2);
		assertNull(controller.finMatch(poule1, match, 0, 1));
		match = poule1.getMatchs().get(3);
		assertNull(controller.finMatch(poule1, match, 0, 1));
		match = poule1.getMatchs().get(5);
		assertNull(controller.finMatch(poule1, match, 0, 1));
		
		Poule poule2 = tournoisPoule.getPoules().get(1);
		
//		System.out.println(poule2);
//		System.out.println(poule2.getClassement());
		
		match = poule2.getMatchs().get(0);
		assertNull(controller.finMatch(poule2, match, 0, 1));
		match = poule2.getMatchs().get(1);
		assertNull(controller.finMatch(poule2, match, 0, 1));
		match = poule2.getMatchs().get(2);
		assertNull(controller.finMatch(poule2, match, 0, 1));
		match = poule2.getMatchs().get(3);
		assertNull(controller.finMatch(poule2, match, 0, 1));
		match = poule2.getMatchs().get(4);
		assertNull(controller.finMatch(poule2, match, 0, 1));
		match = poule2.getMatchs().get(5);
		
		TournoisDirect tournoisDirect = controller.finMatch(poule2, match, 0, 1);
		TournoisDirectController controller2 = new TournoisDirectController(tournoisDirect);
		controller2.start();
		
//		System.out.println(poule1.getClassement());
//		System.out.println("-----------------");
//		System.out.println(poule2.getClassement());
//		System.out.println("-----------------");
//		System.out.println("-----------------");
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[0];
		assertFalse(controller2.finMatch(match, 0, 1));//verfif pas fin de tournois
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[1];
		assertFalse(controller2.finMatch(match, 0, 1));
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(1)[0];
		assertTrue(controller2.finMatch(match, 0, 1));
		
		//System.out.println(tournoisDirect);
		
		
		equipes = new ArrayList<>();
		
		for(int i=0; i<5 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
	
		tournoisDirect = new TournoisDirect("Nom", equipes);
		controller2 = new TournoisDirectController(tournoisDirect);
		controller2.start();
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[0];
		assertFalse(controller2.finMatch(match, 0, 1));//verfif pas fin de tournois
		
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(0)[1];
		assertFalse(controller2.finMatch(match, 0, 1));
		
//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(1)[1];
		assertFalse(controller2.finMatch(match, 0, 1));

//		System.out.println(tournoisDirect);
		
		match = tournoisDirect.getArbre().get(2)[0];
		assertTrue(controller2.finMatch(match, 0, 1));
		
//		System.out.println(tournoisDirect);

	}

}
