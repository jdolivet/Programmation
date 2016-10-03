import java.util.Scanner;

class Romain {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        int[] nombres = {1000, 500, 100, 50, 10, 5, 1 };
        String symboles = "MDCLXVI";

        /*******************************************
         * Complétez le programme à partir d'ici.
         *******************************************/
        
        System.out.print("Entrez un nombre en chiffres romains : ");
        String nbromain = clavier.nextLine();
        nbromain = nbromain.toUpperCase();
        boolean struct=false;
        
        int nb1 = 0;
        boolean structure=true;
        for(int i=0; i < nbromain.length(); ++i)
        {
            char s= nbromain.charAt(i);
            if(symboles.indexOf(s)==-1)
            {
                structure = false;
            }              
        }
        if(structure==false)
        {
            System.out.println("Conversion impossible, nombre romain mal formé.");
        }
        else
        {
            for(int i=0;i<nbromain.length();++i)
            {
                char s= nbromain.charAt(i);
                int pos=symboles.indexOf(s);
                boolean moins=false;
                for(int j=i;j<nbromain.length();++j)
                {
                    char s2=nbromain.charAt(j);
                    int pos2 = symboles.indexOf(s2);
                    if(pos2<pos)
                    {
                        moins=true;
                    }
                            
                }
                if(moins==true)
                {
                    nb1=nb1-nombres[pos]; 
                }
                else
                {
                    nb1=nb1+nombres[pos];                       
                }
            }
            System.out.println("arabes("+nbromain+") = "+nb1);
        }
        
        final int MAX = 3999;
        int nbarabe;
        do
        {
            System.out.print("Entrez un nombre (en chiffres arabes) compris entre 1 et " + MAX + " : ");
            nbarabe=clavier.nextInt();
        }
        while(nbarabe<1 ||nbarabe>MAX);
        
        String nb2="";
        int nbarabe2=nbarabe;
        int [] numeration=new int [symboles.length()];
        for(int i=0;i<symboles.length();++i)
        {
            numeration[i]=nbarabe/nombres[i];
            nbarabe=nbarabe-numeration[i]*nombres[i];  
        }
        for(int i=0;i<symboles.length();++i)
        {
            if(i<symboles.length()-1 && numeration[i]==1 && numeration[i+1]==4)
            {
                nb2=nb2+symboles.charAt(i+1)+symboles.charAt(i-1);
                numeration[i+1]=0;
            }else
            {
                if(i<symboles.length()-1 && numeration[i]==0 && numeration[i+1]==4)
                {
                    nb2=nb2+symboles.charAt(i+1)+symboles.charAt(i);
                    numeration[i+1]=0;
                }else
                {
                    for(int j=1;j<=numeration[i];++j)
                    {
                        nb2=nb2+symboles.charAt(i);
                    }
                }
            }
        }
        System.out.println("romains("+nbarabe2+") = "+nb2);
        
        /*******************************************
         * Ne rien modifier après cette ligne.
         *******************************************/
    }
}
