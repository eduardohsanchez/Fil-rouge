package siteParis;

public class Competiteur {
	//Atributts

	private String nom;

	//Constructeur	

	public Competiteur(String name){
     this.nom = name;
	}

	public String getNom() {
		return nom;
	}

	public void setName(String nom) {
		this.nom = nom;
	}


  public static void main(String[] args) {
      Competiteur quentin = new Competiteur("Quentin");
      System.out.println(quentin.getNom());
      quentin.setName("QUENTIN");
      System.out.println("On a change le nom");
      System.out.println(quentin.getNom());
      Competiteur c = new  Competiteur("toto");
      int[] tab = {1,2,3,54,5,84,84,84};
      System.out.println(tab.length);
  }
  
}
