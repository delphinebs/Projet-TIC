package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;

public class testDirectTournement {

	
	
	//Start tournament
		Equipe equipe1 = new Equipe();
		Equipe equipe2=new Equipe();
		Equipe equipe3=new Equipe();
		Equipe equipe4=new Equipe();
		Equipe equipe5=new Equipe();
		Equipe equipe6=new Equipe();
		ArrayList<Equipe> team =new ArrayList<Equipe>();
		
		
		
		
	@Test
	public void testNotimplemented() {
		fail("Not yet implemented");
	}
	
	
	
	
		@Test
		public void testStartDirectTournamentDistributematchEven() {
			//direct tournament even teams
			team.add(equipe1);
			
			team.add(equipe2);
			team.add(equipe3);
			team.add(equipe4);
			team.add(equipe5);
			team.add(equipe6);
			TournoisDirect tournois = new TournoisDirect();
			TournoisDirectController tournoisController = new TournoisDirectController( tournois);
			tournois.setEquipes(team);
			tournoisController.start();
			
		
			assertEquals(tournoisController.getTournois().getArbre().size(),tournoisController.getTournois().getEquipes().size()/2,3);//2 team= 1 match to play
			
			
			}
		
				@Test
				public void testStartDirectTournamentDistributematchNotEven() {
					//direct tournament even teams
					team.add(equipe1);
					
					team.add(equipe2);
					team.add(equipe3);
					team.add(equipe4);
					team.add(equipe5);
					
					TournoisDirect tournois = new TournoisDirect();
					TournoisDirectController tournoisController = new TournoisDirectController( tournois);
					tournois.setEquipes(team);
					tournoisController.start();
					
				
					assertEquals(tournoisController.getTournois().getArbre().size(),((int)tournoisController.getTournois().getEquipes().size()/2)+1, 3);//5= 1 match to play
					
					
					}
				
				
				@Test
				public void testNotFinishedDirectTournament() {
					//direct tournament 
					team.add(equipe1);
					
					team.add(equipe2);
					team.add(equipe3);
					team.add(equipe4);
					team.add(equipe5);
					
					TournoisDirect tournois = new TournoisDirect();
					TournoisDirectController tournoisController = new TournoisDirectController( tournois);
					tournois.setEquipes(team);
					tournoisController.start();
					assertEquals(tournoisController.finDeTournois(), false);
					
					
					
					}
		
				
				//test finished tournament

}
