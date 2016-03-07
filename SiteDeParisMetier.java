package siteParis;


import java.util.LinkedList;
import java.util.Random;


/**
 * 
 * @author Bernard Prou et Julien Mallet
 * <br><br>
 * La classe qui contient toutes les méthodes "Métier" de la gestion du site de paris. 
 * <br><br>
 * Dans toutes les méthodes :
 * <ul>
 * <li>un paramètre de type <code>String</code> est invalide si il n'est pas instancié.</li>
 *  <li>pour la validité d'un password de gestionnaire et d'un password de joueur :
 * <ul>
 * <li>       lettres et chiffres sont les seuls caractères autorisés </li>
 * <li>       il doit avoir une longueur d'au moins 8 caractères </li>
 * </ul></li>       
 * <li>pour la validité d'un pseudo de joueur  :
 * <ul>
 * <li>        lettres et chiffres sont les seuls caractères autorisés  </li>
 * <li>       il doit avoir une longueur d'au moins 4 caractères</li>
 * </ul></li>       
 * <li>pour la validité d'un prénom de joueur et d'un nom de joueur :
 *  <ul>
 *  <li>       lettres et tiret sont les seuls caractères autorisés  </li>
 *  <li>      il  doit avoir une longueur d'au moins 1 caractère </li>
 * </ul></li>
 * <li>pour la validité d'une compétition  :       
 *  <ul>
 *  <li>       lettres, chiffres, point, trait d'union et souligné sont les seuls caractères autorisés </li>
 *  <li>      elle  doit avoir une longueur d'au moins 4 caractères</li>
 * </ul></li>       
 * <li>pour la validité d'un compétiteur  :       
 *  <ul>
 *  <li>       lettres, chiffres, trait d'union et souligné sont les seuls caractères autorisés </li>
 *  <li>      il doit avoir une longueur d'au moins 4 caractères.</li>
 * </ul></li></ul>
 */

public class SiteDeParisMetier {

   //Attributs
	private LinkedList<Competition> listeCompetitions;
   	private LinkedList<Joueur> listeJoueurs;
	private LinkedList<Pari> listeParis;
	private String passwordGestionnaire;

	
	public SiteDeParisMetier(String passwordGestionnaire) throws MetierException {
		this.passwordGestionnaire = passwordGestionnaire;
		this.validitePasswordGestionnaire(passwordGestionnaire);
      	this.listeJoueurs = new LinkedList<Joueur>();
      	this.listeCompetitions = new LinkedList<Competition>();
      	this.listeParis= new LinkedList<Pari>();
   }

	public String inscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurExistantException, JoueurException {
		
		//Metier Expection levee si le password est invalide ou si le password est incorrect
		this.validitePasswordGestionnaire(passwordGestionnaire);
		if (!this.passwordGestionnaire.equals(passwordGestionnaire) ) throw new MetierException();
		
		//JoueurExpection levee si le meme nom, prenom ou pseudo sont invalides
		this.validiteNom(nom);
		this.validitePrenom(prenom);
		this.validitePseudo(pseudo);
		
		
		//Creer un joueur avec compte=0
		Joueur newJoueur = new Joueur(nom,prenom,pseudo,null, 0);
		
		//JoueurExistantExpection levee si un joueur existe avec le meme nom, prenom ou pseudo 
		for (Joueur j:listeJoueurs){
			if ((j.equals(newJoueur))) throw new JoueurExistantException();
		}

		//Ajoute le joueur
		
		String unMotdePasseUnique=this.createPassword();
		newJoueur.setPassword(unMotdePasseUnique);
		listeJoueurs.add(newJoueur);
		
		
		
		return unMotdePasseUnique;
	}


