/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
import java.util.ArrayList;

class OptionVoyage{
	private String nom;
	private double prix;
	
	public OptionVoyage(String n, double p){
		this.nom=n;
		this.prix=p;
	}
	
	public String getNom(){
		return nom;
	}
	
	public double prix(){
		return prix;
	}
	
	public String toString(){
		return nom + " -> "+this.prix()+" CHF";
	}	
}

class Sejour extends OptionVoyage{
	private int nbNuits;
	private double prixNuit;
	
	public Sejour(String name, double pf, int n, double p){
		super(name, pf+n*p);
	}
	
	public double prix(){
		return super.prix();
	}
}

class Transport extends OptionVoyage{
	private boolean longueur;
	public static final double TARIF_LONG=1500.0;
	public static final double TARIF_BASE=200.0;
	
	public Transport(String name, double p){
		super(name, p);
		this.longueur=false;
	}
	
	public Transport(String name, double p, boolean l){
		super(name, p);
		this.longueur=l;
	}
	
	public double prix(){
		if(longueur){
			return (TARIF_LONG+super.prix());
		}else{
			return (TARIF_BASE+super.prix());
		}
	}
	
}

class KitVoyage{
	private ArrayList<OptionVoyage> listOptions;
	private String depart;
	private String destination;
	
	public KitVoyage(String i, String f){
		this.depart=i;
		this.destination=f;
		listOptions=new ArrayList<OptionVoyage>();
	}
	
	public double prix(){
		double tot=0.0;
		for(int i = 0 ; i < listOptions.size(); i++){
			tot=tot+listOptions.get(i).prix();
		}
		return tot;
	}
	
	public String toString(){
		String res = "Voyage de "+this.depart+" à "+this.destination+", avec pour options :\n";
		for(int i = 0 ; i < listOptions.size(); i++){
			res=res+"   - "+listOptions.get(i).toString()+"\n";
		}
		res=res+"Prix total : "+prix()+" CHF\n";
		return res;
	}
	
	public KitVoyage ajouterOption(OptionVoyage o){
		if(o!=null){
			listOptions.add(o);
		}
		return this;
	}
	
	public KitVoyage annuler(){
		listOptions.clear();
		return this;
	}
	
	public int getNbOptions(){
		int tot = 0;
		for(int i = 0 ; i < listOptions.size(); i++){
			tot = tot + 1;
		}
		return tot;
	}
	
}

/*******************************************
 * Ne pas modifier apres cette ligne
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/

public class Voyage {
    public static void main(String args[]) {

        // TEST 1
        System.out.println("Test partie 1 : ");
        System.out.println("----------------");
        OptionVoyage option1 = new OptionVoyage("Séjour au camping", 40.0);
        System.out.println(option1.toString());

        OptionVoyage option2 = new OptionVoyage("Visite guidée : London by night" , 50.0);
        System.out.println(option2.toString());
        System.out.println();

        // FIN TEST 1


        // TEST 2
        System.out.println("Test partie 2 : ");
        System.out.println("----------------");

        Transport transp1 = new Transport("Trajet en car ", 50.0);
        System.out.println(transp1.toString());

        Transport transp2 = new Transport("Croisière", 1300.0);
        System.out.println(transp2.toString());

        Sejour sejour1 = new Sejour("Camping les flots bleus", 20.0, 10, 30.0);
        System.out.println(sejour1.toString());
        System.out.println();

        // FIN TEST 2


        // TEST 3
        System.out.println("Test partie 3 : ");
        System.out.println("----------------");

        KitVoyage kit1 = new KitVoyage("Zurich", "Paris");
        kit1.ajouterOption(new Transport("Trajet en train", 50.0));
        kit1.ajouterOption(new Sejour("Hotel 3* : Les amandiers ", 40.0, 5, 100.0));
        System.out.println(kit1.toString());
        System.out.println();
        kit1.annuler();

        KitVoyage kit2 = new KitVoyage("Zurich", "New York");
        kit2.ajouterOption(new Transport("Trajet en avion", 50.0, true));
        kit2.ajouterOption(new Sejour("Hotel 4* : Ambassador Plazza  ", 100.0, 2, 250.0));
        System.out.println(kit2.toString());
        kit2.annuler();

        // FIN TEST 3
    }
}

