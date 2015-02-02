package fr.epf.tic1.javaee.projet.view.console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import fr.epf.tic1.javaee.projet.model.Equipe;

public class ConsoleView {

	public int choixTournois(){
		
		System.out.println("----Choix du tournois----");
		System.out.println("1 : Commencer un tournois avec poules");
		System.out.println("2 : Commencer un tournois sans poules");
		System.out.println("0 : Quitter");
		System.out.println("-------------------------");
		System.out.println("Touches autorisées : 1, 2, 0");
		
		int commande = 0;
		Scanner sc = new Scanner(System.in);
		boolean valide = false;
		
		while(!valide){
		try{
		commande = sc.nextInt();
		valide = true;
		}catch(InputMismatchException ime){
			return choixTournois();
		}
		}
		
		switch(commande){
		case 1:
			return 1;
		case 2:
			return 2;
		case 0:
			return 0;
		default:
			return choixTournois();	
		}
	
	}
	
	public String choixNom(String type){
		
		System.out.println("Tapez le nom de votre "+ type +" :");//Restreindre taille?
		
		Scanner sc = new Scanner(System.in);
		String nom;
		nom = sc.nextLine();
		
			switch(validerNom(nom)){
			case 1:
				return nom;
			case 2:
				return choixNom(type);
			default:
				return choixNom(type);	
			}
			
	}
	
	private int validerNom(String nom){
		
		System.out.println("Valider le nom : \"" + nom + "\" ?");
		System.out.println("1. oui");
		System.out.println("2. non");
		System.out.println("Touches autorisées : 1, 2");
		
		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;
		
		while(!valide){
			try{
			commande = sc.nextInt();
			valide = true;
			}catch(InputMismatchException ime){
				return validerNom(nom);
			}
			}
			
			switch(commande){
			case 1:
				return 1;
			case 2:
				return 2;
			default:
				return validerNom(nom);	
			}
	}

	public ArrayList<Equipe> donnerEquipes() {
		ArrayList<Equipe> equipes = new ArrayList<>();
		
		equipes.add(new Equipe(choixNom("equipe")));
		
		boolean encore = true;
		
		while(encore){
			
			System.out.println("\n\n --Equipe--");
			for(Equipe equipe : equipes){
				System.out.println(equipe);
			}
			System.out.println("-----");
			switch(actionDonnerEquipe()){
			case 1:
				equipes.add(new Equipe(choixNom("equipe")));
				break;
			case 2:
				supprimerEquipe(equipes);
				break;
			case 3:
				encore = false;
				break;
			}
		}
		
		return equipes;
	}
	
	private void supprimerEquipe(ArrayList<Equipe> equipes){
		equipes.remove(choixEquipe(equipes)-1);
	}
	
	private int choixEquipe(ArrayList<Equipe> equipes){
		for(int i=0 ; i<equipes.size() ; i++){
			System.out.println( (i+1) +". "+equipes.get(i));
		}
		System.out.println("Entrez le numero de l'equipe selectionnee:");
		
		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;
		
		while(!valide){
			try{
			commande = sc.nextInt();
			valide = true;
			}catch(InputMismatchException ime){
				return choixEquipe(equipes);
			}
			}
			
		if(commande<=0 || commande>equipes.size()) return choixEquipe(equipes);
			
		return commande;
		
	}
	
	private int actionDonnerEquipe(){
		
		System.out.println("1. Ajouter une equipe");
		System.out.println("2. Supprimer une equipe");
		System.out.println("3. Fin du choix des equipes");
		System.out.println("Touches autorisées : 1, 2, 3");
		
		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;
		
		while(!valide){
			try{
			commande = sc.nextInt();
			valide = true;
			}catch(InputMismatchException ime){
				return actionDonnerEquipe();
			}
			}
			
			switch(commande){
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 3;
			default:
				return actionDonnerEquipe();	
			}
	}
}
