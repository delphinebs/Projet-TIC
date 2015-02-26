package fr.epf.tic1.javaee.projet.view.console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.epf.tic1.javaee.projet.controller.TournoisDirectController;
import fr.epf.tic1.javaee.projet.controller.TournoisPouleController;
import fr.epf.tic1.javaee.projet.execptions.NomDejaPrisExecption;
import fr.epf.tic1.javaee.projet.model.ATournois;
import fr.epf.tic1.javaee.projet.model.Equipe;
import fr.epf.tic1.javaee.projet.model.Match;
import fr.epf.tic1.javaee.projet.model.Poule;
import fr.epf.tic1.javaee.projet.model.TournoisDirect;
import fr.epf.tic1.javaee.projet.model.TournoisPoule;

public class ConsoleView {

	
	public void Launch(){
		ATournois tournois;
		ConsoleView console =new ConsoleView();
		
		//Choix du type de tournois
		switch(console.choixTournois()){
		case 1:
			tournois = new TournoisPoule();
			tournois.setNom(console.choixNom("tournois"));
			tournois.setEquipes(console.choixNbEquipes());
			menuEnCoursGeneral(tournois);
			break;
		case 2:
			tournois = new TournoisDirect();
			tournois.setNom(console.choixNom("tournois"));
			tournois.setEquipes(console.choixNbEquipes());
			menuEnCoursGeneral(tournois);
			break;
		case 0:
			System.exit(0);
		}
	}
	

