/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

class Employe{
	private final String nom;
	private double revenuMensuel;
	private int taux;
	private double prime;
	private static final int MAX_ESSAIS=5;
	
	public Employe(String n, double rev){
		this.nom=n;
		this.revenuMensuel=rev;
		this.taux =100;
		this.prime=0.0;
		System.out.print("Nous avons un nouvel employé : "+this.nom+",");
	}
	
	public Employe(String n, double rev, int t){
		this.nom=n;
		this.revenuMensuel=rev;
		if (t<10){
			this.taux=10;
		}
		else if (t>100){
			this.taux=100;
		}
		else{
			this.taux=t;
		}
		this.prime=0.0;
		System.out.print("Nous avons un nouvel employé : "+this.nom+",");
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public double getRevenu(){
		return this.revenuMensuel;
	}
	
	public int getTaux(){
		return this.taux;
	}
	
	public double getPrime(){
		return this.prime;
	}
	
	public double revenuAnnuel(){
		return this.getRevenu()*12*this.getTaux()/100.0 + this.getPrime();		
	};
	
	public String toString(){
		String res=this.getNom()+" :\n"+"  Taux d'occupation : "+this.getTaux()+"%. Salaire annuel : "+String.format("%.2f",this.revenuAnnuel())+" francs";
		if (this.getPrime()!=0.0){
			res=res+", Prime : "+String.format("%.2f",this.getPrime());
		}
		return res+".";
	};
	
	public void demandePrime(){
		Scanner clavier = new Scanner(System.in);
		int nbEssais=1;
		do {
			System.out.println("Montant de la prime souhaitée par "+this.nom+" ?");
			try{
				double i =clavier.nextDouble();
				if(i>2.0*this.revenuAnnuel()/100.0){
					System.out.println("Trop cher!");
					++nbEssais;
				}else{
					this.prime = i;
					break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Vous devez introduire un nombre!");
				clavier.nextLine();
				++nbEssais;
			} 
		}while(nbEssais<=MAX_ESSAIS);
		if(nbEssais>MAX_ESSAIS){
			this.prime=0.0;
		}
	}
	
}

class Manager extends Employe{
	private int nbJours;
	private int nvClients;
	public static final int FACTEUR_GAIN_CLIENT=500;
	public static final int FACTEUR_GAIN_VOYAGE=100;
	
	public Manager(String n, double rev, int nb, int nv){
		super(n,rev);
		this.nbJours=nb;
		this.nvClients=nv;
		System.out.println(" c'est un manager.");
	}
	
	public Manager(String n, double rev, int nb, int nv, int t){
		super(n,rev,t);
		this.nbJours=nb;
		this.nvClients=nv;
		System.out.println(" c'est un manager.");		
	}
	
	public double revenuAnnuel(){
		return this.getRevenu()*12*this.getTaux()/100.0 + nvClients*FACTEUR_GAIN_CLIENT + nbJours*FACTEUR_GAIN_VOYAGE + this.getPrime();
	}
	
	public String toString(){
		String res=this.getNom()+" :\n"+"  Taux d'occupation : "+this.getTaux()+"%. Salaire annuel : "+String.format("%.2f",this.revenuAnnuel())+" francs";
		if (this.getPrime()!=0.0){
			res=res+", Prime : "+String.format("%.2f",this.getPrime());
		}
		res=res+".\n"+"  A voyagé "+this.nbJours+" jours et apporté "+this.nvClients+" nouveaux clients.\n";
		return res;
	}
	
}

class Testeur extends Employe{
	private int nbErreurs;
	public static final int FACTEUR_GAIN_ERREURS=10;
	
	public Testeur(String n, double rev,int nb){
		super(n,rev);
		this.nbErreurs=nb;
		System.out.println(" c'est un testeur.");
	}
	
	public Testeur(String n, double rev, int nb, int t){
		super(n,rev,t);
		this.nbErreurs=nb;
		System.out.println(" c'est un testeur.");		
	}
	
	public double revenuAnnuel(){
		return this.getRevenu()*12*this.getTaux()/100.0 + nbErreurs*FACTEUR_GAIN_ERREURS + this.getPrime();
	}
	
	public String toString(){
		String res=this.getNom()+" :\n"+"  Taux d'occupation : "+this.getTaux()+"%. Salaire annuel : "+String.format("%.2f",this.revenuAnnuel())+" francs";
		if (this.getPrime()!=0.0){
			res=res+", Prime : "+String.format("%.2f",this.getPrime());
		}
		res=res+".\n"+"  A corrigé "+this.nbErreurs+" erreurs.\n";
		return res;
	}
	
}

class Programmeur extends Employe{
	private int projets;
	public static final int FACTEUR_GAIN_PROJETS=200;
	
	public Programmeur(String n, double rev,int p){
		super(n,rev);
		this.projets=p;
		System.out.println(" c'est un programmeur.");
	}
	
	public Programmeur(String n, double rev,int p, int t){
		super(n,rev,t);
		this.projets=p;
		System.out.println(" c'est un programmeur.");		
	}
	
	public double revenuAnnuel(){
		return this.getRevenu()*12*this.getTaux()/100.0 + projets*FACTEUR_GAIN_PROJETS + this.getPrime();
	}
	
	public String toString(){
		String res=this.getNom()+" :\n"+"  Taux d'occupation : "+this.getTaux()+"%. Salaire annuel : "+String.format("%.2f",this.revenuAnnuel())+" francs";
		if (this.getPrime()!=0.0){
			res=res+", Prime : "+String.format("%.2f",this.getPrime());
		}
		res=res+".\n"+"  A mené à bien "+this.projets+" projets\n";
		return res;
	}
	
}


/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
class Employes {
    public static void main(String[] args) {

        List<Employe> staff = new ArrayList<Employe>();

        // TEST PARTIE 1:

        System.out.println("Test partie 1 : ");
        staff.add(new Manager("Serge Legrand", 7456, 30, 4 ));
        staff.add(new Programmeur("Paul Lepetit" , 6456, 3, 75 ));
        staff.add(new Testeur("Pierre Lelong", 5456, 124, 50 ));

        System.out.println("Affichage des employés : ");
        for (Employe modele : staff) {
            System.out.println(modele);
        }
        // FIN TEST PARTIE 1
        // TEST PARTIE 2
        System.out.println("Test partie 2 : ");

        staff.get(0).demandePrime();

        System.out.println("Affichage après demande de prime : ");
        System.out.println(staff.get(0));

        // FIN TEST PARTIE 2
    }
}

