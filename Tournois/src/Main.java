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
			TournoisPouleController controllerPoule = new TournoisPouleController();
			controllerPoule.start(tournois);
			break;
		case 2:
			tournois = new TournoisDirect();
			tournois.setNom(console.choixNom("tournois"));
			tournois.setEquipes(console.donnerEquipes());
			TournoisDirectController controllerDirect = new TournoisDirectController();
			controllerDirect.start(tournois);
			break;
		case 0:
			return;
		}
		
		
	}

}
