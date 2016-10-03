import java.util.ArrayList;

/*****************************************************
 * Compléter le code à partir d'ici
 *****************************************************/
class Produit{
	private String nom;
	private String unite;
	
	public Produit(String n, String u){
		this.nom=n;
		this.unite=u;
	}
	
	public Produit(String n){
		this.nom=n;
		this.unite="";
	}
	
	public String getNom(){
		return nom;
	}
	
	public String getUnite(){
		return unite;
	}
	
	public String toString(){
		return nom;
	}
	
	public double quantiteTotale(String nomProduit){
		if (nomProduit.equals(this.nom)){
			return 1.0;
		}else{
			return 0.0;
		}
	}
	
	public Produit adapter(double n){
		return this;
	}
	
	
}

class Ingredient{
	private Produit produit;
	private double quantite;
	
	public Ingredient(Produit p, double q){
		this.produit = p;
		this.quantite = q;
	}
	
	public Produit getProduit(){
		return produit;
	}
	
	public double getQuantite(){
		return quantite;
	}
	
	public String toString(){
		return quantite+" "+produit.getUnite()+" de "+produit.adapter(quantite).toString();
	}
	public double quantiteTotale(String nomProduit){
		return quantite * produit.quantiteTotale(nomProduit);
	}	
	
}

class Recette{
	private ArrayList<Ingredient> listIngredients;
	private String nom;
	private double repet;
	
	public Recette(String n, double r){
		this.nom = n;
		this.repet=r;
		listIngredients=new ArrayList<Ingredient>();
	}
	
	public Recette(String n){
		this.nom = n;
		this.repet=1.0;
		listIngredients=new ArrayList<Ingredient>();
	}
	
	public ArrayList<Ingredient> getList(){
		return  listIngredients;
	}
	public double getRepet(){
		return repet;
	}
	
	public void ajouter(Produit p, double quantite){		
		Ingredient i= new Ingredient(p,quantite*repet);	
		listIngredients.add(i);
	}
	
	public Recette adapter(double n){
		Recette res = new Recette(this.nom, n*repet);
		for(int i=0; i < listIngredients.size();i++){
			res.ajouter(this.listIngredients.get(i).getProduit(),this.listIngredients.get(i).getQuantite()/repet);
		}
		return res;
	}
		
	public String toString(){
		String res = "  Recette \""+this.nom+"\" x "+this.repet+":";
		for(int i=0; i < listIngredients.size();i++){
			res += "\n  "+(i+1)+". "+listIngredients.get(i).toString() ;
		}
		return res;
	}
	
	public double quantiteTotale(String nomProduit){
		double res = 0.0;
		for(Ingredient i : listIngredients){
				res = res+i.quantiteTotale(nomProduit);			
		}
		return res;
	}
	
}

class ProduitCuisine extends Produit{
	private Recette r;

	
	public ProduitCuisine(String n){
		super(n, "portion(s)");
		this.r = new Recette(n);
	}
	public ProduitCuisine(String n, double r){
		super(n, "portion(s)");
		this.r = new Recette(n,r);
	}
		
	
	public void ajouterARecette(Produit produit, double quantite){
		r.ajouter(produit,quantite);
	}
	
	
	public ProduitCuisine adapter(double n){
		ProduitCuisine res = new ProduitCuisine(this.getNom(),n*r.getRepet());
		for(int i=0; i < r.getList().size();i++){
			res.ajouterARecette(this.r.getList().get(i).getProduit(),this.r.getList().get(i).getQuantite());
		}
		return res;
	}
	
	public String toString(){
		String res = super.toString()+"\n";
		res = res + r.toString();
		return res;
	}
	
	public double quantiteTotale(String nomProduit){
		if (this.getNom().equals(nomProduit)){
			return 1.0;
		}else{
			return r.quantiteTotale(nomProduit);
		}
	}
	
}

/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/

class Restaurant {
    public static void main(String[] args) {

        // quelques produits de base
        Produit oeufs          = new Produit("oeufs");
        Produit farine         = new Produit("farine", "grammes");
        Produit beurre         = new Produit("beurre", "grammes");
        Produit sucreGlace     = new Produit("sucre glacé", "grammes");
        Produit chocolatNoir   = new Produit("chocolat noir", "grammes");
        Produit amandesMoulues = new Produit("amandes moulues", "grammes");
        Produit extraitAmandes = new Produit("extrait d'amandes", "gouttes");

        ProduitCuisine glacage = new ProduitCuisine("glaçage au chocolat");
        // recette pour une portion de glaçage :
        glacage.ajouterARecette(chocolatNoir, 200);
        glacage.ajouterARecette(beurre,        25);
        glacage.ajouterARecette(sucreGlace,   100);

        System.out.println(glacage);
        System.out.println();

        ProduitCuisine glacageParfume = new ProduitCuisine("glaçage au chocolat parfumé");
        // besoin de 1 portions de glaçage au chocolat et de 2 gouttes
        // d'extrait d'amandes pour 1 portion de glaçage parfumé
        glacageParfume.ajouterARecette(extraitAmandes, 2);
        glacageParfume.ajouterARecette(glacage,        1);

        System.out.println(glacageParfume);
        System.out.println();

        Recette recette = new Recette("tourte glacée au chocolat");
        // recette pour une portion de tourte glacée :
        recette.ajouter(oeufs,           5);
        recette.ajouter(farine,        150);
        recette.ajouter(beurre,        100);
        recette.ajouter(amandesMoulues, 50);
        recette.ajouter(glacageParfume,  2);

        System.out.println("===  Recette finale  ======\n");
        System.out.println(recette);
        System.out.println();

        afficherQuantiteTotale(recette, beurre);
        System.out.println();

        Recette doubleRecette = recette.adapter(2);
        System.out.println("===  Recette finale x 2 ===\n");
        System.out.println(doubleRecette);
        System.out.println();

        afficherQuantiteTotale(doubleRecette, beurre);
        afficherQuantiteTotale(doubleRecette, oeufs);
        afficherQuantiteTotale(doubleRecette, extraitAmandes);
        afficherQuantiteTotale(doubleRecette, glacage);
        System.out.println();

        System.out.println("===========================\n");
        System.out.println("Vérification que le glaçage n'a pas été modifié :\n");
        System.out.println(glacage);
    }

    private static void afficherQuantiteTotale(Recette recette, Produit produit) {
        String nom = produit.getNom();
        System.out.println("Cette recette contient " +
                           recette.quantiteTotale(nom) + " " + produit.getUnite() + " de " + nom);
    }
}