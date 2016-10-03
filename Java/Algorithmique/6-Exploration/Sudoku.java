class Sudoku {
  int[][] grille;
  
  Sudoku(int[][] t) {
      this.grille=t;
  }
  
  public boolean verifieLigne(int i) {
    for(int k=0;k<9;k++){
        for(int j=k+1;j<9;j++){
            if((grille[i][k]!=0)&&(grille[i][j]!=0)&&(grille[i][k]==grille[i][j])){
                return false;
            }
        }
    }
    return true;
  }

  public boolean verifieColonne(int i) {
    for(int k=0;k<9;k++){
        for(int j=k+1;j<9;j++){
            if((grille[k][i]!=0)&&(grille[j][i]!=0)&&(grille[k][i]==grille[j][i])){
                return false;
            }
        }
    }
    return true;
  }

  public boolean verifieCarre(int i,int j) {
      int largeur=(i/3)*3;
      int hauteur=(j/3)*3;
      for(int k=largeur;k<largeur+3;k++){
        for(int l=hauteur;l<hauteur+3;l++){
            for(int kk=largeur;kk<largeur+3;kk++){
                for(int ll=hauteur;ll<hauteur+3;ll++){
                    if((grille[k][l]!=0)&&(grille[kk][ll]!=0)&&(k!=kk)&&(l!=ll)&&(grille[k][l]==grille[kk][ll])){
                        return false;
                    }
                }
            }
        }
    }
    
    return true;
  }
  
  public boolean verifiePossible(int i,int j, int val) {
    if(grille[i][j]!=0){
        return false;
    }
    int[][] brouillon=new int[9][9];
    for(int k=0;k<9;k++){
        for(int l=0;l<9;l++){
            if((k==i)&&(l==j)){
                brouillon[k][l]=val;
            }else{
                brouillon[k][l]=grille[k][l];
            }
        }
    }
    Sudoku tentative=new Sudoku(brouillon);

    if(tentative.verifieLigne(i)==false){
        return false;
    }
    if(tentative.verifieColonne(j)==false){
        return false;
    }
    if(tentative.verifieCarre(i,j)==false){
        return false;
    }   
    return true;
  }
  
  public boolean resousGrille() {
    int[][] sauvegarde=new int[9][9];
    for(int k=0;k<9;k++){
        for(int l=0;l<9;l++){
            sauvegarde[k][l]=grille[k][l];           
        }
    }
    resousGrille(0,0);
    if(resousGrille(0,0)==true){
        return true;
    }else{
        grille=sauvegarde;
        return false;
    }
  }
  public boolean resousGrille(int i,int j){
      if(i==9){
          return true;
      }
      if(grille[i][j]!=0){
          return resousGrille(i+j/8,(j+1)%9);
      }
      boolean solution=false;
      for(int k=1;k<=9;k++){
          if(verifiePossible(i,j,k)){
              grille[i][j]=k;
              if(resousGrille(i+j/8,(j+1)%9)){
                  solution=true;
              }else{
                  grille[i][j]=0;
              }
          }
      }
      return solution;
  }

  public int solutionUnique() {
    int[][] test_1=new int[9][9];
    for(int k=0;k<9;k++){
        for(int l=0;l<9;l++){
            test_1[k][l]=grille[k][l];           
        }
    }
    Sudoku Test_1=new Sudoku(test_1);
    int[][] test_2=new int[9][9];
    for(int k=0;k<9;k++){
        for(int l=0;l<9;l++){
            test_2[k][l]=grille[k][l];           
        }
    } 
    Sudoku Test_2=new Sudoku(test_2);
    if(resousGrille()==false){
        return 0;
    }else{
        Test_1.resousGrille(0,0);
        Test_2.resousGrille_inv(0,0);

            //Test_1.afficheGrille();
            //System.out.println("");
            //Test_2.afficheGrille(); 
            //System.out.println("");            
    for(int k=0;k<9;k++){
        for(int l=0;l<9;l++){
            if(test_1[k][l]!=test_2[k][l]){
                return 2;
            }
        }
    }

            return 1;
        
    }
  }
  
    public boolean resousGrille_inv(int i,int j){
      if(i==9){
          return true;
      }
      if(grille[i][j]!=0){
          return resousGrille_inv(i+j/8,(j+1)%9);
      }
      boolean solution=false;
      for(int k=9;k>=1;k--){
          if(verifiePossible(i,j,k)){
              grille[i][j]=k;
              if(resousGrille_inv(i+j/8,(j+1)%9)){
                  solution=true;
              }else{
                  grille[i][j]=0;
              }
          }
      }
      return solution;
  }
    

  
  public void afficheGrille() {
    if (this.grille == null)
      return;

    for(int i = 0 ;i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        System.out.print(this.grille[i][j]+" ");
      }
      System.out.println();
    }
  }
}
