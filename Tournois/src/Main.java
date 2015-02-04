import java.util.Scanner;
import java.util.regex.Pattern;

import fr.epf.tic1.javaee.projet.controller.*;
import fr.epf.tic1.javaee.projet.model.*;
import fr.epf.tic1.javaee.projet.view.console.ConsoleView;
import fr.epf.tic1.javaee.projet.view.javafx.JavaFXView;



public class Main {

	public static void main(String[] args) {
		
		System.out.println("J- Java FX");
		System.out.println("C- Console");
		
		Pattern pattern = Pattern.compile("^[JjCc]$");
		Scanner sc = new Scanner(System.in);
		String s;
		
		
		do{
			System.out.println("Choisissez votre mode :");
			s = sc.next();
		}while(!pattern.matcher(s).find());
			
			
		switch(s){
		case "J":
		case "j":
			JavaFXView.launch(JavaFXView.class, args);
			break;
		case "C":
		case "c":
			modeConsole();
			break;
			
		}
		
		
	}

	public static void modeConsole(){
		ATournois tournois;
		ConsoleView console =new ConsoleView();
		
		//Choix du type de tournois
		switch(console.choixTournois()){
		case 1:
			tournois = new TournoisPoule();
			tournois.setNom(console.choixNom("tournois"));
			tournois.setEquipes(console.donnerEquipes());
			console.menuEnCoursGeneral(tournois);
			break;
		case 2:
			tournois = new TournoisDirect();
			tournois.setNom(console.choixNom("tournois"));
			tournois.setEquipes(console.donnerEquipes());
			console.menuEnCoursGeneral(tournois);
			break;
		case 0:
			System.exit(0);
		}
		
		main(new String[1]);
	}
}
