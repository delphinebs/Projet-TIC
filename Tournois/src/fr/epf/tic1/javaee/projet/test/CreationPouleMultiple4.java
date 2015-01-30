package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.epf.tic1.javaee.projet.controller.TournoisPouleController;
import fr.epf.tic1.javaee.projet.model.*;

public class CreationPouleMultiple4 {

	@Test
	public void test() {
		TournoisPouleController controller = new TournoisPouleController();
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		for(int i=0; i<8 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisPoule tournoisPoule = new TournoisPoule("TournoisTest", equipes);
		
		controller.start(tournoisPoule);
		
		//Les poules
		ArrayList<Poule> poules = tournoisPoule.getPoules();
		
		if(poules.size()!=2) fail("Mauvais nombre de poules");
		
		for(Poule poule : poules){
			System.out.println("Poule : "+ poule.getNumero());
			
			if(poule.getEquipes().size()!=4) fail("Mauvais nombre d'equipe par poule");
			for(Equipe equipe : poule.getEquipes()){
				System.out.println(" Equipe : "+ equipe);
			}
			
			if(poule.getMatchs().size()!=6) fail("Mauvais nombre de match");
			System.out.println(" Match : ");
			for(Match match : poule.getMatchs()){
				System.out.println("  "+ match);
			}
			System.out.println(poule);
		}
		
		
		
		if(tournoisPoule.getQualifiés().size()!=0) fail("Mauvais nombre de qualifies");

	}

}
