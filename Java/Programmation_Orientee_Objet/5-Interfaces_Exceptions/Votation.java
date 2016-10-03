import java.util.ArrayList;
import java.util.Random;

/*******************************************
 * Completez le programme à partir d'ici.
 *******************************************/
class Postulant{
	private String nom;
	private int nbElecteurs;
	
	public Postulant(String n, int nb){
		this.nom=n;
		this.nbElecteurs=nb;
	}
	
	public Postulant(String n){
		this.nom=n;
		this.nbElecteurs=0;
	}
	
	public Postulant(Postulant p){
		this.nom=p.nom;
		this.nbElecteurs=p.nbElecteurs;
	}
	
	public void elect(){
		this.nbElecteurs +=1;
	}
	
	public void init(){
		this.nbElecteurs =0;
	}
	
	public int getVotes(){
		return this.nbElecteurs;
	}
	
	public String getNom(){
		return this.nom;
	}
	
}

class Scrutin{
	private ArrayList<Postulant> listPostulants;
	private int maxVotants;
	private int date;
	private ArrayList<Vote> votes;
	
	public Scrutin(ArrayList<Postulant> p, int m, int d, boolean init){
		this.listPostulants=new ArrayList<Postulant>();
		if(p!=null){
			for(int i=0; i<p.size();i++){
				Postulant pos= new Postulant(p.get(i));
				this.listPostulants.add(pos);
			}
		}
		this.maxVotants=m;
		this.date=d;
		if(init){
			for(int i=0; i<this.listPostulants.size();i++){
				this.listPostulants.get(i).init();
			}
		}
		this.votes=new ArrayList<Vote>();
	}
	
	public Scrutin(ArrayList<Postulant> p, int m, int d){
		this.listPostulants=new ArrayList<Postulant>();
		if(p!=null){
			for(int i=0; i<p.size();i++){
				Postulant pos= new Postulant(p.get(i));
				this.listPostulants.add(pos);
			}
		}
		this.maxVotants=m;
		this.date=d;
		for(int i=0; i<this.listPostulants.size();i++){
			this.listPostulants.get(i).init();
		}
		this.votes=new ArrayList<Vote>();
	}
	
	public int calculerVotants(){
		int res=0;
		for(int i=0; i<this.listPostulants.size();i++){
			res=res+this.listPostulants.get(i).getVotes();
		}
		return res;
	}
	
	public String gagnant(){
		String res = listPostulants.get(0).getNom();
		int max = listPostulants.get(0).getVotes();
		for(int i=1; i<listPostulants.size();i++){
			if (this.listPostulants.get(i).getVotes()>=max){
				res = listPostulants.get(i).getNom();
				max = listPostulants.get(i).getVotes();
			}
		}
		return res;
	}
	
	public void resultats(){
		if(this.calculerVotants()!=0){
			double ratio = (double)this.calculerVotants()/(double)this.maxVotants*100.0;
			System.out.println("Taux de participation -> "+String.format("%.1f",ratio)+" pour cent");
			System.out.println("Nombre effectif de votants -> "+this.calculerVotants());
			System.out.println("Le chef choisi est -> "+this.gagnant());
			System.out.println();
			System.out.println("Répartition des electeurs");
		
			for(int i=0; i<listPostulants.size();i++){
				System.out.println(this.listPostulants.get(i).getNom()+" -> "+String.format("%.1f",(double)this.listPostulants.get(i).getVotes()/(double)this.calculerVotants()*100.0)+" pour cent des électeurs");
			}	
		}else{
			System.out.println("Scrutin annulé, pas de votants");
		}
		System.out.println();
	}
	
	public void compterVotes(){
		for(int i=0; i<this.listPostulants.size();i++){
			for (int j = 0; j<votes.size();j++){
				if(votes.get(j).getPostulant().equals(this.listPostulants.get(i).getNom()) && !votes.get(j).estInvalide()){
					this.listPostulants.get(i).elect();
				}
			}
		}
	}
	
	public void simuler(double taux, int d){
		int nbVotant=(int)(this.maxVotants*taux);
		for (int i = 0; i<nbVotant;i++){
			int candNum = Utils.randomInt(this.listPostulants.size());
			if(i%3==0){
				String nomCandidat=this.listPostulants.get(candNum).getNom();
				Vote vote=new BulletinElectronique(nomCandidat,d,this.date);
				System.out.println(vote);
				this.votes.add(vote);
			}
			if(i%3==1){
				String nomCandidat=this.listPostulants.get(candNum).getNom();
				Vote vote;
				if(i%2==0){
					vote=new BulletinPapier(nomCandidat,d,this.date,false);					
				}else{
					vote=new BulletinPapier(nomCandidat,d,this.date,true);
				}
				System.out.println(vote);
				this.votes.add(vote);
			}
			if(i%3==2){
				String nomCandidat=this.listPostulants.get(candNum).getNom();
				Vote vote;
				if(i%2==0){
					vote=new BulletinCourrier(nomCandidat,d,this.date,false);
				}else{
					vote=new BulletinCourrier(nomCandidat,d,this.date,true);
				}
				System.out.println(vote);
				this.votes.add(vote);
			}
		}
	}
		
}

