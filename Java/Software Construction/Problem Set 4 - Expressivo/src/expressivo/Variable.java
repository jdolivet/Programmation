package expressivo;

import java.util.Map;
import java.lang.NumberFormatException;

/**
 * An immutable data type representing a Variable
 *      It represents the variable of a monome
 */

public class Variable implements Expression {
    private final String name;
    
    // Abstraction function
    //    represents the variable x
    // Rep invariant
    //    name must be a case-sensitive nonempty string of letters
    private void checkRep() {
        assert name.length() > 0;
        assert !name.contains(" ");
    }
    // Safety from rep exposure
    //    all fields are immutable and final
    
    /** Make a Variable. */
    public Variable(String name) {
        this.name = name;
        checkRep();
    }
    
    
    /** Differentiate a Variable. */
    @Override
    public Expression differentiate(Variable x){
        if (this.equals(x)){
            return new Number(1);
        }else{
            return new Number(0);
        }
    }    
    
    /** Return the value of a Variable : an Exception! */
    @Override 
    public double getValue() throws NumberFormatException{
        throw new NumberFormatException();
    }   
    
    /** Simplify a Variable. */
    @Override
    public Expression simplify(Map<Variable, Number> env){
        Variable var = new Variable(this.name);
        if (env.containsKey(var)){
            Number result = env.get(var);
            return result;
        }else{
            return var;
        }
    }
    
    /** Print a Variable. */
    @Override 
    public String toString() {
        return name;
    }
    
    /** Equality of Variables. */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) { 
            return false; 
        }
        Variable that = (Variable) thatObject;
        return (this.name.equals(that.name));
    }
    
    /** Hashcode of a Variable. */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}