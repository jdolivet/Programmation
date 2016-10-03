// Ces squelettes sont à compléter et sont là uniquement pour prévenir des
// erreurs de compilation.
class Element {
    public int s;
    public Occurrence e;
  Element (Occurrence e, int s) {
      this.e=e;
      this.s=s;
  
  }
}

class Occurrence {
   public int retour, taille;   
 
   Occurrence (int retour, int taille) {
       this.retour = retour;
       this.taille = taille;
   }
}

class LZ77 {  
    public static Occurrence plusLongueOccurrence(    int[] t,
      int positionCourante,
      int tailleFenetre) { 
            int min;
            if(positionCourante-tailleFenetre<0){
                min=0;
            }else{
                min=positionCourante-tailleFenetre;
            }
            int taille=0;
            int retour=0;
        for(int i=min;i<positionCourante;i++){            
                if(t[i]==t[positionCourante]){
                    int retour1=positionCourante-i;
                    int taille1=1;
                    while((i+taille1<positionCourante)&&(positionCourante+taille1<t.length)&&(t[i+taille1]==t[positionCourante+taille1])){
                        taille1=taille1+1;
                    }
                    if(taille1>taille){
                        taille=taille1;
                        retour=retour1;
                    }
                }

            
        }
    Occurrence oc = new Occurrence(retour,taille); 
    return oc;
        }
    
        public static int LZ77Longueur(int[] t, int tailleFenetre){
            int i=0;
            int longueur=0;
            while(i<t.length){
                Occurrence o=plusLongueOccurrence(t,i,tailleFenetre);
                longueur=longueur+1;
                i=i+o.taille+1;                
            }
            return longueur;
    }
        
        public static Element[] LZ77(int[] tab, int tailleFenetre){
            int longueur=LZ77Longueur(tab,tailleFenetre);
            Element[] t=new Element[longueur];
            int k=0;
            int i=0;
            while(k<tab.length){
                Occurrence o=plusLongueOccurrence(tab,k,tailleFenetre);
                t[i]=new Element(o,tab[k+o.taille]);
                k=k+o.taille+1;                
                i=i+1;
            }
            return t;
            
        }
        
        public static void afficheEncode(Element[] tab){
            for (Element e:tab) {
                System.out.printf("(%d,%d)%d", e.e.retour, e.e.taille, e.s);
            }
            System.out.printf("\n");         
        }
        
        public static int LZ77InverseLongueur(Element[] t){
            int longueur=0;
            for(int i=0;i<t.length;i++){
                longueur=longueur+t[i].e.taille+1;
            }
            return longueur;
        }
        
        public static int[] LZ77Inverse(Element[] t){
            int longueur=LZ77InverseLongueur(t);
            int[] tab=new int[longueur];
            int pos=0;
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].e.taille;j++){
                    tab[pos]=tab[pos-t[i].e.retour];
                    pos=pos+1;
                }
                tab[pos]=t[i].s;
                pos=pos+1;
            }
            return tab;
            
        }
        
        void blit(int[] t1, int[] t2, int start1, int len, int start2){
            
        }
        
        public static void afficheDecode(int[] t){
            for (int i:t) {
                System.out.printf("%d ", i);
            }
            System.out.printf("\n");
        }

    }


    