	public long desinscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurInexistantException, JoueurException {
		//Metier Expection levee si le password est invalide ou si le password est incorrect 
		this.validitePasswordGestionnaire(passwordGestionnaire);
		if (!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
		
		
		//Creer un joueur pour chercher dans la liste de joueurs
		Joueur nouveauJoueur = new Joueur(nom,prenom,pseudo,null, 0);
		//JoueurInexistantExpection levee si un joueur existe avec le meme nom, prenom ou pseudo 
		if(this.estJoueurExistantNomPrenomPseudo(nouveauJoueur)==false)  throw new JoueurInexistantException();

		//Get Joueur
		Joueur joueurChoisi=null;
		for (Joueur j:listeJoueurs){
			if ((j.equals(nouveauJoueur))){
				joueurChoisi=j;
				break;
			} 
		}
		//JoueurExpection levee si le joueur a des paris en cours 
		for (Pari p:listeParis){
			
			if (joueurChoisi.equals(p.getJoueur())) throw new JoueurException();
		}
		
		long jetons =joueurChoisi.getCompte();
		this.listeJoueurs.remove(joueurChoisi);
		return jetons;
		
	}
	
	protected boolean estJoueurExistantNomPrenomPseudo(Joueur nouveauJoueur){
		for (Joueur j:listeJoueurs){
			if ((j.equals(nouveauJoueur))) return true;
		}
		return false;
	}



	public void ajouterCompetition(String competition, DateFrancaise dateCloture, String [] competiteurs, String passwordGestionnaire) throws MetierException, CompetitionExistanteException, CompetitionException  {
		//Metier Expection levee si le password est invalide, si le password est incorrect ou si le tableau de competiteurs n'est pas intancie
		this.validitePasswordGestionnaire(passwordGestionnaire);
		if(competiteurs==null) throw new MetierException();
		this.validiteCompetition(competition);
		
		//CompetitionExistanteException levee si une competition exist avec le meme nom
		if(estCompetitionExistante(competition)) throw new CompetitionExistanteException();
		
		//CompetitionException levee si le nom de la competition ou des competiteurs sont invalides, si il y a moins de 2 competiteurs, si un des competiteurs n'est pas instancie
		// si deux competiteurs ont le meme nom, si la date de cloture n'est pas instanciee ou est depassee
		if(dateCloture==null || dateCloture.estDansLePasse()==true) throw new CompetitionException();
		if(competiteurs.length < 2) throw new CompetitionException();
		for(int i=0;i<competiteurs.length;i++){
			this.validiteCompetiteur(competiteurs[i]);
			for(int j=0;j<competiteurs.length;j++){
				if(competiteurs[i]==competiteurs[j] && i!=j)	throw new CompetitionException();
			}
		}
		
		//Creer array de competiteurs
		Competiteur[] competiteursArray = new Competiteur[competiteurs.length];
		for(int i=0;i<competiteurs.length;i++){
			competiteursArray[i]= new Competiteur(competiteurs[i]);
		}
		//Creer competition
		Competition nouveauCompetition = new Competition(competition, dateCloture, competiteursArray);
		
		//Ajouter competition
		listeCompetitions.add(nouveauCompetition);
	}
   
	protected boolean estCompetitionExistante(String competition){
		for (Competition c:listeCompetitions){
			if(competition.equals(c.getNom()))	return true;
		}
		return false;
   }
   


	public void solderVainqueur(String competition, String vainqueur, String passwordGestionnaire) throws MetierException,  CompetitionInexistanteException, CompetitionException  {
		
		this.validitePasswordGestionnaire(passwordGestionnaire);
		
    	if(this.estCompetitionExistante(competition)==false) throw new CompetitionInexistanteException();
    	
    	this.validiteCompetition(competition);
    	this.validiteCompetiteur(vainqueur);
    	
		//Get the competition
		Competition competitionChoisi=null;
		for (Competition c : listeCompetitions) {
			if (c.getNom().equals(competition)){
				competitionChoisi= c;
				break;
			}
		}
		
		if(!competitionChoisi.getDateCloture().estDansLePasse()) throw new CompetitionException();
		if(competitionChoisi.estCompetiteurDansLaCompetition(vainqueur)==false) throw new CompetitionException();
		
		long sommeJetonsMises=0;
		long sommeJetonsMisesVainqueur=0;
		
		boolean vainqueurFlag= false;
		
		for(Pari p : listeParis){
			if (p.getCompetition().getNom().equals(competitionChoisi.getNom())){
				sommeJetonsMises=sommeJetonsMises+p.getJetons();
				p.getJoueur().debiterMise(p.getJetons());
				if(p.getCompetiteur().getNom().equals(vainqueur)){
					vainqueurFlag= true;
					sommeJetonsMisesVainqueur=sommeJetonsMisesVainqueur+p.getJetons();
				}
			}
			
		}
		
		if(vainqueurFlag==true){
			for(Pari p : listeParis){
				if (p.getCompetition().getNom().equals(competitionChoisi.getNom()) && p.getCompetiteur().getNom().equals(vainqueur)){
					p.getJoueur().crediter((p.getJetons()*sommeJetonsMises)/sommeJetonsMisesVainqueur);
				}	
			}
		}
		
		else{
			for(Pari p : listeParis){
				if (p.getCompetition().getNom().equals(competitionChoisi.getNom())){
					p.getJoueur().crediter(p.getJetons());
				}	
			}
		}
		
		this.listeCompetitions.remove(competitionChoisi);
	}


	public void crediterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws MetierException, JoueurException, JoueurInexistantException {
		//Metier Expection levee si le password est invalide, si le password est incorrect ou si la sommeEnjetons est negative
		this.validitePasswordGestionnaire(passwordGestionnaire);
		if (sommeEnJetons<0) throw new MetierException();
		//JoueurExpection levee si le meme nom, prenom ou pseudo sont invalides
		this.validiteNom(nom);
		this.validitePrenom(prenom);
		this.validitePseudo(pseudo);
		//Creer un joueur pour chercher dans la liste de joueurs
		Joueur newJoueur = new Joueur(nom,prenom,pseudo,null, 0);
		
		//JoueurExistantExpection levee si un joueur existe avec le meme nom, prenom ou pseudo 
		for (Joueur j:listeJoueurs){
			if ((j.equalsNomPrenomPseudo(newJoueur))){				
				j.setCompte(j.getCompte()+sommeEnJetons);
				//System.out.println("Joueur : " +j.getPrenom() + " Compte : " +j.getCompte() ) ;
				return;
			}
		}
		
		throw new JoueurInexistantException();
		
		
	}


	public void debiterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws  MetierException, JoueurInexistantException, JoueurException {
		//Metier Expection levee si le password est invalide, si le password est incorrect ou si la sommeEnjetons est negative
		this.validitePasswordGestionnaire(passwordGestionnaire);
		if (sommeEnJetons<0) throw new MetierException();
		//JoueurExpection levee si le meme nom, prenom ou pseudo sont invalides
		this.validiteNom(nom);
		this.validitePrenom(prenom);
		this.validitePseudo(pseudo);
		//Creer un joueur pour chercher dans la liste de joueurs
		Joueur newJoueur = new Joueur(nom,prenom,pseudo,null, 0);
		
		//JoueurExistantExpection levee si un joueur existe avec le meme nom, prenom ou pseudo 
		for (Joueur j:listeJoueurs){
			if ((j.equalsNomPrenomPseudo(newJoueur))){
				if((j.getCompte()-sommeEnJetons) < 0) throw new JoueurException();
				j.debiter(sommeEnJetons);
				//System.out.println("La plata que quedo es: " + j.getCompte());
				return;
			}
		}
		throw new JoueurInexistantException();
	}


	public LinkedList <LinkedList <String>> consulterJoueurs(String passwordGestionnaire) throws MetierException {
		//Metier Expection levee si le password est invalide ou si le password est incorrect 
		this.validitePasswordGestionnaire(passwordGestionnaire);
		
		LinkedList<LinkedList<String>> listeDeJoueurs = new LinkedList<LinkedList<String>>();
		LinkedList<String> dataJoueurs;
		
		for (Joueur j:listeJoueurs){
        	dataJoueurs = new LinkedList<String>();
        	dataJoueurs.add(j.getNom());
        	dataJoueurs.add(j.getPrenom());
        	dataJoueurs.add(j.getPseudo());
        	dataJoueurs.add(Long.toString(j.getCompte()));
        	dataJoueurs.add(Long.toString(j.getMise()));
        	listeDeJoueurs.add(dataJoueurs);
		}
         return listeDeJoueurs;
	}




    public void miserVainqueur(String pseudo, String passwordJoueur, long miseEnJetons, String competition, String vainqueurEnvisage) throws MetierException, JoueurInexistantException, CompetitionInexistanteException, CompetitionException, JoueurException  {
    	if(miseEnJetons<0) throw new MetierException();
    	this.validitePseudo(pseudo);
    	this.validitePasswordJoueur(passwordJoueur);
    	if(this.estJoueurInexistant(pseudo, passwordJoueur)) throw new JoueurInexistantException();
		this.validiteCompetition(competition);
    	if(this.estCompetitionExistante(competition)==false) throw new CompetitionInexistanteException();
		this.validiteCompetiteur(vainqueurEnvisage);
		//Get the competition
		Competition competitionChoisi=null;
		for (Competition c : listeCompetitions) {
			if (c.getNom().equals(competition)){
				competitionChoisi= c;
				break;
			}
		}
		if(competitionChoisi.getDateCloture().estDansLePasse()) throw new CompetitionException();
		if(competitionChoisi.estCompetiteurDansLaCompetition(vainqueurEnvisage)==false) throw new CompetitionException();
		
		//Get the joueur
		Joueur joueurChoisi=null;
		for (Joueur j : listeJoueurs) {
			if (j.getPseudo().equals(pseudo) && j.getPassword().equals(passwordJoueur)){
				joueurChoisi= j;
				if((joueurChoisi.getCompte()-miseEnJetons)<0) throw new JoueurException();
				break;
			}
		}
		
		
		//Debiter le compte
		joueurChoisi.debiter(miseEnJetons);
		
		//Ajouter le mise
		joueurChoisi.ajouterMise(miseEnJetons);
		
		Pari newPari= new Pari(miseEnJetons, joueurChoisi ,competitionChoisi.getCompetiteur(vainqueurEnvisage), competitionChoisi);
		
		listeParis.add(newPari);
		
		//System.out.println("Joueur : " +joueurSelected.getPrenom() + " Mise : " + miseEnJetons + " Reste : " +joueurSelected.getCompte() ) ;
		
		

	}

    protected boolean estJoueurInexistant(String pseudo, String passwordJoueur){
		for (Joueur j:listeJoueurs){
			if (j.getPseudo().equals(pseudo) && j.getPassword().equals(passwordJoueur)){
				return false;
			}
		}
		return true;
    }
    
	public LinkedList <LinkedList <String>> consulterCompetitions(){
	
		LinkedList<LinkedList<String>> listeDeCompetition = new LinkedList<LinkedList<String>>();
		LinkedList<String> dataCompetition;

		for (Competition c : listeCompetitions) {
			dataCompetition = new LinkedList<String>();
			dataCompetition.add(c.getNom());
			dataCompetition.add(c.getDateCloture().toString());
			listeDeCompetition.add(dataCompetition);
		}

		return listeDeCompetition;
	} 

	public LinkedList <String> consulterCompetiteurs(String competition) throws CompetitionException, CompetitionInexistanteException{
		this.validiteCompetition(competition);
		if(estCompetitionExistante(competition)==false) throw new CompetitionInexistanteException();
		//Get the competition
		Competition competitionChoisi = null;
		for (Competition c:listeCompetitions){
			if(competition.equals(c.getNom())){
				competitionChoisi=c;
				break;
			}
		}
		//Get the competiteurs 
		Competiteur[] competiteursArray = new Competiteur[competitionChoisi.getCompetiteursNombre()];
		competiteursArray = competitionChoisi.getCompetiteurs();
		
		//Transform an array into a list
		LinkedList <String> listeDeCompetiteurs= new LinkedList <String>();
		for(int i=0;i<competiteursArray.length;i++){
			listeDeCompetiteurs.add(competiteursArray[i].getNom());
		}
		
		return listeDeCompetiteurs;
	}


	protected void validitePasswordGestionnaire(String passwordGestionnaire) throws MetierException {
	    if (passwordGestionnaire==null) throw new MetierException();
	    if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
	    if (!passwordGestionnaire.matches("[0-9A-Za-z]{8,}")) throw new MetierException();
	}
	
	protected void validitePasswordJoueur(String passwordJoueur) throws JoueurException {
	    if (passwordJoueur==null) throw new JoueurException();
	    if (!passwordJoueur.matches("[0-9A-Za-z]{8,}")) throw new JoueurException();
	}

	protected void validiteNom(String nomprenompseudo) throws JoueurException {
		if (nomprenompseudo==null) throw new JoueurException();
		if (!nomprenompseudo.matches("[A-Za-z-]{1,}")) throw new JoueurException();
	}
	
	protected void validitePrenom(String nomprenompseudo) throws JoueurException {
		if (nomprenompseudo==null) throw new JoueurException();
		if (!nomprenompseudo.matches("[A-Za-z-]{1,}")) throw new JoueurException();
	}
	
	protected void validitePseudo(String nomprenompseudo) throws JoueurException {
		if (nomprenompseudo==null) throw new JoueurException();
		if (!nomprenompseudo.matches("[0-9A-Za-z]{4,}")) throw new JoueurException();
	}

	protected void validiteCompetition(String competition) throws CompetitionException {
		if (competition==null) throw new CompetitionException();
		if (!competition.matches("[0-9A-Za-z.-_]{4,}")) throw new CompetitionException();
	}
	
	protected void validiteCompetiteur(String competiteur) throws CompetitionException {
		if (competiteur==null) throw new CompetitionException();
		if (!competiteur.matches("[0-9A-Za-z-_]{4,}")) throw new CompetitionException();
	}

	
	protected String createPassword(){
	
		Random r = new Random();
	    char tab_lowchar[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','o','q','r','s'};
	    char tab_highchar[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','Q','R','S'};
	    int tab_nb[] = {0,1,2,3,4,5,6,7,8,9};
	    int valeurMin = 0;
	    int valeurMax = 17;
	    int valeurMaxNb = 8;
	    int miniValuer = 3;
	    String finalPwd = "";
	    
	    for(int i=1;i<=10;i++){
	         int choix = valeurMin + r.nextInt(miniValuer - valeurMin);
	
	         if(choix == 0){
	         //pour recuperer un entier
	         int vava = valeurMin + r.nextInt(valeurMaxNb - valeurMin);          
	         finalPwd += tab_nb[vava]; 
	         }
	           
	         if(choix == 1){
	         //pour recuperer un petit caractere
	         int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
	         finalPwd += tab_lowchar[valeur]
	         ;
	         }      
	         if(choix ==2){
	         //pour recuperer un entier
	         int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);      
	         finalPwd += tab_highchar[valeur];
	         }                                               
	    }
	    return finalPwd;
	}


    public static void main(String[] args) {
	   try{
	      SiteDeParisMetier bwin= new SiteDeParisMetier("nnnnnn33312");
	      bwin.inscrireJoueur("Hugo", "Sanchez", "Apostador66667", "nnnnnn33312");
	      bwin.inscrireJoueur("Eduardo", "Sanchez", "Apostador66668", "nnnnnn33312");
	      bwin.inscrireJoueur("Roque", "Sanchez", "Apostador66669", "nnnnnn33312");
	      System.out.println("inscrire Joueur OK");
	      bwin.desinscrireJoueur("Hugo", "Sanchez", "Apostador6666", "nnnnnn33312");
	      System.out.println("desinscrire Joueur OK");
	      
	      
	      DateFrancaise dateCloture = new DateFrancaise(22, 12, 2014);
	      System.out.println(dateCloture);
	      String[] competiteurs = new String[2];	      
	      competiteurs[0]="hugo";
	      competiteurs[1]="quentin";
	      bwin.ajouterCompetition("Competenciadeprueba", dateCloture, competiteurs, "nnnnnn33312");
	      bwin.ajouterCompetition("Competenciadeprueba2", dateCloture, competiteurs, "nnnnnn33312");
	      
	      LinkedList <String> listedecompetiteurs=new LinkedList <String> ();
	      listedecompetiteurs=bwin.consulterCompetiteurs("Competenciadeprueba");
	      System.out.println("La lista de competidores es: ");
	      System.out.println(listedecompetiteurs);
	      
	      LinkedList<LinkedList<String>> listOfListCompetition = new LinkedList<LinkedList<String>>();
	      listOfListCompetition=bwin.consulterCompetitions();
	      System.out.println("La lista de competiciones es");
	      System.out.println(listOfListCompetition);
	      
	      LinkedList<LinkedList<String>> listOfListJoueurs = new LinkedList<LinkedList<String>>();
	      listOfListJoueurs=bwin.consulterJoueurs("nnnnnn33312");
	      System.out.println("Los jugadores son");
	      System.out.println(listOfListJoueurs);
	      
	      bwin.crediterJoueur("Eduardo", "Sanchez", "Apostador66668", 10,"nnnnnn33312");
	      
	      listOfListJoueurs=bwin.consulterJoueurs("nnnnnn33312");
	      System.out.println("Los jugadores son y sus guita");
	      System.out.println(listOfListJoueurs);
	      
	      bwin.debiterJoueur("Eduardo", "Sanchez", "Apostador66668", 2,"nnnnnn33312");
	      listOfListJoueurs=bwin.consulterJoueurs("nnnnnn33312");
	      System.out.println("Los jugadores son y su nueva guita");
	      System.out.println(listOfListJoueurs);
	      
	      bwin.miserVainqueur("Apostador66668", "cambiarcontrasenia", 1, "Competenciadeprueba", "hugo");
	      listOfListJoueurs=bwin.consulterJoueurs("nnnnnn33312");
	      System.out.println("Los jugadores son y su nueva nueva guita despues de apostar");
	      System.out.println(listOfListJoueurs);
	      
	      bwin.solderVainqueur("Competenciadeprueba", "quentin", "nnnnnn33312");
	      listOfListJoueurs=bwin.consulterJoueurs("nnnnnn33312");
	      System.out.println("Los jugadores son y su nueva nueva nueva nueva guita");
	      System.out.println(listOfListJoueurs);	      
	      
	      
	   }catch(MetierException mep){
	      System.out.println("Wrong password MetierException");      
	   }catch(JoueurExistantException jee){
		   System.out.println("Wrong password JoueurExistantException");      
	   }catch(JoueurException je){
		   System.out.println("Wrong password JoueurExistant2");      
	   }catch(JoueurInexistantException jie){
		   System.out.println("Wrong password JoueurInexistantException");
	   }catch(DateFrancaiseException dfi){
		   System.out.println("Wrong password DateFrancaiseException");
	   }catch(CompetitionExistanteException cee){
		   System.out.println("Wrong password CompetitionExistanteException");
	   }catch(CompetitionException ce){
		   System.out.println("Wrong password CompetitionException");
	   }catch(CompetitionInexistanteException cie){
		   System.out.println("Wrong password CompetitionInexistanteException");
	   }
	}

}


