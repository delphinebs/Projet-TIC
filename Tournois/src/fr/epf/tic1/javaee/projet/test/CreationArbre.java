package fr.epf.tic1.javaee.projet.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.model.*;

public class CreationArbre {

	@Test
	public void test() {
		TournoisDirectController controller = new TournoisDirectController();
		ArrayList<Equipe> equipes = new ArrayList<>();
	
		for(int i=0; i<13 ; i++){
			equipes.add(new Equipe("Equipe"+(i+1)));
		}
		
		TournoisDirect tournoisDirect = new TournoisDirect("TournoisTest", equipes);
		
		controller.start(tournoisDirect);
		
		ArrayList<Match[]> arbre = tournoisDirect.getArbre();
		
		System.out.println("Nombre d'etage: " +arbre.size());
		if(arbre.size()!=4) fail("Mauvais nombre d'etage");
		
		
		//Verif visuel
		for(Match[] matchs : arbre){
			System.out.println("Etage");
			for(Match match : matchs){
				System.out.println(match);
			}
		}
		
		System.out.println(tournoisDirect);
		
	}

}
