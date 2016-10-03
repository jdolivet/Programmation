/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
abstract class Navire{
	private int coordX;
	private int coordY;
	private int drapeau;
	private boolean detruit;
	
	public Navire(int x, int y, int d){
		if(x<0){
			this.coordX=0;
		}else if(x>Piraterie.MAX_X){
			this.coordX=Piraterie.MAX_X;
		}else{
			this.coordX=x;
		}
		if(y<0){
			this.coordY=0;
		}else if(y>Piraterie.MAX_Y){
			this.coordY=Piraterie.MAX_Y;
		}else{
			this.coordY=y;
		}	
		this.drapeau=d;
		this.detruit=false;	
	}
	
	public int getX(){
		return coordX;
	}
	
	public int getY(){
		return coordY;
	}
	
	public int getDrapeau(){
		return drapeau;
	}
	
	public boolean estDetruit(){
		return detruit;
	}
	
	public double distance(Navire n){
		return Math.sqrt((int)Math.pow((coordX-n.getX()),2)+(int)Math.pow((coordY-n.getY()),2));
	}
	
	public void avance(int unitsX, int unitsY){
		if(coordX + unitsX > Piraterie.MAX_X){
			coordX=Piraterie.MAX_X;
		}else if(coordX + unitsX < 0){
			coordX=0;
		}else{
			coordX = coordX + unitsX;			
		}
		if(coordY + unitsY > Piraterie.MAX_Y){
			coordY=Piraterie.MAX_Y;
		}else if(coordY + unitsY < 0){
			coordY=0;
		}else{
			coordY = coordY + unitsY;		
		}
	}
	
	public void coule(){
		detruit =true;
	}	
	
	public String getNom(){
		return "Bateau";
	}
	

	public abstract boolean estPacifique();
	public abstract void recoitBoulet();
	public abstract void combat(Navire n);
	
	public void rencontre(Navire n){
		if(this.distance(n)<Piraterie.RAYON_RENCONTRE && this.getDrapeau()!=n.getDrapeau()){
			this.combat(n);
		}
	}
	
}

class Pirate extends Navire{
	private boolean damage;
	
	public Pirate(int x, int y, int d, boolean dam){
		super(x,y,d);
		this.damage=dam;
	}
	
	public String getNom(){
		return "Bateau pirate";
	}
	
	public String toString(){
		String etat;
		if(this.estDetruit()){
			etat="détruit";
		}else if(damage){
			etat="ayant subi des dommages";
		}else{
			etat="intact";
		}
		return this.getNom()+" avec drapeau "+this.getDrapeau()+" en ("+this.getX()+","+this.getY()+") -> "+etat;
	}
	
	public boolean estEndommage(){
		return damage;
	}
	
	public boolean estPacifique(){
		return false;
	}
	
	public void recoitBoulet(){
		if(estEndommage()){
			this.coule();
		}else
		{
			damage=true;
		}		
	}
	
	public void combat(Navire n){
		n.recoitBoulet();
		if(!n.estPacifique()){
			this.recoitBoulet();			
		}
	}
}

class Marchand extends Navire{
	
	public Marchand(int x, int y, int d){
		super(x,y,d);
	}
	
	public String getNom(){
		return "Bateau marchand";
	}
	
	public String toString(){
		String etat;
		if(this.estDetruit()){
			etat="détruit";
		}else{
			etat="intact";
		}
		return this.getNom()+" avec drapeau "+this.getDrapeau()+" en ("+this.getX()+","+this.getY()+") -> "+etat;
	}
	
	public boolean estPacifique(){
		return true;
	}
	
	public void recoitBoulet(){
		this.coule();
	}
		
	public void combat(Navire n){
		if(!n.estPacifique()){
			this.recoitBoulet();			
		}
	}
}
/*******************************************
 * Ne pas modifier apres cette ligne
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/
class Piraterie {

    static public final int MAX_X = 500;
    static public final int MAX_Y = 500;
    static public final double RAYON_RENCONTRE = 10;

    static public void main(String[] args) {
        // Test de la partie 1
        System.out.println("***Test de la partie 1***");
        System.out.println();
        // un bateau pirate 0,0 avec le drapeau 1 et avec dommages
        Navire ship1 = new Pirate(0, 0, 1, true);
        // un bateau marchand en 25,0 avec le drapeau 2
        Navire ship2 = new Marchand(25, 0, 2);
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println("Distance: " + ship1.distance(ship2));
        System.out.println("Quelques déplacements horizontaux et verticaux");
        // se deplace de 75 unites a droite et 100 en haut
        ship1.avance(75, 100);
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println("Un déplacement en bas:");
        ship1.avance(0, -5);
        System.out.println(ship1);
        ship1.coule();
        ship2.coule();
        System.out.println("Après destruction:");
        System.out.println(ship1);
        System.out.println(ship2);

        // Test de la partie 2
        System.out.println();
        System.out.println("***Test de la partie 2***");
        System.out.println();

        // deux vaisseaux sont enemis s'ils ont des drapeaux differents

        System.out.println("Bateau pirate et marchand ennemis (trop loin):");
        // bateau pirate intact
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Marchand(0, 25, 2);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Après la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Bateau pirate et marchand ennemis (proches):");
        // bateau pirate intact
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Marchand(2, 0, 2);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Après la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Bateau pirate et marchand amis (proches):");
        // bateau pirate intact
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Marchand(2, 0, 1);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Après la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Deux bateaux pirates ennemis intacts (proches):");
        // bateaux pirates intacts
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Pirate(2, 0, 2, false);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Après la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Un bateau pirate intact et un avec dommages, ennemis:");
        // bateau pirate intact
        Navire ship3 = new Pirate(0, 2, 3, false);
        System.out.println(ship1);
        System.out.println(ship3);
        ship3.rencontre(ship1);
        System.out.println("Après la rencontre:");
        System.out.println(ship1);
        System.out.println(ship3);
        System.out.println();

        System.out.println("Deux bateaux pirates ennemis avec dommages:");
        System.out.println(ship2);
        System.out.println(ship3);
        ship3.rencontre(ship2);
        System.out.println("Après la rencontre:");
        System.out.println(ship2);
        System.out.println(ship3);
        System.out.println();
    }
}
