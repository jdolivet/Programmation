import java.util.Scanner;

class PredProie {

    private final static double TAUX_ATTAQUE_INIT = 0.01;
    private final static double TAUX_CROISSANCE_LAPINS = 0.3;
    private final static double TAUX_CROISSANCE_RENARDS = 0.008;
    private final static double TAUX_MORTALITE = 0.1;
    private final static int DUREE = 50;

    public static void afficheStatus(int temps, double lapins, double renards) {
        System.out.print("Après ");
        System.out.print(temps);
        System.out.print(" mois, il y a ");
        System.out.format("%.3f", lapins );
        System.out.print(" lapins et ");
        System.out.format("%.3f", renards );
        System.out.println(" renards");
    }

    public static void afficheTauxDAttaque(double tauxAttaque) {
        System.out.println("");
        System.out.print("***** Le taux d'attaque vaut ");
        System.out.format("%.2f", tauxAttaque * 100);
        System.out.println("%");
    }

    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/
    static double entrerPopulation(String animal, double nombreMin, Scanner clavier)
    {
    	double a;
    	do
    	{
    	    System.out.print("Combien de ");
    	    System.out.print(animal+" au départ (>="+nombreMin);
    	    System.out.print(") ? "); 
    	    a = clavier.nextDouble();
    	}while(a<nombreMin);
    	return a;
    }
    
    static double calculerLapins(double nbLapins, double nbRenards, double tauxAttaque)
    {
    	double a =  nbLapins*(1.0 + TAUX_CROISSANCE_LAPINS - tauxAttaque * nbRenards );
    	return plafonner(a);
    }
    
    static double calculerRenards(double nbLapins, double nbRenards, double tauxAttaque)
    {
    	double a = nbRenards*(1.0 + tauxAttaque * nbLapins * TAUX_CROISSANCE_RENARDS - TAUX_MORTALITE);
    	return plafonner(a);
    }
    
    static double plafonner(double value)
    {
    	if(value<0)
    	{
    		return 0.0;
    	}
    	else
    	{
    		return value;
    	}
    }
    
    static void simule(double nombresRenards, double nombresLapins, double tauxInit, double tauxFin)
    { 	
    	double resteRenards=nombresRenards;
    	double resteLapins=nombresLapins;
    	double nbRenards;
    	double nbLapins;
    	double taux=tauxInit;
    	while(taux<=tauxFin)
    	{
        	boolean extinctionRenards=false;
        	boolean extinctionLapins=false;
        	boolean disparuRenards=false;
        	boolean disparuLapins=false;
        	boolean remonteRenards=false;
        	boolean remonteLapins=false;   
    		nbRenards=nombresRenards;
    		nbLapins=nombresLapins;
    		int i=0;
        	while((nbRenards>=2 || nbLapins>=2) && i<DUREE)
        	{
        		resteRenards=nbRenards;
        		resteLapins=nbLapins;        		
        		nbRenards=calculerRenards(resteLapins,resteRenards,taux);
        		nbLapins=calculerLapins(resteLapins,resteRenards,taux);
        		
            	if(extinctionRenards==true && nbRenards>resteRenards)
            	{
            		remonteRenards=true;
            	}
            	if(extinctionLapins==true && nbLapins>resteLapins)
            	{
            		remonteLapins=true;
            	}        	
            	if(nbRenards<5)
            	{
            		extinctionRenards=true;
            	}
            	if(nbLapins<5)
            	{
            		extinctionLapins=true;
            	}
            	if(nbRenards<2)
            	{
            		nbRenards=0;
            		disparuRenards=true;
            	}
            	if(nbLapins<2)
            	{
            		nbLapins=0;
            		disparuLapins=true;
            	}       		
        		i=i+1;
        	}

        	
        	afficheTauxDAttaque(taux);
        	afficheStatus(i,nbLapins,nbRenards);
        	boolean affichage=false;
        	if(extinctionRenards==true || disparuRenards==true||remonteRenards==true)
        	{
        		afficheResultat("renards",extinctionRenards, disparuRenards,remonteRenards);
        		affichage=true;
        	}
        	if(extinctionLapins==true || disparuLapins==true||remonteLapins==true)
        	{
        		afficheResultat("lapins",extinctionLapins, disparuLapins,remonteLapins);
        		affichage=true;
        	}
        	if(affichage==false)
        	{
        		System.out.println("Les lapins et les renards ont des populations stables.");
        	}    
        	
        	taux=taux+0.01;

    	}
    	
    }
    
    static void afficheResultat(String animal, boolean menacesExtinction, boolean disparus, boolean remonte)
    {
    	if(menacesExtinction==true)
    	{
    		 System.out.println("Les "+animal+" ont été en voie d'extinction");
    	    	if(remonte==true)
    	    	{
    	    	    System.out.println("mais la population est remontée ! Ouf !");
    	    	}
    	}
    	if(disparus==true)
    	{
    		 System.out.println("et les "+animal+" ont disparus :-(");
    	}
    }

   
    /*******************************************
     * ne rien modifier apres cette ligne
     *******************************************/

    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);

        // Saisie des populations initiales
        double renardInit = entrerPopulation("renards", 2.0, clavier);
        double lapinsInit = entrerPopulation("lapins", 5.0, clavier);

        // ===== PARTIE 1 =====
        // Première simulation
        // Evolution de la population avec les paramètres initiaux

        simule(renardInit, lapinsInit, TAUX_ATTAQUE_INIT, TAUX_ATTAQUE_INIT);

        // ===== PARTIE 2 =====
        // Variation du taux d'attaque
        System.out.println("");

        double tauxInit = 0.0;
        double tauxFin = 0.0;

        do {
            System.out.print("taux d'attaque au départ en % (entre 0.5 et 6) ? ");
            tauxInit = clavier.nextDouble();
        } while ((tauxInit < 0.5) || (tauxInit > 6.0));

        do {
            System.out.print("taux d'attaque à la fin  en % (entre ");
            System.out.print(tauxInit);
            System.out.print(" et 6) ? ");
            tauxFin = clavier.nextDouble();
        } while ((tauxFin < tauxInit) || (tauxFin > 6.0));

        tauxInit /= 100.0;
        tauxFin  /= 100.0;


        simule(renardInit, lapinsInit, tauxInit, tauxFin);
    }
}