	private void menuEnCoursGeneral(ATournois tournois) {

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

	private void menuDirect(TournoisDirect tournois,
			TournoisDirectController controller) {
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
				gererEquipe(tournois.getEquipes(), tournois, null);
				break;
			case 4:
				System.exit(0);// Quitter programmme
			}

		}

	}

	private TournoisDirect menuPoule(TournoisPoule tournois,
			TournoisPouleController controller) {

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
				gererEquipe(tournois.getEquipes(), tournoisDirect, tournois);
				break;
			case 4:
				System.exit(0);// Quitter programmme
			}

		}

		return tournoisDirect;
	}
	

	private int choixNbJoueur(){
		System.out.println("Tapez le nombre de joueur dans votre equipe :");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		return Integer.parseInt(commande);
	}
	
	private int choixTournois() {

		System.out.println("----Choix du tournois----");
		System.out.println("1 : Commencer un tournois avec poules");
		System.out.println("2 : Commencer un tournois sans poules");
		System.out.println("0 : Quitter");
		System.out.println("-------------------------");
		System.out.println("Touches autorisées : 1, 2, 0");

		Pattern pattern = Pattern.compile("^[120]$");
		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!pattern.matcher(commande).find());

		switch (commande) {
		case "1":
			return 1;
		case "2":
			return 2;
		case "0":
			return 0;
		default:
			return choixTournois();
		}

	}

	private String choixNom(String type) {

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

	private Match choixMatch(ArrayList<Match> matchs) {
		for (int i = 0; i < matchs.size(); i++) {
			System.out.println((i + 1) + ". " + matchs.get(i));
		}
		System.out.println("Entrez le numero du match selectionnee:");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		if (Integer.parseInt(commande) <= 0
				|| Integer.parseInt(commande) > matchs.size())
			return choixMatch(matchs);

		return matchs.get(Integer.parseInt(commande) - 1);
	}

	private Match choixMatch(Match[] matchs) {
		ArrayList<Integer> forbidden = new ArrayList<>();

		for (int i = 0; i < matchs.length; i++) {
			if (matchs[i].getScore()[0] != -2)
				System.out.println((i + 1) + ". " + matchs[i]);
			else
				forbidden.add(i);
		}
		System.out.println("Entrez le numero du match selectionnee:");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		if (Integer.parseInt(commande) <= 0
				|| Integer.parseInt(commande) > matchs.length
				|| forbidden.contains(Integer.parseInt(commande) - 1))
			return choixMatch(matchs);

		return matchs[Integer.parseInt(commande) - 1];
	}

	private Poule choixPoule(ArrayList<Poule> poules) {
		for (int i = 0; i < poules.size(); i++) {
			System.out.println((i + 1) + ". " + poules.get(i));
		}
		System.out.println("Entrez le numero de la poule selectionnee:");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		if (Integer.parseInt(commande) <= 0
				|| Integer.parseInt(commande) > poules.size()) {
			return choixPoule(poules);
		}
		return poules.get(Integer.parseInt(commande) - 1);

	}

	private int choixEquipe(ArrayList<Equipe> equipes) {
		for (int i = 0; i < equipes.size(); i++) {
			System.out.println((i + 1) + ". " + equipes.get(i));
		}
		System.out.println("Entrez le numero de l'equipe selectionnee:");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		if (Integer.parseInt(commande) <= 0
				|| Integer.parseInt(commande) > equipes.size())
			return choixEquipe(equipes);

		return Integer.parseInt(commande) - 1;

	}

	private ArrayList<Equipe> choixNbEquipes() {
		ArrayList<Equipe> equipes = new ArrayList<>();

		System.out.println("Tapez le nombre d'equipe dans votre tournois :");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		if (Integer.parseInt(commande) < 3) {
			System.out
					.println("Il faut au moins 3 equipes pour commencer un tournois");
			return choixNbEquipes();
		}

		for (int i = 0; i < Integer.parseInt(commande); i++) {
			equipes.add(new Equipe("Equipe" + (i + 1)));
		}

		return equipes;
	}

	
	private int validerNom(String nom) {
		System.out.println("Valider le nom : \"" + nom + "\" ?");
		System.out.println("1. oui");
		System.out.println("2. non");
		System.out.println("Touches autorisées : 1, 2");

		Pattern pattern = Pattern.compile("^[12]$");
		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!pattern.matcher(commande).find());

		switch (commande) {
		case "1":
			return 1;
		case "2":
			return 2;
		default:
			return validerNom(nom);
		}
	}


	private void finDirect(TournoisDirect tournois) {
		System.out.println("Le tournois est fini");
		System.out.println("L'equipe "+tournois.getGagnant()+" gagne le tournois!!");
		System.out.println(tournois);
	}

	private void finPoule() {
		System.out.println("La phase de poule est terminée!");
	}

	private TournoisDirect finMatch(TournoisPoule tournois,
			TournoisPouleController controller) {
		ArrayList<Poule> poules = tournois.getPoules();
		Poule poule = choixPoule(poules);
		Match match = choixMatch(poule.getMatchs());
		int[] scores = donnerScores(match);
		return controller
				.finMatch(tournois, poule, match, scores[0], scores[1]);
	}

	private boolean finMatch(TournoisDirect tournois,
			TournoisDirectController controller) {
		Match match = choixMatch(tournois.getArbre().get(tournois.getTour()));
		int[] scores = donnerScores(match);
		while(scores[0]==scores[1]){
			System.out.println("L'egalite est impossible en elimination directe");
			scores = donnerScores(match);
		}
		return controller.finMatch(tournois, match, scores[0], scores[1]);
	}

	
	private int[] donnerScores(Match match) {

		int[] scores = new int[2];
		Equipe[] equipes = match.getEquipes();

		for (int i = 0; i < 2; i++) {

			scores[i] = donnerScore(equipes[i]);

		}

		return scores;
	}

	private int donnerScore(Equipe equipe) {

		System.out.println("\n\nScore Equipe " + equipe + " :");

		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!commande.matches("[0-9]*"));

		if (Integer.parseInt(commande) < 0)
			return donnerScore(equipe);

		return Integer.parseInt(commande);

	}

	
	private int actionTournois() {

		System.out.println("1. Ajouter un score");
		System.out.println("2. Afficher le tournois");
		System.out.println("3. Gerer une equipe");
		System.out.println("4. Quitter");
		System.out.println("Touches autorisées : 1, 2, 3, 4");

		Pattern pattern = Pattern.compile("^[1234]$");
		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!pattern.matcher(commande).find());

		switch (commande) {
		case "1":
			return 1;
		case "2":
			return 2;
		case "3":
			return 3;
		case "4":
			return 4;
		default:
			return actionTournois();
		}

	}

	private int actionEquipe() {

		System.out.println("1. Changer le nom");
		System.out.println("2. Changer le nombre de joueur");
		System.out.println("3. Changer la decription");
		System.out.println("4. Voir les statistiques");
		System.out.println("5. Retour au menu principal");
		System.out.println("Touches autorisées : 1, 2, 3, 4, 5");

		Pattern pattern = Pattern.compile("^[12345]$");
		Scanner sc = new Scanner(System.in);
		String commande;

		do {
			commande = sc.next();
		} while (!pattern.matcher(commande).find());

		switch (commande) {
		case "1":
			return 1;
		case "2":
			return 2;
		case "3":
			return 3;
		case "4":
			return 4;
		case "5":
			return 5;
		default:
			return actionEquipe();
		}

	}

	
	private void gererEquipe(ArrayList<Equipe> equipes, TournoisDirect direct,
			TournoisPoule poule) {
		Equipe equipe = equipes.get(choixEquipe(equipes));

		switch (actionEquipe()) {
		case 1:
			System.out.println("Nom actuel : " + equipe.getNom());
			try {
				String nom = choixNom("equipe");
				for (Equipe equ : equipes) {
					if (equ.getNom().equals(nom) && !equ.equals(equipe))
						throw new NomDejaPrisExecption();
				}
				equipe.setNom(nom);
			} catch (NomDejaPrisExecption ndpe) {
				System.out.println("Nom deja pris");
				gererEquipe(equipes,direct,poule);
			}

			break;
		case 2:
			System.out.println("Nombre de joueurs actuel : "
					+ equipe.getNbJoueur());
			equipe.setNbJoueur(choixNbJoueur());
			break;
		case 3:
			System.out.println("Description actuel : "
					+ equipe.getDescription());
			equipe.setDescription(choixNom("equipe et sa description"));
			break;
		case 4:
			System.out.println(equipe.stats(direct, poule));
			break;
		case 5:
			return;
		default:
			gererEquipe(equipes, direct, poule);
		}

	}
	
}
