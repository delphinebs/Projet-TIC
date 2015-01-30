package fr.epf.tic1.javaee.projet.model;

public class ResultatsEquipe implements Comparable<ResultatsEquipe>{

	private Equipe equipe;
	private int points;
	private int differenceDeButs;
	
	public ResultatsEquipe(Equipe equipe) {
		points=0;
		differenceDeButs=0;
	}

	//Utiliser pour pouvoir sort() les resultats des equipes dan une poule
	@Override
	public int compareTo(ResultatsEquipe arg0) {
		
		//ou pas
		//Probleme de type, pas execption existante: execption custom ou cast qui cre CastExecption
		
		/*if(!(arg0 instanceof ResultatsEquipe)) throw */
		
		
		ResultatsEquipe resultats = (ResultatsEquipe) arg0;
		
		if(resultats.getPoints()>this.points) return -1;
		else if(resultats.getPoints()<this.points) return 1;
		else if(resultats.getDifferenceDeButs()>this.differenceDeButs) return -1;
		else if(resultats.getDifferenceDeButs()<this.differenceDeButs) return 1;
		else return 0;

	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getDifferenceDeButs() {
		return differenceDeButs;
	}

	public void setDifferenceDeButs(int differenceDeButs) {
		this.differenceDeButs = differenceDeButs;
	}
	
}
