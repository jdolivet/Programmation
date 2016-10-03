import java.util.LinkedList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.text.DecimalFormat;




public class Test {

    // pour charger les cartes
    static Carte carte;
    static final String chemin ="./"; // data file location
    Carte ens = new Carte(chemin+"mf.txt");
    


    static HashMap<Ville, HashSet<Ville>> voisins;
    static HashMap<String, HashSet<Ville>> nom;
    static Ville[] villes;

    static void construitGraphe(Collection<Ville> cv, double minDist) {
	int n = cv.size();
	villes = new Ville[n];
	int k = 0;
	for (Ville v : cv) {
	    villes[k] = v;
	    k++;
	}

        
	double R = 6371000;
	double latDist = minDist * 180.0 / Math.PI / R;
	// des villes à moins de minDist l'une de l'autre auront au plus une différence de
	// latitude (ou de longitude)  de latDist 
	

	// indication : on peut trier un tableau de villes par Array.sort
        Arrays.sort(villes);
	// ici construire le graphe des villes en mettant les bonnes valeurs 
	// dans les tables voisins et nom

    nom = new HashMap<String, HashSet<Ville>>();
    voisins = new HashMap<Ville, HashSet<Ville>>();
    String nomVille1, nomVille2;
    Ville v1, v2;
    int l;
    for (int j=0; j<villes.length; j++) {
        v1 = villes[j];
        nomVille1 = v1.getNom();
        if(nom.containsKey(nomVille1)==false){
            nom.put(nomVille1, new HashSet<Ville>());
        }
        nom.get(nomVille1).add(v1);
        l = j;
        while(l<villes.length-1 && (v1.getLatitude()-villes[l+1].getLatitude())<latDist){
            l++;
            v2 = villes[l];
            nomVille2 = v2.getNom();
            if(v1.distance(v2)<=minDist){
                if(voisins.containsKey(v1)==false){
                    voisins.put(v1, new HashSet<Ville>());
                }
                if(voisins.containsKey(v2)==false){
                    voisins.put(v2, new HashSet<Ville>());
                }
                voisins.get(v1).add(v2);
                voisins.get(v2).add(v1);
            }
        }
    }

    }






    static Ville premiereVille(String s) {
	return(nom.get(s).iterator().next());
    }

    static double Dijkstra(Ville orig, Ville dest) {
	// utiliser Dijkstra pour calculer la longueur du plus court chemin
	// entre v1 et v2
	// rendre -1 s'il n'y a pas de chemin
    HashMap<Ville, Double> distances = new HashMap<Ville, Double>();
    PriorityQueue<VilleDist> file = new PriorityQueue<VilleDist>();
    file.add(new VilleDist(orig, 0));
    VilleDist dist;
    Ville courant;
    double distCourant;
    dist = new VilleDist(orig, 0);
    courant = dist.v;
    while (courant.equals(dest)==false && !file.isEmpty()) {
        dist = file.remove();
        courant = dist.v;
        distCourant = dist.d;
        if (!distances.containsKey(courant)){
            distances.put(courant, distCourant);
            if(voisins.containsKey(courant)){
                for (Ville m : voisins.get(courant)) {
                    if (!distances.containsKey(m)){
                        file.add(new VilleDist(m,distCourant+m.distance(courant)));
                    }
                }
            }
        }
    }
    if(file.isEmpty() && dist.equals(dest)==false){
        return (double)(-1);
    }
    else return distances.get(dest).doubleValue();
}




  public static void initMayotte(double minDist){
	Carte ens = new Carte(chemin+"mf.txt");
	construitGraphe(ens.villes(), minDist);
  }

  public static void initFrance(double minDist){
	Carte ens = new Carte(chemin+"fr.txt");
	construitGraphe(ens.villes(), minDist);

  }


    public static void test1(double minDist) {
	System.out.println();
    	System.out.println("Mayotte, pas de "+minDist);
    	initMayotte(minDist);

    	 Ville v1 = premiereVille("Accua") ;
    	 Ville v2 = premiereVille("Moutsamoudou");
    	 Ville v3 = premiereVille("Bandraboua");
    	 Ville v4 = premiereVille("Mambutzou");
	afficheDijkstra(v1, v2);
	afficheDijkstra(v2, v1);
	afficheDijkstra(v1, v3);
	afficheDijkstra(v3, v1);
	afficheDijkstra(v1, v4);
	afficheDijkstra(v4, v1);
	afficheDijkstra(v2, v3);
    }


    public static void afficheDijkstra(Ville v1, Ville v2) {
	DecimalFormat df = new DecimalFormat("#.000");
	double d = Dijkstra(v1,v2);
	String s = "  pas de chemin";
	if (d > 0) s = df.format(Dijkstra(v1,v2) / 1000);

	System.out.println(v1.getNom()+" "+v2.getNom()+" "+s);
    }


    public static void test2(double minDist) {
	System.out.println();
    	System.out.println("France, pas de "+minDist);

    	initFrance(minDist);

    	Ville paris = premiereVille("Paris") ;
    	Ville rouen = premiereVille("Rouen");
	Ville palaiseau = premiereVille("Palaiseau");
	Ville perpignan = premiereVille("Perpignan");
	Ville strasbourg = premiereVille("Strasbourg");
	Ville hagenau = premiereVille("Hagenau");
	Ville brest = premiereVille("Brest");
	Ville hendaye = premiereVille("Hendaye");
		
    	afficheDijkstra(paris, rouen);
    	afficheDijkstra(palaiseau, rouen);
    	afficheDijkstra(palaiseau, paris);
	afficheDijkstra(paris, perpignan);
	afficheDijkstra(hendaye, perpignan);
	afficheDijkstra(paris, strasbourg);
	afficheDijkstra(hagenau, strasbourg);
	afficheDijkstra(hagenau, brest);


    }

    public static void main (String[] args) {

    	//test1(1850);
    	//test1(2000);
    	//test1(3000);
    	//test1(3400);
    	//test1(4000);

	//tests sur la carte de France
	//test2(2000);
	//test2(5000);
	//test2(7000);
	test2(10000);




    }



}
