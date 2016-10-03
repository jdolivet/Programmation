import java.util.ArrayList;
import java.util.Collections;


class Cellule {
    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/
    private String nom;
    private double taille;
    private int energie;
    private String couleur;
 
	public Cellule(String nom,double taille, int energie, String couleur)
	{
		this.nom=nom;
		this.taille=taille;
		this.energie=energie;
		this.couleur=couleur;
	}
    
	public Cellule()
	{
		this.nom="Pyrobacculum";
		this.taille=10;
		this.energie=5;
		this.couleur="verte";
	}
	public Cellule(Cellule c)
	{
		this.nom=c.nom;
		this.taille=c.taille;
		this.energie=c.energie;
		this.couleur=c.couleur;		
	}
	public void affiche()
	{
		System.out.println(nom+", taille = "+taille+" microns, énergie = "+energie+", couleur = "+couleur);
	}
	public int getEnergie()
	{
		return this.energie;
	}
	public Cellule division()
	{
		String newColor;
		if (this.couleur=="verte")
			{
			newColor="bleue";
			}
		else if (this.couleur=="bleue")
			{
			newColor="rouge";
			}
		else if (this.couleur=="rouge")
			{
			newColor="rose bonbon";
			}
		else if (this.couleur=="violet")
			{
			newColor="verte";
			}
		else
		{
		newColor=this.couleur+" fluo";
		}
		int newEnergie=this.energie;
		this.energie=this.energie-1;
		return new Cellule(this.nom,this.taille, newEnergie, newColor);
				
	}
}
class Petri{
	private ArrayList<Cellule> listCellules;
	
	public Petri()
	{
		listCellules=new ArrayList<Cellule>();
	}
	
	public void ajouter(Cellule c)
	{
		listCellules.add(c);
	}
	public void affiche()
	{
		for(Cellule c: listCellules)
		{
			c.affiche();
		}
	}
	public void evolue()
	{
		int nbCellules = listCellules.size();
		for(int i=0;i<nbCellules;i++)
		{
				ajouter(listCellules.get(i).division());				
		}

		for(int i=0;i<listCellules.size();i++)
		{
			if(listCellules.get(i).getEnergie()<=0)
			{
				int last = listCellules.size()-1;
				Collections.swap(listCellules, i, last);
				listCellules.remove(last);			
			}
		}

	}
	
}
/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/
class SimulationPetri {
    public static void main(String[] args) {
        Petri assiette = new Petri();

        assiette.ajouter(new Cellule());
        assiette.ajouter(new Cellule("PiliIV", 4, 1, "jaune"));
        System.out.println("Population avant évolution :");
        assiette.affiche();

        assiette.evolue();
        System.out.println("Population après évolution :");
        assiette.affiche();
    }
}

