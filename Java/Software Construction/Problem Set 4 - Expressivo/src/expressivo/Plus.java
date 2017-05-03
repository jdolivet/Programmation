package expressivo;


import java.util.Map;

/**
 * An immutable data type representing a Sum
 *      It represents the sum of the monomes
 */

class Plus implements Expression {
    private final Expression left, right;
    
    // Abstraction function
    //    represents the sum of two expressions left+right
    // Rep invariant
    //    true
    // Safety from rep exposure
    //    all fields are immutable and final
    
    /** Make a Plus which is the sum of left and right. */
    public Plus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    
    /** Differentiate a Plus which is the sum of of the derivates. */
    @Override
    public Expression differentiate(Variable x){
        Expression newLeft = this.left.differentiate(x);
        Expression newRight = this.right.differentiate(x);
        Expression result = new Plus(newLeft, newRight);
        return result;
    }
    
    /** Return the value of a Plus. */
    @Override 
    public double getValue() throws NumberFormatException{
        try{
            double result = this.left.getValue() + this.right.getValue();
            return result;
        }catch(NumberFormatException e){
            throw new NumberFormatException();
        }
    }
    
    /** Simplify a Plus. More : 0+expression = expression+0 = expression */
    @Override
    public Expression simplify(Map<Variable, Number> env){
        Plus add = new Plus(this.left.simplify(env), this.right.simplify(env));
        try{
            try{
                if(this.left.getValue() == 0){
                    return this.right.simplify(env);
                }
            }catch(NumberFormatException e){}
            try{
                if(this.right.getValue() == 0){
                    return this.left.simplify(env);
                }
            }catch(NumberFormatException e){}
            double value = add.getValue();
            Number result = new Number(value);
            return result;
        }catch(NumberFormatException e){
            return add;
        }
    }
    
    /** Print a Plus. */
    @Override
    public String toString() {
        return "(" + left + ")+(" + right + ")";
    }
    
    /** Equality of Plus. */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Plus)) { 
            return false; 
        }
        Plus that = (Plus) thatObject;
        return (this.left.equals(that.left) && this.right.equals(that.right));
    }
    
    /** Hashcode of a Plus. */
    @Override
    public int hashCode() {     // to keep the importance of position
        return left.hashCode() - right.hashCode();
    }
}