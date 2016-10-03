/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johann
 */
import java.util.LinkedList;


public class Exploration {
    final char[][] grille;
    final int dim;
    final Dictionnaire d;
    
    Exploration (char[][] grille, int dim, Dictionnaire d){
        this.grille=grille;
        this.dim=dim;
        this.d=d;
    }
    
    boolean[][] masque;
    LinkedList<Character> prefix;
    LinkedList<String> motsTrouves;
    
    public LinkedList<String> exploreTout(){
        masque=new boolean [dim][dim];
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                masque[i][j]=false;
            }
        }
        motsTrouves=new LinkedList<String>();
        prefix=new LinkedList<Character>();
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                Position p =new Position(this,j,i);
                Noeud n=d.racine;
                explore(p,n);
            }
        }
        /*for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                System.out.println(masque[i][j]);
            }
        }        */
        return motsTrouves;
    }
    
    public void explore1(Position p, Noeud n){
        char lettre_suivante=grille[p.x][p.y];
        boolean[][] masque_temp=new boolean [dim][dim];
        masque_temp=new boolean [dim][dim];
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                masque_temp[i][j]=masque[i][j];
            }
        }
        LinkedList<Character> prefix_temp=new LinkedList<Character>(prefix);
        Noeud nouveau=n.trouveFils(lettre_suivante);
        if(nouveau!=null){
            prefix.add(lettre_suivante);
            if(nouveau.estMot()){
                motsTrouves.add(versChaine(prefix));
            }
        }
        masque[p.x][p.y]=true;
        prefix=prefix_temp;
        masque=masque_temp;
    }
    
    public void explore(Position p, Noeud n){
        char lettre_suivante=grille[p.x][p.y];
        boolean[][] masque_temp=new boolean [dim][dim];
        masque_temp=new boolean [dim][dim];
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                masque_temp[i][j]=masque[i][j];
            }
        }
        LinkedList<Character> prefix_temp=new LinkedList<Character>(prefix);
        Noeud nouveau=n.trouveFils(lettre_suivante);
        masque[p.x][p.y]=true;
        if(nouveau!=null){
            prefix.add(lettre_suivante);
            if(nouveau.estMot()){
                int k=0;
                for(String x:motsTrouves){
                    if(x.length()<prefix.size()){
                        k=k+1;
                    }
                    if(x.length()==prefix.size()&&x.compareTo(versChaine(prefix))<0){
                        k=k+1;                        
                    }
                }
                motsTrouves.add(k,versChaine(prefix));
            }
            for(Position futur:p.deplacementsLegaux()){
                explore(futur,nouveau);
            }
        }
        prefix=prefix_temp;
        masque=masque_temp;
    }    
    
    public static String versChaine(LinkedList<Character> l) {
        StringBuilder sb = new StringBuilder();
        for (Character c: l){
            sb.append(c);
        }            
        return sb.toString();
    }
 
    
}
