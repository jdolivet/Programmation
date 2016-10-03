// Utility.distance(x,y) retourne la distance entre x et y
class Utility {
    static int distance(int x, int y) {
        return Math.abs(x - y);
    }
}

class Creature {
    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/
    private final String nom;
    private int niveau;
    private int pointsDeVie;
    private int force;
    private int position;
    
    public Creature(String nom, int niveau, int pointsDeVie, int force){
    	this.nom =nom;
    	this.niveau=niveau;
    	this.pointsDeVie=pointsDeVie;
    	this.force=force;
    	this.position=0;
    }
    public Creature(String nom, int niveau, int pointsDeVie, int force, int pos){
    	this.nom =nom;
    	this.niveau=niveau;
    	this.pointsDeVie=pointsDeVie;
    	this.force=force;
    	this.position=pos;
    }
    
    public int position(){
    	return position;
    }
    
    public int niveau(){
    	return niveau;
    }
    
    public void setNiveau(){
    	niveau = niveau + 1;
    }
	
    public boolean vivant(){
    	return (pointsDeVie>0);
    }
    
    public int pointsAttaque(){
    	if (this.vivant()){
    		return niveau * force;
    	}else
    	{return 0;}
    }
    
    public void deplacer(int p){
    	if(vivant()){
        	position = position+p; 		
    	}
    }
    
    public void adieux(){
    	System.out.print(nom);
    	System.out.println(" n'est plus !");  	
    }
    
    public void faiblir(int p){
    	if (this.vivant()){
    		pointsDeVie = pointsDeVie - p;
    	}
    	if (!this.vivant())
    	{
    		pointsDeVie = 0;
    		this.adieux();
    	}
    }
    
    public String toString(){
    	return this.nom+", niveau: "+this.niveau+", points de vie: "+this.pointsDeVie+", force: "+this.force+", points d'attaque: "+this.pointsAttaque()+", position: "+this.position;
    }    
}

class Dragon extends Creature{
	private int porteeFlamme;
	
	public Dragon(String nom, int niveau, int pointsDeVie, int force,int portee){
		super(nom, niveau, pointsDeVie, force);
		this.porteeFlamme=portee;
	}
	public Dragon(String nom, int niveau, int pointsDeVie,  int force,int portee,int pos){
		super(nom, niveau, pointsDeVie, force, pos);
		this.porteeFlamme=portee;
	}
	
	public void voler(int p){
		if(vivant()){
			this.deplacer(p - this.position());			
		}
	}
	
	public void souffleSur(Creature c){
		boolean attaque = false;
		if (this.vivant() && c.vivant() && (Utility.distance(c.position() , this.position()) <= this.porteeFlamme)){
			c.faiblir(this.pointsAttaque());
			this.faiblir(Utility.distance(c.position() , this.position()));
			attaque = true;
		}
		if (this.vivant() && !c.vivant() && attaque==true){
			this.setNiveau(); 
		}
	}
}

class Hydre extends Creature{
	private int longueurCou;
	private int dosePoison;
	
	public Hydre(String nom, int niveau, int pointsDeVie, int force,int longueur, int dose){
		super(nom, niveau, pointsDeVie, force);
		this.longueurCou = longueur;
		this.dosePoison = dose;
	}
	public Hydre(String nom, int niveau, int pointsDeVie, int force, int longueur, int dose,int pos){
		super(nom, niveau, pointsDeVie, force,pos);
		this.longueurCou = longueur;
		this.dosePoison = dose;
	}
	
	public void empoisonne(Creature c){
		boolean attaque = false;
		if (this.vivant() && c.vivant() && (Utility.distance(c.position() , this.position()) <= this.longueurCou)){
			c.faiblir(this.pointsAttaque() + this.dosePoison);
			attaque=true;
		}
		if (!c.vivant()&& attaque==true){
			this.setNiveau();
		}
	}
}

/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/
// ======================================================================
class Dragons {
    private static void combat(Dragon dragon, Hydre hydre) {
        hydre.empoisonne(dragon); // l'hydre a l'initiative (elle est plus rapide)
        dragon.souffleSur(hydre);
    }


    public static void main(String[] args) {
        Dragon dragon = new Dragon("Dragon rouge"   , 2, 10, 3, 20         );
        Hydre  hydre =  new Hydre ("Hydre maléfique", 2, 10, 1, 10, 1,  42 );

        System.out.println(dragon);
        System.out.println("se prépare au combat avec :");
        System.out.println(hydre);

        System.out.println();

        System.out.println("1er combat :");
        System.out.println("\t Les créatures ne sont pas à portée, donc ne peuvent pas s'attaquer.");
        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);

        System.out.println();

        System.out.println("Le dragon vole à proximité de l'hydre :");
        dragon.voler(hydre.position() - 1);
        System.out.println(dragon);

        System.out.println();

        System.out.println("L'hydre recule d'un pas :");
        hydre.deplacer(1);
        System.out.println(hydre);

        System.out.println();
        System.out.println("2e combat :");
        System.out.format("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n",
                          "+ l'hydre inflige au dragon une attaque de 3 points",
                          " [ niveau (2) * force (1) + poison (1) = 3 ] ;",
                          "+  le dragon inflige à l'hydre une attaque de 6 points",
                          " [ niveau (2) * force (3) = 6 ] ;",
                          " + pendant son attaque, le dragon perd 2 points de vie supplémentaires",
                          "[ correspondant à la distance entre le dragon et l'hydre : 43 - 41 = 2 ]." );

        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);

        System.out.println();
        System.out.println("Le dragon avance d'un pas :");
        dragon.deplacer(1);
        System.out.println(dragon);

        System.out.println();
        System.out.println("3e combat :");
        System.out.format("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n",

                          "+ l'hydre inflige au dragon une attaque de 3 points",
                          "[ niveau (2) * force (1) + poison (1) = 3 ] ;",
                          "+ le dragon inflige à l'hydre une attaque de 6 points",
                          "[ niveau (2) * force (3) = 6 ] ;",
                          "+ pendant son attaque, le dragon perd 1 point de vie supplémentaire",
                          "[ correspondant à la distance entre le dragon et l'hydre : 43 - 42 = 1 ] ;",
                          "+ l'hydre est vaincue et le dragon monte au niveau 3.");
        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);

        System.out.println();
        System.out.println("4e Combat :");
        System.out.println("\t quand une créature est vaincue, rien ne se passe.");
        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);
    }
}
