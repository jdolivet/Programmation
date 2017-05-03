package expressivo;

import java.util.Map;
import java.lang.NumberFormatException;

/**
 * An immutable data type representing a Product
 *      It represents the monomes
 */

class Times implements Expression {
    private final Expression left, right;
     
     // Abstraction function
     //    represents the product of two expressions left+right
     // Rep invariant
     //    true
     // Safety from rep exposure
     //    all fields are immutable and final
     
     /** Make a Times which is the product of left and right. */
     public Times(Expression left, Expression right) {
        this.left = left;
        this.right = right;
     }
     
     
     /** Differentiate a Times which is a sum of products. */
     @Override
     public Expression differentiate(Variable x){
         Expression left1 = this.left;
         Expression right1 = this.right.differentiate(x);
         Expression firstTimes = new Times(left1, right1);
         Expression left2 = this.left.differentiate(x);
         Expression right2 = this.right;
         Expression secondTimes = new Times(left2, right2);
         Expression result = new Plus(firstTimes, secondTimes);
         return result;
     }
     
     /** Return the value of a Times. More : 0*x = x*0 = 0. */
     @Override 
     public double getValue() throws NumberFormatException{
         try{
             try{
                 if(this.left.getValue() == 0){
                     return 0;
                 }
             }catch(NumberFormatException e){}
             try{
                 if(this.right.getValue() == 0){
                     return 0;
                 }
             }catch(NumberFormatException e){}
             double result = this.left.getValue() * this.right.getValue();
             return result;
         }catch(NumberFormatException e){
             throw new NumberFormatException();
         }
     }
     
     /** Simplify a Times. More 1*expression = expression*1 = expression  */
     @Override
     public Expression simplify(Map<Variable, Number> env){
         Times prod = new Times(this.left.simplify(env), this.right.simplify(env));
         try{
             try{
                 if(this.left.getValue() == 1){
                     return this.right.simplify(env);
                 }
             }catch(NumberFormatException e){}
             try{
                 if(this.right.getValue() == 1){
                     return this.left.simplify(env);
                 }
             }catch(NumberFormatException e){}
             double value = prod.getValue();
             Number result = new Number(value);
             return result;
         }catch(NumberFormatException e){
             return prod;
         }
     }
     
     /** Print a Times. */
     @Override 
     public String toString() {
         return "(" + left + ")*(" + right + ")";
     }
     
     /** Equality of Times. */
     @Override
     public boolean equals(Object thatObject) {
         if (!(thatObject instanceof Times)) { 
             return false; 
         }
         Times that = (Times) thatObject;
         return (this.left.equals(that.left) && this.right.equals(that.right));
     }
     
     /** Hashcode of a Times. */
     @Override
     public int hashCode() {     // to keep the importance of position
         return left.hashCode() - right.hashCode();
     }
 }
