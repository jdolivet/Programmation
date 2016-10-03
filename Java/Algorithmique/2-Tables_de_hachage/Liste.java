/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johann
 */
import java.util.NoSuchElementException;

public class Liste {
    //Tete de liste
    private Cellule tete;
    
    //Constructeur
    Liste(){
        tete=null;
    }
    
    //ajouteTete
    public Liste ajouteTete(Objet val){
        tete=new Cellule(val,tete);
        return this;
    }
    
    //SupprimeTete
    public Liste supprimeTete(){
        if (tete==null){
            throw new NoSuchElementException();
        }
        else{
        tete=tete.suivante;
        return this;
        }
    }
    
    //recherche element
    public boolean contient(Objet o){
        boolean c=false;
        Cellule parcourt=tete;
        while (parcourt!=null){
            if(o.nom().equals(parcourt.contenu.nom())){
                c=true;
            }
            parcourt=parcourt.suivante;
        }
        return c;
    }
        
     //longueur liste
    public int longueur(){
        int i=0;
        Cellule parcourt=tete;
        while(parcourt!=null) {
            parcourt=parcourt.suivante;
            i=i+1;
        }
        return i;
    }
    
    
}
class Cellule{
    //Contenu
    Objet contenu;
    //Cellule suivante
    Cellule suivante;
    
    //Constructeur
    Cellule(Objet c, Cellule n){
        contenu=c;
        suivante=n;
    }
}



