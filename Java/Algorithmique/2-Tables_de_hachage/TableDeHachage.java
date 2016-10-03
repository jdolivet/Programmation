/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johann
 */
public class TableDeHachage {
        Liste[] Table;
    //constructeur
    TableDeHachage(int n){
        Table=new Liste[n];
        for(int i=0;i<n;i++){
            Table[i]=new Liste();
        }
    }
    
    //ajoute element table
    public void ajoute(Objet o){
        int position=o.hash()%Table.length;
        if (position<0){
            position=position+Table.length;
        }
        Table[position].ajouteTete(o);        
    }
    
    //test si table contient element
    public boolean contient(Objet o){
        boolean c=false;
        int n=Table.length;
        for (int i=0;i<n;i++){
            if (Table[i].contient(o)==true){
                c=true;
            }
        }
        return c;
    }
    
    //Cherche la plus grande liste
    public int[] remplissageMax(){
        int[] res=new int[2];
        int n=Table.length;
        int taille=Table[0].longueur();
        int index=0;
        for(int i=1;i<n;i++){
            if(Table[i].longueur()>taille){
                taille=Table[i].longueur();
                index=i;
            }
        }
        res[0]=index;
        res[1]=taille;
        return res;
    }
            
    
}
