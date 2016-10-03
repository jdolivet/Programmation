import java.util.Scanner;

public class Champi {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);

        /*******************************************
         * Completez le programme a partir d'ici.
         *******************************************/

        System.out.println("Pensez a un champignon : amanite tue mouches, pied bleu, girolle,");
        System.out.println("cèpe de Bordeaux, coprin chevelu ou agaric jaunissant.");

       System.out.print("Est-ce que votre champignon a un chapeau convexe (true : oui, false : non) ? ");
       boolean chapeau = clavier.nextBoolean();
       System.out.print("Est-ce que votre champignon vit en forêt (true : oui, false : non) ? ");
       boolean foret = clavier.nextBoolean();   
       
       if(chapeau==true && foret==false)
       {
           System.out.print("==> Le champignon auquel vous pensez est ");
           System.out.print("l'agaric jaunissant");    	   
       }
       if(chapeau==false && foret==false)
       {
    	   System.out.print("==> Le champignon auquel vous pensez est ");
    	   System.out.print("le coprin chevelu");			
       }
       if(chapeau==false && foret==true)
       {
           System.out.print("Est-ce que votre champignon a des lamelles (true : oui, false : non) ? ");
           boolean lamelles = clavier.nextBoolean();   
           if(lamelles==false)
           {
        	   System.out.print("==> Le champignon auquel vous pensez est ");
        	   System.out.print("le cèpe de Bordeaux");
           }
           else{
        	   System.out.print("==> Le champignon auquel vous pensez est ");
               System.out.print("la girolle");      	   
           }  	   
       }
       if(chapeau==true && foret==true)
       {
           System.out.print("Est-ce que votre champignon a un anneau (true : oui, false : non) ? ");
           boolean anneau = clavier.nextBoolean();  
           if(anneau==true)
           {
        	   System.out.print("==> Le champignon auquel vous pensez est ");
               System.out.print("l'amanite tue-mouches");
           }
           else{
        	   System.out.print("==> Le champignon auquel vous pensez est ");
               System.out.print("le pied bleu");   	   
           }  	   
       }       
        
      
        /*******************************************
         * Ne rien modifier apres cette ligne.
         *******************************************/

        clavier.close();
    }
}