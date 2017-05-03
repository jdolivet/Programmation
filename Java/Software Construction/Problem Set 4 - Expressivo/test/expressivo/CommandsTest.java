/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   
    //      differentiate()
    //          number : good var, wrong var
    //          var : short, long
    //          product : mixte
    //          sum : mixte
    //      simplify():
    //          var, num
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // Tests for differentiate()
    @Test
    public void testDifferenciateNum() {  // dc/dx=0
        String expression = "1";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "0.0";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateVar() {  // dx/dx=1
        String expression = "x";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "1.0";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateVarCaseSensitive() {  // dx/dX=0
        String expression = "x";
        String var = "X";
        String result = Commands.differentiate(expression, var);
        String expected = "0";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateVarLong() {  // dFoo/dFoo=1
        String expression = "Foo";
        String var = "Foo";
        String result = Commands.differentiate(expression, var);
        String expected = "1.0";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateOtherVar() { // dx/dy=0
        String expression = "x";
        String var = "y";
        String result = Commands.differentiate(expression, var);
        String expected = "0.0";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateMonome() { // d(2*x)/dx=2*1+0*x
        String expression = "2*x";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "((2.0)*(1.0))+((0.0)*(x))";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateProduct() { // d(x*y)/dx=x*0+1*y
        String expression = "x*y";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "((x)*(0.0))+((1.0)*(y))";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateSquare() { // d(x*x)/dx=x*1+1*x
        String expression = "x*x";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "((x)*(1.0))+((1.0)*(x))";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateSum() { // d(1+x)/dx=0+1
        String expression = "1+x";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "(0.0)+(1.0)";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    @Test
    public void testDifferenciateSumVar() { // d(y+x)/dx=0+1
        String expression = "y+x";
        String var = "x";
        String result = Commands.differentiate(expression, var);
        String expected = "(0.0)+(1.0)";
        assertEquals(result, Expression.parse(expected).toString());
    }
    
    // Tests for simplify()
    @Test
    public void testSymplifyVar() { 
        String var1 = "x";
        double nb1 = 2;
        String var2 = "y";
        double nb2 = 3.14;
        HashMap<String, Double> env = new HashMap<String, Double>();
        
        String test1 = "z";
        String result1 = Commands.simplify(test1, env);
        assertEquals(result1, "z");
        
        env.put(var1, nb1);
        String test2 = "z";
        String result2 = Commands.simplify(test2, env);
        assertEquals(result2, "z");
        
        env.put(var2, nb2);        
        String test3 = "z";
        String result3 = Commands.simplify(test3, env);
        assertEquals(result3, "z");
        String test4 = "y";
        String result4 = Commands.simplify(test4, env);;
        assertEquals(result4, Expression.parse("3.14").toString());
        
    }
    
    @Test
    public void testSymplifyNum() { 
        String var1 = "x";
        double nb1 = 2;
        String var2 = "y";
        double nb2 = 3.14;
        HashMap<String, Double> env = new HashMap<String, Double>();
        
        String test1 = "5";
        String result1 = Commands.simplify(test1, env);
        assertEquals(result1, Expression.parse("5.0").toString());
        
        env.put(var1, nb1);
        String test2 = "5";
        String result2 = Commands.simplify(test2, env);
        assertEquals(result2, Expression.parse("5.0").toString());
        
        env.put(var2, nb2);        
        String test3 = "5";
        String result3 = Commands.simplify(test3, env);
        assertEquals(result3, Expression.parse("5.0").toString());
    }
    
    
}
