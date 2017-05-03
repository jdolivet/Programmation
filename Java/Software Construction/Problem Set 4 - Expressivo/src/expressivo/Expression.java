package expressivo;

import java.io.File;
import java.util.Map;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;


/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */

public interface Expression {
    
    // Datatype definition
    // Expression = Number(value:double) + Variable(letter:String) 
    //              + Plus(left:Expression, right:Expression) + Times(left:Expression, right:Expression)
    
    enum ExpressivoGrammar{ROOT,
        SUM,
        PRODUCT,
        PRIMITIVE,
        WHITESPACE,
        VARIABLE,
        NUMBER};
    
        
        
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        try{
            Parser<ExpressivoGrammar> parser = GrammarCompiler.compile(new File("src/expressivo/Expression.g"), ExpressivoGrammar.ROOT);
            ParseTree<ExpressivoGrammar> tree = parser.parse(input);
            //System.out.println(tree.toString());    
            //tree.display();
            Expression ast = buildAST(tree);
            return ast;
        }catch(Exception e){
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Function converts a ParseTree to an Expression. 
     * @param p
     *  ParseTree<ExpressivoGrammar> that is assumed to have been constructed by the grammar in Expression.g
     * @return
     */
    static Expression buildAST(ParseTree<ExpressivoGrammar> p){
        
        switch(p.getName()){
        /*
         * Since p is a ParseTree parameterized by the type ExpressivoGrammar, p.getName() 
         * returns an instance of the ExpressivoGrammar enum. This allows the compiler to check
         * that we have covered all the cases.
         */
        case NUMBER:
            /*
             * A number will be a terminal containing a number.
             */
            return new Number(Double.parseDouble(p.getContents()));
        case VARIABLE:
            /*
             * A variable will be a terminal containing a variable.
             */
            return new Variable(String.valueOf(p.getContents()));
        case PRIMITIVE:
            /*
             * A primitive will have either a number, a variable or a sum as child (in addition to some whitespace)
             * By checking which one, we can determine which case we are in.
             */             
            
            if(p.childrenByName(ExpressivoGrammar.NUMBER).isEmpty()){
                if(p.childrenByName(ExpressivoGrammar.VARIABLE).isEmpty()){
                    return buildAST(p.childrenByName(ExpressivoGrammar.SUM).get(0));
                }else{
                    return buildAST(p.childrenByName(ExpressivoGrammar.VARIABLE).get(0));
                }
            }else{
                return buildAST(p.childrenByName(ExpressivoGrammar.NUMBER).get(0));
            }
            
        case PRODUCT:
            /*
             * A product will have one or more children that need to be multiplied together.
             * Note that we only care about the children that are primitive. There may also be 
             * some whitespace children which we want to ignore.
             */
            boolean firstProd = true;
            Expression resultProd = null;
            for(ParseTree<ExpressivoGrammar> child : p.childrenByName(ExpressivoGrammar.PRIMITIVE)){
                if(firstProd){
                    resultProd = buildAST(child);
                    firstProd = false;
                }else{
                    resultProd = new Times(resultProd, buildAST(child));
                }
            }
            if(firstProd){ throw new RuntimeException("product must have a non whitespace child:" + p); }
            return resultProd;
            
        case SUM:
            /*
             * A sum will have one or more children that need to be summed together.
             * Note that we only care about the children that are product. There may also be 
             * some whitespace children which we want to ignore.
             */
            boolean firstSum = true;
            Expression resultSum = null;
            for(ParseTree<ExpressivoGrammar> child : p.childrenByName(ExpressivoGrammar.PRODUCT)){
                if(firstSum){
                    resultSum = buildAST(child);
                    firstSum = false;
                }else{
                    resultSum = new Plus(resultSum, buildAST(child));
                }
            }
            if(firstSum){ throw new RuntimeException("sum must have a non whitespace child:" + p); }
            return resultSum;
        case ROOT:
            /*
             * The root has a single sum child, in addition to having potentially some whitespace.
             */
            return buildAST(p.childrenByName(ExpressivoGrammar.SUM).get(0));
        case WHITESPACE:
            /*
             * Since we are always avoiding calling buildAST with whitespace, 
             * the code should never make it here. 
             */
            throw new RuntimeException("You should never reach here:" + p);
        }   
        /*
         * The compiler should be smart enough to tell that this code is unreachable, but it isn't.
         */
        throw new RuntimeException("You should never reach here:" + p);
    }
    
    
    /**
     * Function that differentiate the expression
     * @param the variable from which the differentiation is done
     * @return the expression after the derivation
     */
    public Expression differentiate(Variable x);

    
    /**
     * Function that return the value of a numerical expression
     * @param the expression must be an expression of constants
     * @return the numerical value
     * @throws NumberFormatException if the expression is invalid
     */
    public double getValue() throws NumberFormatException;
    
    /**
     * Function that simplify the expression
     * @param the map with the Variable and his value (a Number)
     * @return the expression after the simplification
     */
    public Expression simplify(Map<Variable, Number> env);
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();
    
    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
