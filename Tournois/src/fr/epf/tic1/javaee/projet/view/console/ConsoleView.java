package fr.epf.tic1.javaee.projet.view.console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.controller.TournoisPouleController;
import fr.epf.tic1.javaee.projet.model.*;

public class ConsoleView {

	public int choixTournois() {

		System.out.println("----Choix du tournois----");
		System.out.println("1 : Commencer un tournois avec poules");
		System.out.println("2 : Commencer un tournois sans poules");
		System.out.println("0 : Quitter");
		System.out.println("-------------------------");
		System.out.println("Touches autorisées : 1, 2, 0");

		int commande = 0;
		Scanner sc = new Scanner(System.in);
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return choixTournois();
			}
		}

		switch (commande) {
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

	public String choixNom(String type) {

		System.out.println("Tapez le nom de votre " + type + " :");// Restreindre
																	// taille?

		Scanner sc = new Scanner(System.in);
		String nom;
		nom = sc.nextLine();

		switch (validerNom(nom)) {
		case 1:
			return nom;
		case 2:
			return choixNom(type);
		default:
			return choixNom(type);
		}

	}

	private int validerNom(String nom) {
		System.out.println("Valider le nom : \"" + nom + "\" ?");
		System.out.println("1. oui");
		System.out.println("2. non");
		System.out.println("Touches autorisées : 1, 2");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return validerNom(nom);
			}
		}

		switch (commande) {
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

		while (encore) {

			System.out.println("\n\n --Equipe--");
			for (Equipe equipe : equipes) {
				System.out.println(equipe);
			}
			System.out.println("-----");
			switch (actionDonnerEquipe()) {
			case 1:
				equipes.add(new Equipe(choixNom("equipe")));
				break;
			case 2:
				supprimerEquipe(equipes);
				break;
			case 3:
				if (equipes.size() < 3) {
					System.out.println("Il faut au moins 3 equipes!");
				} else {
					encore = false;
				}
				break;
			}
		}

		return equipes;
	}

	public void menuEnCoursGeneral(ATournois tournois) {

		if (tournois instanceof TournoisDirect) {
			TournoisDirectController controller = new TournoisDirectController();
			controller.start(tournois);
			menuDirect((TournoisDirect) tournois, controller);
		} else {
			TournoisPouleController controller = new TournoisPouleController();
			controller.start(tournois);
			TournoisDirect tournoisDirect = menuPoule((TournoisPoule) tournois,
					controller);
			menuEnCoursGeneral(tournoisDirect);
		}

	}

	private void menuDirect(TournoisDirect tournois, TournoisDirectController controller) {
		boolean fin = false;
		
		while (!fin) {

			switch (actionTournois()) {
			case 1:
				
				if (finMatch(tournois, controller)) {
					finDirect(tournois);
					fin = true;
				}
				break;
			case 2:
				System.out.println(tournois);
				break;
			case 3:
				System.exit(0);// Quitter programmme
			}

		}

	}
	
	private TournoisDirect menuPoule(TournoisPoule tournois, TournoisPouleController controller) {

		boolean fin = false;
		TournoisDirect tournoisDirect = null;

		while (!fin) {

			switch (actionTournois()) {
			case 1:
				tournoisDirect = finMatch(tournois, controller);
				if (tournoisDirect != null) {
					finPoule();
					fin = true;
				}
				break;
			case 2:
				System.out.println(tournois);
				break;
			case 3:
				System.exit(0);// Quitter programmme
			}

		}

		return tournoisDirect;
	}

	private void finDirect(TournoisDirect tournois){
		System.out.println("Le tournois est fini");
		System.out.println(tournois);
	}
	
	private void finPoule(){
		System.out.println("La phase de poule est terminée!");
	}
	
	private TournoisDirect finMatch(TournoisPoule tournois, TournoisPouleController controller) {
		ArrayList<Poule> poules = tournois.getPoules();
		Poule poule = choixPoule(poules);
		Match match = choixMatch(poule.getMatchs());
		int[] scores = donnerScores(match);
		return controller.finMatch(tournois, poule, match, scores[0], scores[1]);
	}
	
	private boolean finMatch(TournoisDirect tournois, TournoisDirectController controller) {
		Match match = choixMatch(tournois.getArbre().get(tournois.getTour()));
		int[] scores = donnerScores(match);
		return controller.finMatch(tournois, match, scores[0], scores[1]);
	}

	private int[] donnerScores(Match match) {

		int[] scores = new int[2];
		Equipe[] equipes = match.getEquipes();

		for (int i=0; i<2 ; i++){
			
			scores[i] = donnerScore(equipes[i]);
		
		}

		return scores;
	}
	
	private int donnerScore(Equipe equipe){
		
		System.out.println("\n\nScore Equipe " + equipe + " :");
		
		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return donnerScore(equipe);
			}
		}

		if (commande < 0)
			return donnerScore(equipe);

		return commande;
		
	}

	private Match choixMatch(ArrayList<Match> matchs) {
		for (int i = 0; i < matchs.size(); i++) {
			System.out.println((i + 1) + ". " + matchs.get(i));
		}
		System.out.println("Entrez le numero du match selectionnee:");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return choixMatch(matchs);
			}
		}

		if (commande <= 0 || commande > matchs.size())
			return choixMatch(matchs);

		return matchs.get(commande-1);
	}

	private Match choixMatch(Match[] matchs) {
		for (int i = 0; i < matchs.length; i++) {
			if(matchs[i].getScore()[0]!=-2) System.out.println((i + 1) + ". " + matchs[i]);
		}
		System.out.println("Entrez le numero du match selectionnee:");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return choixMatch(matchs);
			}
		}

		if (commande <= 0 || commande > matchs.length)
			return choixMatch(matchs);

		return matchs[commande-1];
	}

	
	private Poule choixPoule(ArrayList<Poule> poules) {
		for (int i = 0; i < poules.size(); i++) {
			System.out.println((i + 1) + ". " + poules.get(i));
		}
		System.out.println("Entrez le numero de la poule selectionnee:");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return choixPoule(poules);
			}
		}

		if (commande <= 0 || commande > poules.size())
			return choixPoule(poules);

		return poules.get(commande-1);

	}

	private void supprimerEquipe(ArrayList<Equipe> equipes) {
		equipes.remove(choixEquipe(equipes) - 1);
	}

	private int choixEquipe(ArrayList<Equipe> equipes) {
		for (int i = 0; i < equipes.size(); i++) {
			System.out.println((i + 1) + ". " + equipes.get(i));
		}
		System.out.println("Entrez le numero de l'equipe selectionnee:");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return choixEquipe(equipes);
			}
		}

		if (commande <= 0 || commande > equipes.size())
			return choixEquipe(equipes);

		return commande-1;

	}

	private int actionDonnerEquipe() {

		System.out.println("1. Ajouter une equipe");
		System.out.println("2. Supprimer une equipe");
		System.out.println("3. Fin du choix des equipes");
		System.out.println("Touches autorisées : 1, 2, 3");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return actionDonnerEquipe();
			}
		}

		switch (commande) {
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

	private int actionTournois() {

		System.out.println("1. Ajouter un score");
		System.out.println("2. Afficher le tournois");
		System.out.println("3. Quitter");
		System.out.println("Touches autorisées : 1, 2, 3");

		Scanner sc = new Scanner(System.in);
		int commande = 0;
		boolean valide = false;

		while (!valide) {
			try {
				commande = sc.nextInt();
				valide = true;
			} catch (InputMismatchException ime) {
				return actionDonnerEquipe();
			}
		}

		switch (commande) {
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
