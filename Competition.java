package siteParis;


public class Competition {
   //Atributs
   private String nom;
   private Competiteur[] competiteurs;
   private DateFrancaise dateCloture;
   


   //Constructeur
	public Competition(String nom,DateFrancaise dateCloture,Competiteur[] competiteurs){
      this.nom = nom;
      this.dateCloture = dateCloture;	
      this.competiteurs = competiteurs;
      
	}
   //Methodes
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public DateFrancaise getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(DateFrancaise dateCloture) {
		this.dateCloture = dateCloture;
	}

	public Competiteur[] getCompetiteurs() {
		return competiteurs;
	}


	public void setCompetiteurs(Competiteur[] competiteurs) {
		this.competiteurs = competiteurs;
	}
	
	public int getCompetiteursNombre() {
		return competiteurs.length;
	}
	
	public boolean estCompetiteurDansLaCompetition(String competiteur){
		for(int i=0;i<competiteurs.length;i++){
			if(competiteurs[i].getNom().equals(competiteur)) return true;
		}
		return false;
	}
	
	public Competiteur getCompetiteur(String competiteur){
		for(int i=0;i<competiteurs.length;i++){
			if(competiteurs[i].getNom().equals(competiteur)) return competiteurs[i];
		}
		return null;
	}
   
  public static void main(String[] args) {
      try
      {
         DateFrancaise dateCloture = new DateFrancaise(02, 11, 2014);
         System.out.println(dateCloture);
         Competiteur quentin = new Competiteur("Quentin");
         Competiteur hugo = new Competiteur("Hugo");
         Competiteur antoine = new Competiteur("Antoine");
         Competiteur nathalie = new Competiteur("Nathalie");
      
         Competiteur[] competiteurs = new Competiteur[2];
         competiteurs[0]=hugo;
         competiteurs[1]=quentin;
         Competition tourDeFrance = new Competition("tourDeFrance", dateCloture, competiteurs);
         System.out.println(competiteurs[0].getNom());
         System.out.println(tourDeFrance.getNom());
         tourDeFrance.setNom("River-Boca");
         System.out.println(tourDeFrance.getNom());
         System.out.println(tourDeFrance.getDateCloture());
         DateFrancaise autredateCloture = new DateFrancaise(11, 02, 2032);
         tourDeFrance.setDateCloture(autredateCloture);
         System.out.println(tourDeFrance.getDateCloture());
         Competiteur[] competiteurs2;
         competiteurs2=tourDeFrance.getCompetiteurs();
         Competiteur[] newcompetiteurs = new Competiteur[2];
         newcompetiteurs[0]=nathalie;
         newcompetiteurs[1]=antoine;
         tourDeFrance.setCompetiteurs(newcompetiteurs);
         Competiteur[] competiteurs3;
         competiteurs3=tourDeFrance.getCompetiteurs();
         System.out.println(competiteurs3[0].getNom());
         System.out.println(competiteurs3[1].getNom());
         System.out.println(tourDeFrance.estCompetiteurDansLaCompetition("Antoinee"));
      }
      catch(DateFrancaiseException dfe)
      {
         System.out.println("probleme date");
      }

  }
}
