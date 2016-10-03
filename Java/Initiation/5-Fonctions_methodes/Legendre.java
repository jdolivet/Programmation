import java.util.Scanner;

class Legendre {

    private final static Scanner clavier = new Scanner(System.in);

    public static void main(String[] args) {
        testPremiers(0, 100);
        System.out.println();
        testLegendre();
    }

    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/
    

    static boolean estPremier(int nombre)
    {
    	if(nombre<=1)
    	{
    		return false;
    	}
    	if(nombre==2)
    	{
    		return true;
    	}
    	if(nombre % 2 == 0)
    	{
    		return false;
    	}
    	for(int i=3;i<=Math.sqrt((double)nombre);++i)
		{
    		if(nombre % i == 0)
    		{
    			return false;
    		}
		}
    	return true;
    }
    
    static void testPremiers(int a,int b)
    {
        System.out.print("Premiers entre ");
        System.out.print(a+" et "+b);
        System.out.println(" :");
    	for(int i=a; i<=b;++i)
    	{
    		if(estPremier(i)==true)
    		{
    			System.out.print(i+", ");
    		}
    	}
        System.out.println("");
    }

    static int legendre(int n)
    {
    	for(int i=n*n+1;i<=(n+1)*(n+1)-1;++i)
    	{
    		if(estPremier(i)==true)
    		{
    			return i;
    		}
    	}
    	return 0;
    }
    
    static void testLegendre()
    {
    	int a;
    	do
    	{
    	    System.out.print("Tester la conjecture de Legendre entre : ");   
    	    a = clavier.nextInt();
    	}while(a<=0);
    	int b;
    	do
    	{
    	    System.out.print("et : ");	
    	    b = clavier.nextInt();
    	}while(b<a);
    	
    	for(int i = a;i<=b;++i)
    	{
    		if(legendre(i)!=0)
    		{
        	    System.out.println(i+" : "+legendre(i));	    			
    		}
    		else
    		{
    			System.out.println("PAS TROUVÉ !");
    		}
    	}
    }



    /*******************************************
     * Ne rien modifier après cette ligne.
     *******************************************/
}
