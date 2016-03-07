package siteParis;

import java.util.LinkedList;

public class Joueur {
	//Atributts
	private String nom;
	private String prenom;
	private String pseudo;
	private LinkedList<Pari> paris;
	private long mise;
	private long compte;
	private String motDePasse;

	public Joueur(String nom,String prenom,String pseudo,LinkedList<Pari> paris, long compte){
		this.nom = nom;
		this.prenom=prenom;
		this.pseudo=pseudo;
		this.paris=paris;
		this.compte=compte;
		this.mise=0;
	}	
	
	//Methodes 
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public LinkedList<Pari> getParis() {
		return paris;
	}

	public void setParis(LinkedList<Pari> paris) {
		this.paris = paris;
	}

	public long getCompte() {
		return compte;
	}

	public void setCompte(long compte) {
		this.compte = compte;
	}
	
	public boolean equals (Object o) {
		if (o instanceof Joueur) {
			if ((((Joueur) o).getNom().equals(this.nom) && ((Joueur) o).getPrenom().equals(this.prenom)) || ((Joueur) o).getPseudo().equals(this.pseudo)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean equalsNomPrenomPseudo (Object o) {
		if (o instanceof Joueur) {
			if ((((Joueur) o).getNom().equals(this.nom) && ((Joueur) o).getPrenom().equals(this.prenom)) && ((Joueur) o).getPseudo().equals(this.pseudo)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	public String getPassword() {
		return motDePasse;
	}

	public void setPassword(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public long getMise() {
		return mise;
	}

	public void setMise(long mise) {
		this.mise = mise;
	}
	
	public void ajouterMise(long mise) {
		this.mise = this.mise + mise;
	}
	
	public void debiterMise(long mise) {
		this.mise = this.mise - mise;
	}
	
	public void crediter(long jetons){
		this.compte= this.compte + jetons;
	}
	
	public void debiter(long jetons){
		this.compte= this.compte - jetons;
	}
	
    public static void main(String[] args) {
		//Creer un joueur avec compte=0
		Joueur newJoueur = new Joueur("Huguito","Sanchez","Locura123",null, 0);
		Joueur newJoueur2 = new Joueur("Huguito","Sanchez","Locura123",null, 0);
		System.out.println(newJoueur.equals(newJoueur2));
		System.out.println(newJoueur2.equals(newJoueur));
	}
	
}
