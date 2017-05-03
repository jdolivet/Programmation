package expressivo;

import java.util.Map;
import java.util.Locale;

/**
 * An immutable data type representing a Number
 *      It represents the coefficient of a monome
 */

class Number implements Expression {
    private final double nb;
    
    // Abstraction function
    //    represents the double a
    // Rep invariant
    //    nb >= 0 (can't be with space but no need to check)
    
    private void checkRep() {
        assert nb >= 0;
    }
    
    // Safety from rep exposure
    //    all fields are immutable and final
    
    /** Make a Number. */
    public Number(double nb) {
        this.nb = nb;
        checkRep();
    }
        
    
    /** Differentiate a Number. */
    @Override
    public Expression differentiate(Variable x){
        return new Number(0);
    }
    
    /** The value of a Number. */
    @Override 
    public double getValue() {
        return nb;
    }
    
    /** Simplify a Number. */
    @Override
    public Expression simplify(Map<Variable, Number> env){
        double value = this.getValue();
        return new Number(value);
    }
    
    /** Print a Number. */
    @Override 
    public String toString() {
        String flottant = String.valueOf(nb);
        if (flottant.contains("E")){
            String expr = String.format(Locale.ROOT,"%.5f", nb);
            return expr;
        }else{
            return flottant;
        }
    }
    
    /** Equality of Numbers. */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) { 
            return false; 
        }
        Number that = (Number) thatObject;
        return (this.nb == that.nb);
    }
    
    /** Hashcode of a Number. */
    @Override
    public int hashCode() { // hashcode are equals if the numbers have difference < 10^(-10)
        return (int) (nb * 1000000000.0);
    }
    
}