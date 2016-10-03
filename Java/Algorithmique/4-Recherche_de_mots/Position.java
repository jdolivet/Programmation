
import java.util.LinkedList;

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

public class Position {
    final int x, y;
    final Exploration e;
        
    public Position(Exploration e, int x, int y){
        this.x=x;
        this.y=y;
        this.e=e; 
    }
        
    private boolean estLegal(){
        if(0<=x&&x<e.dim&&0<=y&&y<e.dim&&e.masque[x][y]==false){
            return true;
        }else{
            return false;
        }            
    }
        
        
    public LinkedList<Position> deplacementsLegaux(){
        LinkedList<Position> futur=new LinkedList<Position>();
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                Position p =new Position(e,x+i,y+j);
                if(p.estLegal()){
                    futur.add(p);
                }
            }
        }
        return futur;
    }
    
}
