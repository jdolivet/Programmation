/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johann
 */
public class RLE {
    	public static void main(String[] args) {


	}
        
    
    public static int longueurRLE(int[] t){
        if (t.length==0){
            return 0;
        }
        else{
        int longueur = 2;
        for(int i=0;i<t.length-1;i++){
            if (t[i]!=t[i+1]){
                longueur=longueur+2;
            }                
            }
        return longueur;
        }      
    }
    
    public static int[] RLE(int[] t){
        if (t.length==0){
            return t;
        }
        else{
        int[] t2=new int[t.length+1];
        for(int i=0;i<t.length;i++){
            t2[i]=t[i];            
        }
        t2[t.length]=2;
        int longueur=longueurRLE(t);
        int[] tab=new int[longueur];
        int k=0;

        tab[0]=t[0];
        int repet=1;
        for(int i=0;i<t2.length-1;i++){
            if (t2[i]==t2[i+1]){
                repet=repet+1;
            }else{
                tab[k]=t2[i];
                tab[k+1]=repet;
                k=k+2;  
                repet=1;
            }       
   
        }  
        return tab;
        }
    }
    
    public static int longueurRLEInverse(int[] t){
        int s=0;
        for(int i=1;i<t.length;i+=2){
            s=s+t[i];
        }
        return s;        
    }
    
    public static int[] RLEInverse(int[] t){
        int longueur=longueurRLEInverse(t);
        int[] tableau=new int[longueur];
        int k=0;
        for(int i=0;i<t.length;i+=2){
            for(int j=0;j<t[i+1];j++){
                tableau[k]=t[i];
                k=k+1;                
            }
        }
        return tableau;
    }
    
}