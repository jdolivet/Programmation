import java.util.ArrayList;

class Cloture {
    public static void main(String[] args) {
        int[][] carte = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,0,0},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        };

        /*******************************************
         * Completez le programme à partir d'ici.
         *******************************************/
        int longueur=carte.length;
        int largeur=carte[0].length;
        for(int i=0;i<longueur;++i)
        {
            for(int j=0;j<largeur;++j)
            {
                int pixel=carte[i][j];
                if(pixel!=0 && pixel!=1)
                {
                    System.out.print("Votre carte du terrain n'a pas le bon format :\nvaleur '");
                    System.out.print(pixel+"' trouvée en position [");
                    System.out.print(i+"]["+j);
                    System.out.println("]");  
                    return;
                }
            }
        }
        
        ArrayList<Integer> abscisse = new ArrayList<Integer>();
        ArrayList<Integer> ordonnee = new ArrayList<Integer>();
        int composante = 1;
        
        for(int i=0;i<longueur;++i)
        {
            for(int j=0;j<largeur;++j)
            {
                int pixel=carte[i][j];
                if(pixel==0)
                {
                    composante = composante + 1;
                    ordonnee.add(i);
                    abscisse.add(j);
                    while(!ordonnee.isEmpty())
                    {
                        int ordbord=ordonnee.get(0);
                        int absbord=abscisse.get(0);  
                        ordonnee.remove(0);
                        abscisse.remove(0);
                        if(carte[ordbord][absbord]==0)
                        {
                            carte[ordbord][absbord]=composante;
                            if(ordbord>0 && carte[ordbord-1][absbord]==0)
                            {
                                ordonnee.add(ordbord-1);
                                abscisse.add(absbord);        
                            }
                            if(ordbord<longueur-1 && carte[ordbord+1][absbord]==0)
                            {
                                ordonnee.add(ordbord+1);
                                abscisse.add(absbord);        
                            }
                            if(absbord>0 && carte[ordbord][absbord-1]==0)
                            {
                                ordonnee.add(ordbord);
                                abscisse.add(absbord-1);        
                            }
                            if(absbord<largeur-1 && carte[ordbord][absbord+1]==0)
                            {
                                ordonnee.add(ordbord);
                                abscisse.add(absbord+1);        
                            }
                        }                       
                    }
                }
            }
        }
        
        ArrayList<Integer> bord = new ArrayList<Integer>();
        for(int i=0;i<largeur;i++)
        {
            if(carte[0][i]!=1)
            {
                bord.add(carte[0][i]);
            }
        }
        for(int i=0;i<largeur;i++)
        {
            if(carte[longueur-1][i]!=1)
            {
                bord.add(carte[longueur-1][i]);
            }
        }
        for(int i=0;i<longueur;i++)
        {
            if(carte[i][0]!=1)
            {
                bord.add(carte[i][0]);
            }
        }
        for(int i=0;i<longueur;i++)
        {
            if(carte[i][largeur-1]!=1)
            {
                bord.add(carte[i][largeur-1]);
            }
        }
        
        for(int i=0;i<longueur;++i)
        {
            int l=0;
            while(carte[i][l]!=1)
            {
                ++l;
            }
            int premierun=l;
            int k=largeur-1;
            while(carte[i][k]!=1)
            {
                --k;
            }
            int dernierun=k;
            for(int j=l;j<k;++j)
            {
                if(carte[i][j]!=1 && bord.contains(carte[i][j]))
                {        
                    System.out.println("Votre carte du terrain n'a pas le bon format :");
                    System.out.print("bord extérieur entrant trouvé en position [");
                    System.out.print(i+"]["+j);
                    System.out.println("]");         
                    return;
                }
            }
        }
        
     /*   for(int i=0;i<longueur;++i)
        {
            for(int j=0;j<largeur;++j)
            {
                int pixel=carte[i][j];
                System.out.print(pixel+" ");
            }
            System.out.println();
        }
        System.out.println();*/
        
        for(int i=0;i<longueur;++i)
        {
            for(int j=0;j<largeur;++j)
            {
                int pixel=carte[i][j];
                boolean droite=false;
                boolean gauche=false;
                if(pixel!=1)
                {
                    for(int g=0;g<j;++g)
                    {
                        if(carte[i][g]==1)
                        {
                            gauche=true;
                        }
                    }
                    for(int d=j;d<largeur;++d)
                    {
                        if(carte[i][d]==1)
                        {
                            droite=true;
                        }
                    }
                }
                if(droite==true && gauche==true)
                {
                    carte[i][j]=1;
                }
            }
        }
        
     /*   for(int i=0;i<longueur;++i)
        {
            for(int j=0;j<largeur;++j)
            {
                int pixel=carte[i][j];
                System.out.print(pixel+" ");
            }
            System.out.println();
        }*/
        
        double perimetre=0.0;
        for(int i=0;i<longueur;++i)
        {
            for(int j=0;j<largeur;++j)
            {
                int pixel=carte[i][j];
                if(pixel==1)
                {
                    if(i==0 ||carte[i-1][j]!=1)
                    {
                        perimetre = perimetre+2.5;
                    }
                    if(i==longueur-1 ||carte[i+1][j]!=1)
                    {
                        perimetre = perimetre+2.5;
                    }
                    if(j==0 ||carte[i][j-1]!=1)
                    {
                        perimetre = perimetre+2.5;
                    }
                    if(j==largeur-1 ||carte[i][j+1]!=1)
                    {
                        perimetre = perimetre+2.5;
                    }
                }

            }
        }

        System.out.print("Il vous faut "+perimetre);
        System.out.println(" mètres de clôture pour votre terrain.");

        /*******************************************
         * Ne rien modifier après cette ligne.
         *******************************************/
    }
}
