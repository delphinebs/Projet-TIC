package fr.epf.tic1.javaee.projet.model;

public class Equipe {

	private String nom;
	
	public Equipe(){
		nom = "default";
	}
	
	public Equipe(String nom){
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj==this) return true;
		if(getClass() != obj.getClass()) return false;
		
		return nom.equals(((Equipe)obj).nom);
	}

	@Override
	public String toString() {	
		return nom;
	}
}
