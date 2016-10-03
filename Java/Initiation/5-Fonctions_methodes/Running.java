public class Running {

    public static void main(String [] argv) {
        int[][] circuits1 = {
            {3, 1, 2, 7, 2, 5, 6, 2},
            {2, 10, 20, 12, 13},
            {3, 7, 12, 15, 18, 4},
            {2, 11, 21, 12, 13}
        };
        displayAllCircuits(circuits1);
        selectCircuit(circuits1, 22);
        System.out.println();

        int[][] circuits2 = null;
        displayAllCircuits(circuits2);
        selectCircuit(circuits2, 10);
        System.out.println();

        int[][] circuits3 = {
            {6, 1, 2, 2, 14},
            {3, 1, 2, 7, 9 },
        };
        displayAllCircuits(circuits3);
        selectCircuit(circuits3, 20);
        selectCircuit(circuits3, -2);
        selectCircuit(circuits3, 25);
        System.out.println();
    }
    /*****************************************************
    * Compléter le code à partir d'ici
    *****************************************************/
    static int totalLength(int[] circuit)
    {
    	if(circuit!=null)
    	{
        	int somme = 0;
        	for(int i=0;i<circuit.length;++i)
        	{
        		somme = somme + circuit[i];
        	}
        	return somme;    		
    	}
    	else{return 0;}
    }
    
    static int stepMaxDiff(int[] circuit)
    {
    	if(circuit == null || circuit.length==0)
    	{
    		return -1;
    	}
    	else
    	{
        	int ecart=0;
        	int indice = 0;
        	for(int i=0;i<circuit.length-1;++i)
        	{
        		if(Math.abs(circuit[i]-circuit[i+1])>=ecart)
        		{
        			ecart=Math.abs(circuit[i]-circuit[i+1]);
        			indice = i;
        		}
        	}
    		if(Math.abs(circuit[0]-circuit[circuit.length-1])>=ecart)
    		{
    			indice = circuit.length-1;
    		}
        	return indice;	
    	}
    }
    
    static void displayAllCircuits(int[][] circuits)
    {
    	if(circuits==null || circuits.length == 0)
    	{
    	    System.out.println("Liste de circuits vide !");    		
    	}else
    	{
	    	for(int i=0; i<circuits.length;++i)
	    	{
				System.out.print("Circuit no "+(i+1)+" ("+totalLength(circuits[i])+" km) : ");
				for(int j=0;j<circuits[i].length;++j)    				
				{
					System.out.print(circuits[i][j]+" ");
				}
			    System.out.print("(étape ayant le plus grand écart avec la suivante = ");
			    System.out.println(stepMaxDiff(circuits[i])+1+")");  			    		    		
	    	}
    	}
    }
    
    static void selectCircuit(int[][] circuits, int minTotalDist)
    {
        System.out.print("Sélection d'un circuit de ");
        System.out.print(minTotalDist+" km au minimum : ");
        if(circuits!=null && circuits.length != 0)
        {
        	
        	int indice=0;
        	int ecart=1000;
        	for(int i =0; i<circuits.length;++i)
        	{
        		if(totalLength(circuits[i])>=minTotalDist && Math.abs((circuits[i].length)-stepMaxDiff(circuits[i]))<=ecart)
        		{
        			ecart=Math.abs((circuits[i].length)-stepMaxDiff(circuits[i]));
        			indice = i;
        		}
        	}
        	if(minTotalDist<=0)
        	{
        	    System.out.println("votre circuit doit avoir au moins 1 km !");		
        	}    	
        	else if(totalLength(circuits[indice])>=minTotalDist)
        	{
        		System.out.println(" le circuit choisi est le "+(indice+1));
        	}
        	else
        	{
        	    System.out.println("aucun circuit possible avec ces critères !");   		
        	}
        	
        }
        else
        {
        	System.out.println("aucun circuit possible avec ces critères !"); 
        }

    }
}