abstract class Vote{
	private String nomVote;
	private int dateEff;
	private int dateLim;
	
	public Vote(String n, int de, int dl){
		this.nomVote=n;
		this.dateEff=de;
		this.dateLim=dl;
	}
	
	abstract public boolean estInvalide();
	
	public int getDate(){
		return this.dateEff;
	}
	
	public int getDateLimite(){
		return this.dateLim;
	}
	
	public String getPostulant(){
		return this.nomVote;
	}
	
	public String toString(){
		if (this.estInvalide()){
			return " pour "+this.nomVote+" -> invalide";
		}else{
			return " pour "+this.nomVote+" -> valide";
		}
	}
	
}

class BulletinPapier extends Vote{
	private boolean signe;
	
	public BulletinPapier(String n, int de, int dl, boolean s){
		super(n,de,dl);
		this.signe=s;
	}
	
	public boolean estInvalide(){
		return(!signe);
	}
	
	public String toString(){
		if (this.estInvalide()){
			return "vote par bulletin papier pour "+this.getPostulant()+" -> invalide";
		}else{
			return "vote par bulletin papier pour "+this.getPostulant()+" -> valide";
		}
	}
	
}

class BulletinCourrier extends BulletinPapier implements CheckBulletin{
	
	public BulletinCourrier(String n, int de, int dl, boolean s){
		super(n,de,dl,s);
	}
	
	public boolean checkDate(){
		return (this.getDateLimite()>=this.getDate());
	}
	
	public boolean estInvalide(){
		return (super.estInvalide() || !this.checkDate());
	}
	
	public String toString(){
		if (this.estInvalide()){
			return "envoi par courrier d'un vote par bulletin papier pour "+this.getPostulant()+" -> invalide";
		}else{
			return "envoi par courrier d'un vote par bulletin papier pour "+this.getPostulant()+" -> valide";
		}
	}
	
}


class BulletinElectronique extends Vote implements CheckBulletin{
	
	public BulletinElectronique(String n, int de, int dl){
		super(n,de,dl);
	}
	
	public boolean checkDate(){
		return (this.getDateLimite()-2>=this.getDate());
	}
	
	public boolean estInvalide(){
		return (!this.checkDate());
	}
	
	public String toString(){
		if (this.estInvalide()){
			return "vote electronique pour "+this.getPostulant()+" -> invalide";
		}else{
			return "vote electronique pour "+this.getPostulant()+" -> valide";
		}
	}
}

interface CheckBulletin{
	boolean checkDate();
}


/*******************************************
 * Ne pas modifier les parties fournies
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/

class Utils {

    private static final Random RANDOM = new Random();

    // NE PAS UTILISER CETTE METHODE DANS LES PARTIES A COMPLETER
    public static void setSeed(long seed) {
        RANDOM.setSeed(seed);
    }

    // génère un entier entre 0 et max (max non compris)
    public static int randomInt(int max) {
        return RANDOM.nextInt(max);
    }
}

/**
 * Classe pour tester la simulation
 */

class Votation {

    public static void main(String args[]) {
        Utils.setSeed(20000);
        // TEST 1
        System.out.println("Test partie I:");
        System.out.println("--------------");

        ArrayList<Postulant> postulants = new ArrayList<Postulant>();
        postulants.add(new Postulant("Tarek Oxlama", 2));
        postulants.add(new Postulant("Nicolai Tarcozi", 3));
        postulants.add(new Postulant("Vlad Imirboutine", 2));
        postulants.add(new Postulant("Angel Anerckjel", 4));

        // 30 -> nombre maximal de votants
        // 15 jour du scrutin
        Scrutin scrutin = new Scrutin(postulants, 30, 15, false);

        scrutin.resultats();

        // FIN TEST 1

        // TEST 2
        System.out.println("Test partie II:");
        System.out.println("---------------");

        scrutin = new Scrutin(postulants, 20, 15);
        // tous les bulletins passent le check de la date
        // les parametres de simuler sont dans l'ordre:
        // le pourcentage de votants et le jour du vote
        scrutin.simuler(0.75, 12);
        scrutin.compterVotes();
        scrutin.resultats();

        scrutin = new Scrutin(postulants, 20, 15);
        // seuls les bulletins papier non courrier passent
        scrutin.simuler(0.75, 15);
        scrutin.compterVotes();
        scrutin.resultats();

        scrutin = new Scrutin(postulants, 20, 15);
        // les bulletins electroniques ne passent pas
        scrutin.simuler(0.75, 15);
        scrutin.compterVotes();
        scrutin.resultats();
        //FIN TEST 2

    }
}
