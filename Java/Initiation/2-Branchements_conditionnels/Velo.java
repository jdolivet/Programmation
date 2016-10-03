import java.util.Scanner;

public class Velo {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        System.out.print("Donnez l'heure de début de la location (un entier) : ");
        int debut = clavier.nextInt();
        System.out.print("Donnez l'heure de fin de la location (un entier) : ");
        int fin = clavier.nextInt();

        /*******************************************
         * Completez le programme a partir d'ici.
         *******************************************/
        boolean valid = true;
        if(debut<0 || fin>24 || debut>24 || fin<0)
        {
        	System.out.println("Les heures doivent être comprises entre 0 et 24 !");
        	valid=false;
        }
        
        if(debut==fin && valid==true)
        {
        	System.out.println("Bizarre, vous n'avez pas loué votre vélo bien longtemps !");
        	valid=false;
        }
        if(debut>fin && valid==true)
        {        
        	System.out.println("Bizarre, le début de la location est après la fin ...");
        	valid=false;
        }
        if(valid==true)
        {
	        System.out.println("Vous avez loué votre vélo pendant");
	        float prix;
	        if(fin<7)
	        {
	        	int duree = fin-debut;
	        	prix=duree;
	        	System.out.println(duree +" heure(s) au tarif horaire de 1.0 francs");
	        }
	        else
	        {
		        if(debut>17)
		        {
		        	int duree = fin-debut;
		        	prix=duree;
		        	System.out.println(duree +" heure(s) au tarif horaire de 1.0 francs");
		        }  
		        else
		        {
		        	int duree1 = (Math.max(fin, 17)-17)+(7-Math.min(7, debut));
		        	if(duree1>0)
		        	{
		        		System.out.println(duree1 +" heure(s) au tarif horaire de 1.0 francs");
		        	}
		        	int duree2 = (Math.min(17, fin)-Math.max(debut, 7));
		        	if(duree2>0)
		        	{		        	
		        		System.out.println(duree2 +" heure(s) au tarif horaire de 2.0 francs");
		        	}
		        	prix=duree1+duree2*2;
		        }
		        
	        }
	        System.out.print("Le montant total à payer est de " + prix);
	        System.out.println(" francs.");    
	        
    	}

        /*******************************************
         * Ne rien modifier apres cette ligne.
         *******************************************/

        clavier.close();
    }
}
