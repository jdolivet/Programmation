/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johann
 */
public class Objet2 extends Objet {
  String nom;
  
  Objet2(String n) {
    nom = n;    
  }
  
  
  public int hash() {
    int h = 5381;
    int n=nom.length();
    for (int i = 0; i < n; ++i) {
        h=h*33^nom.charAt(i);   
    }
    return h;
  } 
  
    public String nom() {
    return nom;
  }
    
}
