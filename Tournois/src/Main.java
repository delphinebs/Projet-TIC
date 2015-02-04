import fr.epf.tic1.javaee.projet.controller.*;
import fr.epf.tic1.javaee.projet.model.*;
import fr.epf.tic1.javaee.projet.view.console.ConsoleView;



public class Main {

	public static void main(String[] args) {
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
