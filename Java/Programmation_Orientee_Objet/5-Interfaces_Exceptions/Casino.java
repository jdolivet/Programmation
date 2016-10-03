import java.util.ArrayList;

/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
abstract class Mise{
	private int nbJetons;
	
	public Mise(int t){
		this.nbJetons=t;
	}
	
	public int getMise(){
		return this.nbJetons;
	}
	
	public abstract int gain(int n);
		
	
}

class Pleine extends Mise{
	private int numero;
	public static final int FACTEUR_GAIN=35;
	
	public Pleine(int nb, int n){
		super(nb);
		this.numero=n;
	}
	
	public int gain(int n){
		if (this.numero==n){
			return this.getMise()*FACTEUR_GAIN;
		}
		else{
			return 0;
		}
	}
	
}

class Rouges extends Mise{
	public static final int ROUGES [] = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36 };
	
	public Rouges(int nb){
		super(nb);
	}
	
	public int gain(int n){
		boolean contain = false;
		for(int i = 0; i<ROUGES.length;i++){
			if(ROUGES[i]==n){
				contain = true;
			}
		}
		if (contain){
			return this.getMise();
		}
		else{
			return 0;
		}
	}
	
}

class Joueur{
	private String name;
	private Mise strategie;
	
	public Joueur(String n){
		this.name=n;
	}
	
	public String getNom(){
		return this.name;
	}
	
	public void setStrategie(Mise mise){
		strategie=mise;
	}
	
	public int getMise(){
		if(this.strategie==null){
			return 0;
		}
		else{
			return this.strategie.getMise();
		}
	}
	
	public int gain(int n){
		if(this.strategie==null){
			return 0;
		}
		else{
			return this.strategie.gain(n);
		}
	}
	
}

abstract class Roulette{
	private ArrayList<Joueur> listJoueurs;
	private int gainTotal;
	private int numeroTire;
	
	public Roulette(){
		listJoueurs = new ArrayList<Joueur>();
	}
	
	public void participe(Joueur j){
		listJoueurs.add(j);
	}
	
	public int getTirage(){
		return numeroTire;
	}
	
	public int getGainMaison(){
		return gainTotal;
	}
	
	public int getParticipants(){
		return listJoueurs.size();
	}
	
	public void rienNeVaPlus(int n){
		numeroTire=n;
	}
	
	abstract public int perteMise(int miseDuJoueur);
	
	public void calculerGainMaison(){
		this.gainTotal=0;
		for(int i = 0; i<listJoueurs.size(); i++){
			if(listJoueurs.get(i).gain(this.numeroTire)==0){
				this.gainTotal=this.gainTotal + perteMise(listJoueurs.get(i).getMise());
			}else{
				this.gainTotal=this.gainTotal - listJoueurs.get(i).gain(this.numeroTire);
			}
		}		
	}
	
	public String toString(){
		calculerGainMaison();
		String res = "Croupier : le numéro du tirage est le "+this.numeroTire;
		for(int i = 0; i<listJoueurs.size(); i++){
			if(listJoueurs.get(i).gain(this.numeroTire)==0){
				res=res+"\n"+" Le joueur "+listJoueurs.get(i).getNom()+" mise "+listJoueurs.get(i).getMise()+" et perd";	
			}else{
				res=res+"\n"+" Le joueur "+listJoueurs.get(i).getNom()+" mise "+listJoueurs.get(i).getMise()+" et gagne "+listJoueurs.get(i).gain(this.numeroTire);	
			}
		}
		res = res + "\n"+"Gain/perte du casino : " + this.getGainMaison();
		return res;
	}
	
}

class RouletteFrancaise extends Roulette{

	
	public RouletteFrancaise(){
		super();
	}
	
	public int perteMise(int miseDuJoueur){
		return miseDuJoueur;
	}
	
}

class RouletteAnglaise extends Roulette implements ControleJoueurs{
	
	public RouletteAnglaise(){
		super();
	}
	
	public int perteMise(int miseDuJoueur){
		return miseDuJoueur /2;
	}
	
	public boolean check(){
			return this.getParticipants()<10;
	}
	
	public void participe(Joueur j){
		if(this.check()){
			super.participe(j);
		}
	}
	
}

interface ControleJoueurs{
	boolean check();
}


/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
class Casino {

    private static void simulerJeu(Roulette jeu) {
        for (int tirage : new int [] { 12, 1, 31 }) {
            jeu.rienNeVaPlus(tirage);
            jeu.calculerGainMaison();
            System.out.println(jeu);
            System.out.println("");
        }
    }

    public static void main(String[] args) {

        Joueur joueur1 = new Joueur("Dupond");
        joueur1.setStrategie(new Pleine(100,1)); // miser 100 jetons sur le 1

        Joueur joueur2 = new Joueur("Dupont");
        joueur2.setStrategie(new Rouges(30)); // miser 30 jetons sur les rouges

        Roulette jeu1 = new RouletteAnglaise();
        jeu1.participe(joueur1);
        jeu1.participe(joueur2);

        Roulette jeu2 = new RouletteFrancaise();
        jeu2.participe(joueur1);
        jeu2.participe(joueur2);

        System.out.println("Roulette anglaise :");
        simulerJeu(jeu1);
        System.out.println("Roulette française :");
        simulerJeu(jeu2);
    }
}
