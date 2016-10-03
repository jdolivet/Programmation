

class Seg {

  // Cette fonction effectue une régression linéaire sur les points allant des
  // indices "d" (début) à "f" (fin). Elle calcule les coefficients "a" et "b"
  // et renvoie également la somme des carrés des erreurs.
  static double[] erreur (double[] xtab, double[] ytab, int d, int f) {
      int l_1=xtab.length;
      int l_2=ytab.length;
      if (l_1!=l_2){
          throw new IllegalArgumentException("les tableaux n'ont pas la même dimension");
      }
      if (l_1==0){
          throw new IllegalArgumentException("le tableau est vide!");
      }    
      if (f<d){
          throw new IllegalArgumentException("les bornes ne sont pas dans le bon ordre");
      }
      if (!(0 <= d && d<=l_1)){
          throw new IllegalArgumentException("d n'est pas dans le tableau");
      }
      if (!(0 <= f && f<=l_1)){
          throw new IllegalArgumentException("f n'est pas dans le tableau");
      }
      double S_xy=0;
      for(int i=d;i<=f;i++){
          S_xy=S_xy+xtab[i]*ytab[i];
      }
      double S_x=0;
      for(int i=d;i<=f;i++){
          S_x=S_x+xtab[i];
      }
      double S_xx=0;
      for(int i=d;i<=f;i++){
          S_xx=S_xx+xtab[i]*xtab[i];
      }
      double S_y=0;
      for(int i=d;i<=f;i++){
          S_y=S_y+ytab[i];
      }
      int n=f-d+1;
      double a=(n*S_xy-S_x*S_y)/(n*S_xx-S_x*S_x);
      double b=(S_y-a*S_x)/n;
      double err=0;
      for(int i=d;i<=f;i++){
          err=err+(ytab[i]-a*xtab[i]-b)*(ytab[i]-a*xtab[i]-b);
      }

      // la ligne suivante est fausse et sert juste a permettre la compilation
      return(new double[] {a, b, err});

  }

  // Cette fonction calcule le nombre de segments pour un coût donné c.
  static int nbSeg (double[] xtab, double[] ytab, double c) {
      int l=xtab.length;
      double[] min_err=new double[l-1];
      int [] min_nb=new int[l-1];
      double erreur, min;
      int nb_seg;
      for(int i=1;i<l;i++){
          min=erreur(xtab,ytab,0,i)[2]+c;
          nb_seg=1;
          for(int j=2;j<i;j++){
              erreur=min_err[j-2]+c+erreur(xtab,ytab,j,i)[2];
              if(erreur<min){
                  min=erreur;
                  nb_seg=1+min_nb[j-2];                  
              }
          }
          min_err[i-1]=min;
          min_nb[i-1]=nb_seg;
      }
      return(min_nb[l-2]);
  }

}


