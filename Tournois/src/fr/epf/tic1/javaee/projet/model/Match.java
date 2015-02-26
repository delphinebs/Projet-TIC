package fr.epf.tic1.javaee.projet.model;

public class Match {

	//Attention!!! => verif pas deux equipes identiques (Execption si deux equipes identiques?)	
	private Equipe[] equipes;
	private int[] scores;
	private boolean joue;
	
	
	/*Constructors*/
	//
	//
	
	public Match() {
		equipes = new Equipe[2];
		equipes[0]= new Equipe();
		equipes[1]= new Equipe();
		scores = new int[2];
		joue=false;
	}
	
	public Match(Equipe equipe1, Equipe equipe2) {
		equipes = new Equipe[2];
		scores = new int[2];
		equipes[0]=equipe1;
		equipes[1]=equipe2;
		
		scores[0]=-1;
		scores[1]=-1;	
		
		joue=false;
	}

	
	/*Getter - Setter*/
	//
	//
	
	public Equipe[] getEquipes(){
		return equipes;
	}
	
	public boolean setEquipes(Equipe equipe){
		if(equipes[0].getNom().equals("default")){
			equipes[0]=equipe;
			return true;
		}
		else if(equipes[1].getNom().equals("default")){
			equipes[1]=equipe;
			return true;
		}
		return false;
		//else Error
	}
	
	public int[] getScore(){
		return scores;
	}
	
	public void setScore(int score1, int score2){
			scores[0]=score1;
			scores[1]=score2;	
			joue=true;
	}
	
	public boolean getJoue(){
		return joue;
	}
	
	@Override
	public String toString() {
		if(joue){
			if(scores[0]==-2) return equipes[0]+"";
			else return equipes[0]+" "+scores[0]+"-"+scores[1]+" "+equipes[1];
		}
		else return equipes[0]+" - "+equipes[1] + " (a jouer)";
	}
	
	//scores[0]==-2 => isFake()

}
