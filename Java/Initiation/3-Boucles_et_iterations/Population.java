import java.util.Scanner;
import java.text.DecimalFormat;

public class Population {
    public static void main(String[] args) {

        DecimalFormat df = new DecimalFormat("#0.000");
        Scanner keyb = new Scanner(System.in);

        int anneeInitiale = 2011;        // annee initiale
        double tauxCroissance = 1.2;     // taux de croissance, en %
        double populationInitiale = 7.0; // population initiale, en milliard d'humains

        double populationCourante = populationInitiale; // population mondiale pour l'annee courante
        int anneeCourante = anneeInitiale;              // annee de calcul

        System.out.println("====---- PARTIE 1 ----====");
        System.out.println("Population en " + anneeCourante + " : " + df.format(populationCourante));

        /*******************************************
         * Completez le programme a partir d'ici.
         *******************************************/

        // ===== PARTIE 1 =====
		// utilisez cette instruction pour poser votre question :
        int anneeFinale;
        do{
			System.out.print("Quelle année (> " + anneeInitiale + ") ? ");
			anneeFinale = keyb.nextInt();
		}while(anneeFinale <= anneeInitiale);
			 
		int nb = anneeFinale - anneeInitiale;
		double populationFinale = populationInitiale * Math.exp(nb*tauxCroissance/100.0);

		System.out.println("Population en " + anneeFinale + " : " + df.format(populationFinale));
		
        // ===== PARTIE 2 =====
        System.out.println("\n====---- PARTIE 2 ----====");
        
        double populationCible;
        do{
        	System.out.print("Combien de milliards (> " + populationInitiale + ") ? ");
        	populationCible = keyb.nextDouble();
		}while(populationCible <= populationInitiale);
        
        double populationAnnuelle = populationInitiale;
        int annee = anneeInitiale;
        int count = 0;
        while(populationAnnuelle <= populationCible){
        	++ count;
        	populationAnnuelle = populationInitiale * Math.exp(count*tauxCroissance/100.0);
        	annee = anneeInitiale + count;
    		System.out.println("Population en " + annee + " : " + df.format(populationAnnuelle));        	
        }

        // ===== PARTIE 3 =====
        System.out.println("\n====---- PARTIE 3 ----====");
        int multiple = 2;
        populationAnnuelle = populationInitiale;
        annee = anneeInitiale;
        count = 0;
        while(populationAnnuelle <= populationCible){
        	++ count;
        	populationAnnuelle = populationAnnuelle * Math.exp(tauxCroissance/100.0);
        	if(populationAnnuelle>=multiple*populationInitiale)
        	{
        		multiple = 2*multiple;
        		tauxCroissance= tauxCroissance/2.0;
        	}
        	annee = anneeInitiale + count;
    		System.out.println("Population en " + annee + " : " + df.format(populationAnnuelle) +" ; taux de croissance : "+tauxCroissance+"%");        	
        }


        /*******************************************
         * Ne rien modifier apres cette ligne.
         *******************************************/
    }
}
