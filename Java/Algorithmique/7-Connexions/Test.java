import java.util.LinkedList;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;




public class Test {

    // les variables où sera stocké le graphe
    public static Ville[] villes;
    public static LinkedList<Integer>[] voisins;


    // pour charger les cartes
    static Carte carte;
    static final String chemin ="./"; // data file location
    Carte ens = new Carte(chemin+"mf.txt");
    
    final static int INEXPLORE = 0;
    final static int VISITE = 2;
    final static int TRAITEMENT = 1;
    
    // variables qui serviront au test
    public static int v1, v2, v3, v4, v5;

    static void construitGraphe(Collection<Ville> cv, double minDist) {
	int n = cv.size();
	villes = new Ville[n];
	voisins = new LinkedList[n]; // cette instruction cause un warning à la compilation
	// que vous pouvez ignorer

	int k = 0;
	for (Ville v : cv) {
	    villes[k] = v;
	    k++;
	}

	// ces deux instructions suivantes ne sont utiles que pour la version optimisée
	// de construitGraphe : deux villes dont les latitudes diffèrent de plus de latDist
	// sont forcément à une distance de plus de minDist 
	double R = 6371000;
	double latDist = minDist * 180.0 / Math.PI / R;
	// on rappelle aussi qu'on peut trier le tableau villes par Arrays.sort(villes)


	// à compléter
        int num_ville=0;
        for (Ville v:cv){
            LinkedList voisin_temp=new LinkedList();
            int num_voisin=0;
            for (Ville w:cv){
                if(num_ville!=num_voisin && v.distance(w)<minDist){
                    voisin_temp.addFirst(num_voisin);
                }
                num_voisin++;
            }
            voisins[num_ville]=voisin_temp;
            num_ville++;
        }
    }

    public static int compteCC(Ville[] villes, LinkedList<Integer>[] voisins) {
	// à compléter
        int compteur = 0;
        HashMap<Ville, Integer> c = new HashMap<Ville, Integer>();
        for(Ville v:villes){
            c.put(v,INEXPLORE);
        }
        int k=0;
        for(Ville v:villes){
            if(c.get(v)==0){
                c.put(v,TRAITEMENT);
                compteur++;
                DFS(villes[k],c,voisins[k]);
            }
            k++;
        }
    return(compteur);    
    }
    
    static void DFS(Ville v,HashMap<Ville,Integer> etat,LinkedList<Integer> vois){
        etat.put(v,TRAITEMENT);       
        for(int j:vois){
            if(etat.get(villes[j])==0){
                DFS(villes[j],etat,voisins[j]);
            }
        }
        etat.put(v,VISITE);
        return;
    }

   public static boolean relie(Ville[] villes, LinkedList<Integer>[] voisins, int v1, int v2) {
	// à compléter
       HashMap<Ville, Integer> c = new HashMap<Ville, Integer>();
       for(int k=0;k<villes.length;k++){
           c.put(villes[k],INEXPLORE);
           }  
       return DFS2(villes[v1],c,voisins[v1],v2);
   }
   
   static boolean DFS2(Ville v,HashMap<Ville,Integer> etat,LinkedList<Integer> vois,int v2){
       etat.put(v, TRAITEMENT);
       if (v==villes[v2]){
           return true;
       }
       for(int j:vois){
           if(etat.get(villes[j])==INEXPLORE && DFS2(villes[j],etat,voisins[j],v2)){
               return true;
           }
       }
       etat.put(v, VISITE);
       return false;
   }

    // trouve l'indice de la première ville nommée s
  static int premiereVille(String s) {
	for (int i = 0; i<villes.length; i++)
	    if (s.equals(villes[i].getNom())) return (i);
	return(-1);
    }

  public static void initMayotte(double minDist){
	Carte ens = new Carte(chemin+"mf.txt");
	construitGraphe(ens.villes(), minDist);

  }

  public static void initFrance(double minDist){
	Carte ens = new Carte(chemin+"fr.txt");
	construitGraphe(ens.villes(), minDist);
  }

    // cette fonction teste vos fonctions sur Mayotte
    public static void test1(double minDist) {
	System.out.println("Mayotte, pas de "+(int)minDist);
	initMayotte(minDist);

	  v1 = premiereVille("Accua") ;
	  v2 = premiereVille("Moutsamoudou");
	  v3 = premiereVille("Bandraboua");
	  v4 = premiereVille("Mambutzou");


	System.out.println("nb composantes : "+compteCC(villes, voisins));
	System.out.println(relie(villes, voisins, v1, v2));
	System.out.println(relie(villes, voisins, v1, v3));
	System.out.println(relie(villes, voisins, v2, v3));
	System.out.println(relie(villes, voisins, v2, v4));

    }


    // cette fonction teste vos fonctions sur la France
    // construitGraphe peut prendre du temps s'il n'est pas optimisé
    // par ailleurs, on doit sans doute augmenter la taille de la pile

    public static void test2(double minDist) {

	System.out.println("France, pas de "+minDist);
	initFrance(minDist);
	System.out.println("composantes : "+compteCC(villes, voisins));
	
	v1 = premiereVille("Paris") ;
	v2 = premiereVille("Rouen");
	v3 = premiereVille("Ajaccio");
	v4 = premiereVille("Narbonne");
	v5 = premiereVille("La Testa");
	System.out.println(relie(villes, voisins, v1, v2));
	System.out.println(relie(villes, voisins, v1, v3));
	System.out.println(relie(villes, voisins, v1, v4));
	System.out.println(relie(villes, voisins, v3, v5));
	System.out.println(relie(villes, voisins, v1, v5));
    }

    public static void main (String[] args) {

	//test1(1850);
	//test1(2000);
	//test1(3000);
	//test1(3400);
	//test1(4000);

	// tests sur la carte de France
	// peuvent être longs voire demander d'augmenter la taille de la pile
	//test2(2000);
	//test2(5000);
	//test2(7000);
	test2(12000);



    }
}
