package siteParis;

import java.util.LinkedList;


public class Pari{
	//Atributts
	private Joueur joueur;
	private Competiteur competiteur;
	private Competition competition;
	private Competiteur vainqueur;
	private long jetons;		

	//Constructeur	
	public Pari(long jetons , Joueur joueur ,Competiteur competiteur,Competition competition){
		this.jetons=jetons;
		this.joueur=joueur;
		this.competiteur=competiteur;
		this.competition=competition;
	}

	//Methodes
	
	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}	
	
	public long getJetons() {
		return jetons;
	}

	public void setJetons(long jetons) {
		this.jetons = jetons;
	}

	public Competiteur getVainqueur() {
		return vainqueur;
	}

	public void setVainqueur(Competiteur vainqueur) {
		this.vainqueur = vainqueur;
	}
	
	public Competiteur getCompetiteur() {
		return competiteur;
	}

	public void setCompetiteur(Competiteur competiteur) {
		this.competiteur = competiteur;
	}
	
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	
	public static void main(String[] args) {
	      try
	      {
	  		DateFrancaise dateCloture = new DateFrancaise(02, 11, 2014);
			System.out.println(dateCloture);
			Competiteur quentin = new Competiteur("Quentin");
			Competiteur hugo = new Competiteur("Hugo");   
			Competiteur[] competiteurs = new Competiteur[2];
			competiteurs[0]=hugo;
			competiteurs[1]=quentin;
			Joueur leost = new Joueur("Leost","Quentin","ElMatador",null, 0);
			Competition tourDeFrance = new Competition("tourDeFrance", dateCloture, competiteurs);
			Pari paris= new Pari(10 , leost , hugo, tourDeFrance);
	        System.out.println("La competencia llamada "+tourDeFrance.getNom() + "se realizara el dia " + dateCloture + " y se apostaran " + paris.getJetons()  + " euros por " + paris.getCompetiteur().getNom());
	      }
	      catch(DateFrancaiseException dfe)
	      {
	         System.out.println("probleme date");
	      }
	}

}
