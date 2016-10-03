import java.util.Scanner;

class Cycliste {
    private static Scanner clavier = new Scanner(System.in);
    public static void main(String[] args) {

        double t = 0.0;    // temps, en min.
        double d = 0.0;    // distance, en km
        double v = 30.0;   // vitesse, en km/h
        double acc = 0.0;  // accélération, en km/h/min
        double p = 175.0;  // puissance, en W

        /******************************************
         * Completez le programme a partir d'ici.
         *******************************************/

        double masse;
        do{
        	System.out.print("masse du cycliste (entre 40 et 180 ) ? ");
			masse = clavier.nextDouble();
		}while(masse<40.0 || masse>180.0);
        
        double vitesseVent;
        do{
            System.out.print("vent (entre -20 et +20 km/h) ? ");
            vitesseVent = clavier.nextDouble();
		}while(vitesseVent<-20.0 || vitesseVent>+20.0); 
        
        double distanceTotale;
        do{
            System.out.print("distance du parcours (<= 200 km) ? ");
            distanceTotale = clavier.nextDouble();
		}while(distanceTotale<11.0 || distanceTotale>200.0); 
        
        double distanceMontee;
        double max = distanceTotale - 10.0;
        do{
        	System.out.print("distance au sommet du col (<= ");
        	System.out.print(max);
            System.out.print(" km) ? ");
            distanceMontee = clavier.nextDouble();
		}while(distanceMontee<=0.0 || distanceMontee > max); 
        
        double penteMontee;
        do{
            System.out.print("pente moyenne jusqu'au sommet (<= 20 %) ? ");
            penteMontee = clavier.nextDouble();
		}while(penteMontee<=0.0 || penteMontee>20.0); 
        
        double penteDescente;
        do{
            System.out.print("pente moyenne après le sommet (<= 20 %) ? ");
            penteDescente = clavier.nextDouble();
		}while(penteDescente<=0.0 || penteDescente>20.0); 

        double dt = 1/60.0;
        double pMin = 10.0;     
        boolean col = false;
        
        while(d < distanceTotale)
        {
            double roundedTime = Math.round(t);
            if (Math.abs(roundedTime - t) < 1e-5 && (int)roundedTime % 5 == 0) {
                System.out.format("%.0f, %.2f, %.2f, %.4f,  %.2f\n",
                                  t, d, v, acc, p);
            }
            
            t = t + dt;
            
            double vReelle = v - vitesseVent;
            
            if(d < distanceMontee && p>pMin)
            {   
            		p = p - 0.5*dt;         	
            }
            if(d>=distanceMontee){
            	p=pMin;
            }
            
            double pente;
            if(d < distanceMontee)
            {
            	pente = penteMontee;
            }  
            else{
            	pente = - penteDescente;
            }
            
            acc = -2118.96 * Math.sin(Math.atan(pente/100));
            acc = acc - 5 * vReelle*Math.abs(vReelle)/masse;
            
            if(p>=0.0 && v>=0.0)
            {
            	acc = acc + 777.6*p/(v*masse);
            }
            
            if(Math.abs(acc)<1e-5)
            {
            	acc = 0.0;
            }
            
            v = v + acc*dt;
            d = d + v*dt/60.0;
            
            if(d > distanceMontee && col == false)
            {
                System.out.format("## Bernard a atteint le sommet en %.0f min.\n", t);
                col=true;
            }
                                
            if(d < distanceMontee && v < 3.0)
            {
                System.out.println("## Bernard abandonne, il n'en peut plus");
                System.out.format("%.0f, %.2f, %.2f, %.4f,  %.2f\n",
                        t, d, v, acc, p);
                return;
            }
            
        }
        


        // conditions spéciales


        /*******************************************
         * Ne rien modifier apres cette ligne.
         *******************************************/

        System.out.println("## Bernard est arrivé");
        System.out.format("%.0f, %.2f, %.2f, %.4f, %.2f\n",
                          t, d, v, acc, p);
    }
}
