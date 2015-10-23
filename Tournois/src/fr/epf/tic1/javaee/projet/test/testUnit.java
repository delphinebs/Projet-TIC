package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.*;

import java.io.Console;
import java.util.ArrayList;

import org.junit.Test;

import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;
import fr.epf.tic1.javaee.projet.view.console.ConsoleView;

public class testUnit {


	//TEST Create TEAM and start match
	@Test
	public void testCreateMatchNewTeam() {
		Match m= new Match();

		
		assertEquals(m.getJoue(),false);//game not played
		assertNotNull(m.getEquipes());
		assertEquals(m.getEquipes()[0].getNom(), m.getEquipes()[1].getNom(), "default");
		assertEquals(m.getEquipes()[0].getDescription(), m.getEquipes()[1].getDescription(), "a renseigner");
		assertEquals(m.getScore()[1],m.getScore()[0],0);//scores null
		}
	@Test
	public void testCreateMatchOldTeam() {
		Equipe equipe1= new Equipe("Team A");
		equipe1.setDescription("from Oulu");
		
		Equipe equipe2= new Equipe("Team B");
		equipe2.setDescription("from Tampere");
		Match m= new Match();

		
		assertEquals(m.getJoue(),false);//game not played
		
		assertNotNull(m.getEquipes());
		assertEquals(m.getScore()[1],m.getScore()[0],0);//scores null
		
		assertEquals(equipe1.getNom(),"Team A");
		assertEquals(equipe1.getDescription(),"from Oulu");
		assertEquals(equipe2.getNom(),"Team B");
		assertEquals(equipe2.getDescription(),"from Tampere");
	}
	
	
	//to do test exception Namealready taken
	
		
	//Match played, set score
		
	@Test
	public void testMatchSetScore(){
		Equipe equipe1=new Equipe();
		Equipe equipe2=new Equipe();
		
		Match m= new Match(equipe1, equipe2);
		m.setScore(2, 5);//team 2 win, match  played
		assertEquals(2,m.getScore()[0]);
		assertEquals(5,m.getScore()[1]);
		assertEquals(m.getJoue(), true); //matched played when we set scores of the match
		
	}
	
	
	@Test
	public void testFinishedMatch() {
		
		//to do
		}
	
		
	
	
	
	
	
	
	
}
