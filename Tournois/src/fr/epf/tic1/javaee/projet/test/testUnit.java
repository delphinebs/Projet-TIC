package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.*;

import java.io.Console;

import org.junit.Test;

import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
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
	
	
	//Start tournament
	
	@Test
	public void testStartDirectTournament() {
		//direct tournament
		
		
		ConsoleView console =new ConsoleView();
		console.Launch();
		console.
		console.choixTournois()=2;//to do
		}
	@Test
	public void testStartPoolTournament() {
		
		//to do
		}
	
		
	
	
	
	
	
	
	
}
