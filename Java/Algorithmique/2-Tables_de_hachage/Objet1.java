/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johann
 */
public class Objet1 extends Objet {
  String nom;
  
  Objet1(String n) {
    nom = n;    
  }
  
  public int hash() {
    int h = 0;
    int n=nom.length();
    int multiple=1;
    for (int i = 0; i < n; ++i) {
        h=h+nom.charAt(n-i-1)*multiple;
        multiple=multiple*31;    
    }
    return h;

  }
  
    public String nom() {
    return nom;
  }
    
}
