/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rossin
 */
import java.util.LinkedList;

public class Dictionnaire {
    Noeud racine;
    
    Dictionnaire(){
        this.racine=new Noeud('_');
    }
    
    public String toString() {
        return racine.toString();
    }

    public boolean existeMot(String s) {
        return racine.existeMotRecursif(s,0);      
    }
    
    public boolean ajouteMot(String s) {
        Noeud n=racine;
        boolean deja=true;
        int i=0;
        while(!n.fils.isEmpty()&&i<s.length()){
            for(Noeud x:n.fils){
                if(x.lettre==s.charAt(i)){
                    deja=false;
                    n=x;
                    i=i+1;
                    break;
                }else{
                    deja=true;                    
                }
            }
            if(deja==true){
                break;
            }
        }
        for(int j=i;j<s.length();j++){
            Noeud ajout=new Noeud(s.charAt(j));
            int k=0;
            for(Noeud x:n.fils){
                if(x.lettre>ajout.lettre){ 
                    k=k+1;
                }                 
            }
            n.fils.add(n.fils.size()-k,ajout);            
            n=ajout;
        }
        if(deja==false){
            for(Noeud x:n.fils){
                if(x.lettre=='*'){    
                    break;
                }else{
                    deja=true;
                }
            }
        }
        if(deja==true){
            Noeud fin=new Noeud('*');
            n.fils.add(0,fin);       
        }  
        return deja;
    }
    
    public boolean estPrefixe(String s) {
        Noeud n=racine;
        boolean pref=false;
        int i=0;
        while(!n.fils.isEmpty()&&i<s.length()){
            for(Noeud x:n.fils){
                if(x.lettre==s.charAt(i)){
                    pref=true;
                    n=x;
                    i=i+1;
                    break;
                }else{
                    pref=false;                    
                }
            }
            if(pref==false){
                break;
            }
        }
        return pref;
        
    }
    
    public void listeMotsAlphabetique() {
	racine.listeMotsAlphabetique(new LinkedList<Character>());        
    }
}

class Noeud {
    //definition
    char lettre;
    LinkedList<Noeud> fils;
    
    //constructeur
    Noeud(char c) {
        this.lettre=c;
        this.fils=new LinkedList<Noeud>();
        }
    
    //conversion en chaine (donc parcours)
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append(lettre); 
        if(!fils.isEmpty()){
            sb.append('(');
            for(Noeud x:fils){                
                sb.append(x.toString()+','); 
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(')'); 
        }
        return sb.toString();
    }

    //ajouter un fils
    public void ajouteFils(Noeud a) {
        this.fils.add(a);	
    }
    
    public boolean existeMotRecursif(String s, int pos){
        boolean existe=true;
        if(pos<s.length()){
            for(Noeud x:fils){  
                if(x.lettre==s.charAt(pos)){
                    existe=true;
                    return x.existeMotRecursif(s,pos+1);               
                }else{                      
                    existe=false;
                }
            }
        }
        for(Noeud x:fils){  
            if(x.lettre=='*'){ 
                break;
            }else{             
                existe=false;
            }
        }
        return existe;
    }
    
        public void listeMotsAlphabetique(LinkedList<Character> prefix) {
            if(lettre == '*'){
                StringBuilder sb = new StringBuilder();
                for(Character c : prefix) {
                    sb.append(c);
                }
                System.out.print(sb.deleteCharAt(0).append(' '));
            }
            else{
                LinkedList<Character> temp = new LinkedList<Character>(prefix);
                temp.add(lettre);
                for (Noeud n : fils) {
                    n.listeMotsAlphabetique(temp);
                }
            }
        }
    public Noeud trouveFils(char c){
        for(Noeud x:fils){
            if(x.lettre==c){
                return x;                
            }
        }
        return null;
    }
    
    public boolean estMot(){
        boolean fin=false;
        if(trouveFils('*')!=null){
            fin=true;
        }
        return fin;
    }
    
        
        
        
}