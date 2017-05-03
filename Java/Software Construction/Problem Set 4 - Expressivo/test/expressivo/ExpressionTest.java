/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;


/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   
    //      toString() :
    //          number : int, float, accuracy 
    //          variable : letter, long
    //          polynome : monome, poly 1 var, poly 2 var
    //      equals() and hashCode() :
    //          number : int, float, accuracy
    //          variable : letter
    //          polynome : structural equality
    //      parse()
    //          number : int, float, space
    //          variable : simple, long, space, case sensitive
    //          plus : number, variable, mixte
    //          times : number, variable, mixte
    //          polynome : simple, priority
    //          invalid syntax
    //      differentiate()
    //          number : good var, wrong var
    //          var : short, long
    //          product : mixte
    //          sum : mixte
    //      simplify()
    //          vars : 0 var, 1 var, >1 var
    //          calculus with number : sum, product, sum of product, product of sum
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // Tests for toString()
    @Test
    public void testToStringNumberInt() {
        Expression tested = new Number(2);
        assertEquals("2.0", tested.toString());
    }
    
    @Test
    public void testToStringNumberFloat() {
        Expression tested = new Number(2.3);
        assertEquals("2.3", tested.toString());
        Expression tested2 = new Number(.3);
        assertEquals("0.3", tested2.toString());
        Expression tested3 = new Number(.00000003);
        assertEquals("0.00000", tested3.toString());
    }
    
    @Test
    public void testToStringNumberFloatAccuracy() {
        double before = 1.23456789;
        double epsilon = 0.0001;
        Expression tested = new Number(before);
        String stTested = tested.toString();
        double nbTested = Double.parseDouble(stTested);
        assertTrue(Math.abs(nbTested - before) <= epsilon);
    }
    
    @Test
    public void testToStringVariableSingle() {
        Expression tested = new Variable("y");
        assertEquals("y", tested.toString());
    }
    
    @Test
    public void testToStringVariableLong() {
        Expression tested = new Variable("Foo");
        assertEquals("Foo", tested.toString());
    }    
    
    @Test
    public void testToStringMonome() {
        Expression coef = new Number(2.3);
        Expression var = new Variable("pi");
        Expression monome = new Times(coef, var);
        assertEquals("(2.3)*(pi)", monome.toString());
    }
    
    @Test
    public void testToStringPolynomeOneVarSum() {
        Expression coef1 = new Number(2.3);
        Expression var = new Variable("pi");
        Expression monome = new Times(coef1, var);
        Expression coef2 = new Number(4.5);
        Expression polynome = new Plus(monome, coef2);
        assertEquals("((2.3)*(pi))+(4.5)", polynome.toString());
    }
    
    @Test
    public void testToStringPolynomeOneVarProduct() {
        Expression coef1 = new Number(2.3);
        Expression var = new Variable("pi");
        Expression monome = new Plus(coef1, var);
        Expression coef2 = new Number(4.5);
        Expression polynome = new Times(monome, coef2);
        assertEquals("((2.3)+(pi))*(4.5)", polynome.toString());
    }
    
    @Test
    public void testToStringPolynomeTwoVar() {
        Expression coef1 = new Number(2.3);
        Expression var1 = new Variable("pi");
        Expression monome1 = new Times(coef1, var1);
        Expression coef2 = new Number(4.5);
        Expression var2 = new Variable("x");
        Expression monome2 = new Times(coef2, var2);
        Expression polynome = new Plus(monome1, monome2);
        assertEquals("((2.3)*(pi))+((4.5)*(x))", polynome.toString());
    }
    
    
    // Tests for equals() and hashCode()
    @Test
    public void testEqualityNumberInt() {
        Expression tested1 = new Number(2);
        Expression tested2 = new Number(2);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
   /* @Test
    public void testNotEqualityNumberIntSpace() {
        Expression tested1 = new Number(2 3);
        Expression tested2 = new Number(23);
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }*/
    
    @Test
    public void testEqualityNumberIntFloat() {
        Expression tested1 = new Number(2);
        Expression tested2 = new Number(2.0);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }    
    
    @Test
    public void testNotEqualityNumber() {
        Expression tested1 = new Number(2);
        Expression tested2 = new Number(2.1);
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testEqualityFloat() {
        Expression tested1 = new Number(1.23456789);
        Expression tested2 = new Number(1.23456789);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testEqualityNumberFloatZerosAfter() {
        Expression tested1 = new Number(2.00);
        Expression tested2 = new Number(2.0);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testEqualityNumberFloatZerosBefore() {
        Expression tested1 = new Number(02.0);
        Expression tested2 = new Number(2.0);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testEqualityVariable() {
        Expression tested1 = new Variable("x");
        Expression tested2 = new Variable("x");
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testNotEqualityVariable() {
        Expression tested1 = new Variable("x");
        Expression tested2 = new Variable("y");
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }    
    
/*    @Test
    public void testNotEqualityVariableSpace() {
        Expression tested1 = new Variable("x y");
        Expression tested2 = new Variable("xy");
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }
    */
    @Test
    public void testEqualityMonome() {
        Expression coef1 = new Number(2.3);
        Expression var1 = new Variable("pi");
        Expression tested1 = new Times(coef1, var1);
        Expression coef2 = new Number(2.3);
        Expression var2 = new Variable("pi");
        Expression tested2 = new Times(coef2, var2);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }    
    
    @Test
    public void testEqualityPolynome() {
        Expression coef1 = new Number(2.3);
        Expression var1 = new Variable("pi");
        Expression monome1 = new Times(coef1, var1);
        Expression coef2 = new Number(4.5);
        Expression tested1 = new Plus(monome1, coef2);
        Expression coef3 = new Number(2.3);
        Expression var2 = new Variable("pi");
        Expression monome2 = new Times(coef3, var2);
        Expression coef4 = new Number(4.5);
        Expression tested2 = new Plus(monome2, coef4);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testStructuralNotEqualityUnaryComposition() {   // 1*x not equal to x
        Expression coef1 = new Number(1);
        Expression var1 = new Variable("x");
        Expression tested1 = new Times(coef1, var1);
        Expression tested2 = new Variable("x");
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testStructuralNotEqualityPlusConstBinaryOrder() {    // 1+2 not equal to 2+1
        Expression coef1 = new Number(1);
        Expression coef2 = new Number(2);
        Expression tested1 = new Plus(coef1, coef2);
        Expression tested2 = new Plus(coef1, coef1);
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }    
    
    @Test
    public void testStructuralNotEqualityPlusBinaryOrder() {    // 1+x not equal to x+1
        Expression coef1 = new Number(1);
        Expression var1 = new Variable("x");
        Expression tested1 = new Plus(coef1, var1);
        Expression tested2 = new Plus(var1, coef1);
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }    
    
    @Test
    public void testStructuralNotEqualityTimesBinaryOrder() {    // 1*x not equal to x*1
        Expression coef1 = new Number(1);
        Expression var1 = new Variable("x");
        Expression tested1 = new Times(coef1, var1);
        Expression tested2 = new Times(var1, coef1);
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }
    
    /*@Test
    public void testStructuralEqualityNaryPlusComposition() {   // (1+2)+3 equal to 1+(2+3)
        Expression coef11 = new Number(1);
        Expression coef12 = new Number(2);
        Expression sum11 = new Plus(coef11,coef12);
        Expression coef13 = new Number(3);
        Expression tested1 = new Plus(sum11,coef13);
        Expression coef21 = new Number(1);
        Expression coef22 = new Number(2);
        Expression coef23 = new Number(3);
        Expression sum21 = new Plus(coef22,coef23);
        Expression tested2 = new Plus(coef21,sum21);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }
    
    @Test
    public void testStructuralEqualityNaryTimesComposition() {   // (1*2)*3 equal to 1*(2*3)
        Expression coef11 = new Number(1);
        Expression coef12 = new Number(2);
        Expression prod11 = new Times(coef11,coef12);
        Expression coef13 = new Number(3);
        Expression tested1 = new Times(prod11,coef13);
        Expression coef21 = new Number(1);
        Expression coef22 = new Number(2);
        Expression coef23 = new Number(3);
        Expression prod21 = new Times(coef22,coef23);
        Expression tested2 = new Times(coef21,prod21);
        System.out.println(tested1);
        System.out.println(tested2);
        assertTrue(tested1.equals(tested2));
        assertTrue(tested2.equals(tested1));
        assertTrue(tested1.hashCode() == tested2.hashCode());
    }    
    */
    @Test
    public void testStructuralNotEqualityNaryPriorityComposition() {   // (1*2)+3 not equal to 1*(2+3)
        Expression coef11 = new Number(1);
        Expression coef12 = new Number(2);
        Expression prod11 = new Times(coef11,coef12);
        Expression coef13 = new Number(3);
        Expression tested1 = new Plus(prod11,coef13);
        Expression coef21 = new Number(1);
        Expression coef22 = new Number(2);
        Expression coef23 = new Number(3);
        Expression sum21 = new Plus(coef22,coef23);
        Expression tested2 = new Times(coef21,sum21);
        assertFalse(tested1.equals(tested2));
        assertFalse(tested2.equals(tested1));
        assertFalse(tested1.hashCode() == tested2.hashCode());
    }
    
    
    // Tests for parse()
    @Test
    public void testParseNum() {
        Expression tested = new Number(1);
        assertEquals(tested, Expression.parse("1"));
        assertEquals(tested, Expression.parse("1 "));
        assertEquals(tested, Expression.parse(" 1"));
    }
    
    @Test
    public void testParseNumFloat() {
        Expression tested = new Number(1.23456789);
        assertEquals(tested, Expression.parse("1.23456789"));
        Expression tested2 = new Number(.2);
        assertEquals(tested2, Expression.parse("0.2"));
    }
    
    @Test
    public void testParseVar() {
        Expression tested = new Variable("x");
        assertEquals(tested, Expression.parse("x"));
        assertEquals(tested, Expression.parse("x "));
        assertEquals(tested, Expression.parse(" x"));
    }
    
    @Test
    public void testParseVarLong() {
        Expression tested = new Variable("Foo");
        assertEquals(tested, Expression.parse("Foo"));
        assertFalse(tested.equals(Expression.parse("foo")));
        assertEquals(tested, Expression.parse(" Foo"));
        assertEquals(tested, Expression.parse("Foo "));
    }
    
    @Test
    public void testParseMonome() {
        Expression tested = new Times(new Number(2), new Variable("x"));
        assertEquals(tested, Expression.parse("2*x"));
        assertEquals(tested, Expression.parse("(2*x)"));
        assertEquals(tested, Expression.parse("(2)*(x)"));
        assertEquals(tested, Expression.parse("((2)*(x))"));
    }
    
    @Test
    public void testParseAddNum() {
        Expression tested = new Plus(new Number(1), new Number(2));
        assertEquals(tested, Expression.parse("1+2"));
        assertFalse(tested.equals(Expression.parse("2+1")));
        assertEquals(tested, Expression.parse("1 + 2 "));
        assertEquals(tested, Expression.parse("(1+2)"));
        assertEquals(tested, Expression.parse("(1)+(2)"));
        assertEquals(tested, Expression.parse("((1)+(2))"));
    }
    
    @Test
    public void testParseAddVar() {
        Expression tested = new Plus(new Variable("x"), new Variable("y"));
        assertEquals(tested, Expression.parse("x+y"));
        assertFalse(tested.equals(Expression.parse("y+x")));
        assertEquals(tested, Expression.parse("x + y "));
        assertEquals(tested, Expression.parse("(x+y)"));
        assertEquals(tested, Expression.parse("(x)+(y)"));
        assertEquals(tested, Expression.parse("((x)+(y))"));
    }
    
    @Test
    public void testParseAdd() {
        Expression tested = new Plus(new Number(2), new Variable("y"));
        assertEquals(tested, Expression.parse("2+y"));
        assertFalse(tested.equals(Expression.parse("y+2")));
        assertEquals(tested, Expression.parse("2 + y "));
        assertEquals(tested, Expression.parse("(2+y)"));
        assertEquals(tested, Expression.parse("(2)+(y)"));
        assertEquals(tested, Expression.parse("((2)+(y))"));
    }
    
    @Test
    public void testParsePolynome() {
        Expression tested = new Plus(new Number(2), new Times (new Variable("y"), new Number(3.2)));
        assertEquals(tested, Expression.parse("2+y*3.2"));
        assertEquals(tested, Expression.parse(" 2 +y  * 3.2 "));
        assertEquals(tested, Expression.parse(" 2 + ( y  * 3.2) "));
        assertFalse(tested.equals(Expression.parse("2+3.2*y")));
        assertFalse(tested.equals(Expression.parse("(2+y)*3.2")));
    }
    
    @Test
    public void testParsePriority1() {
        Expression tested = Expression.parse("2+y*3.2");
        assertEquals(tested, Expression.parse("2+(y*3.2)"));
        assertEquals(tested, Expression.parse(" 2 +y  * 3.2 "));
        assertEquals(tested, Expression.parse(" 2 + ( y  * 3.2) "));
        assertFalse(tested.equals(Expression.parse("2+3.2*y")));
        assertFalse(tested.equals(Expression.parse("(2+y)*3.2")));
    }
    
    @Test
    public void testParsePriority2() {
        Expression tested = Expression.parse("y*3.2+2");
        assertEquals(tested, Expression.parse("(y*3.2)+2"));
        assertEquals(tested, Expression.parse(" y  * 3.2 + 2 "));
        assertEquals(tested, Expression.parse(" ( y  * 3.2) + 2 "));
        assertFalse(tested.equals(Expression.parse("3.2*y +2")));
        assertFalse(tested.equals(Expression.parse("y*(3.2+2)")));
    }
    
    @Test
    public void testParseInvalid() {
        try{
            Expression.parse("3 *");
        }catch(Exception e){
            assert true;
        }
        try{
            Expression.parse("( 3");
        }catch(Exception e){
            assert true;
        }
        try{
            Expression.parse("3 x");
        }catch(Exception e){
            assert true;
        }
        try{
            Expression.parse("2 3");
        }catch(Exception e){
            assert true;
        }
        try{
            Expression.parse("y x");
        }catch(Exception e){
            assert true;
        }
    }
    
    
    // Tests for differentiate()
    @Test
    public void testDifferenciateNum() {  // dc/dx=0
        Expression started = new Number(1);
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("0"));
        assertEquals(tested, Expression.parse("0.00"));
    }
    
    @Test
    public void testDifferenciateVar() {  // dx/dx=1
        Expression started = new Variable("x");
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("1"));
    }
    
    @Test
    public void testDifferenciateVarCaseSensitive() {  // dx/dX=0
        Expression started = new Variable("x");
        Variable var = new Variable("X");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("0"));
    }
    
    @Test
    public void testDifferenciateVarLong() {  // dFoo/dFoo=1
        Expression started = new Variable("Foo");
        Variable var = new Variable("Foo");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("1"));
    }
    
    @Test
    public void testDifferenciateOtherVar() { // dx/dy=0
        Expression started = new Variable("x");
        Variable var = new Variable("y");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("0"));
    }
    
    @Test
    public void testDifferenciateMonome() { // d(2*x)/dx=2*1+0*x
        Expression started = new Times(new Number(2), new Variable("x"));
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("2*1+0*x"));
    }
    
    @Test
    public void testDifferenciateProduct() { // d(x*y)/dx=x*0+1*y
        Expression started = new Times(new Variable("x"), new Variable("y"));
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("x*0+1*y"));
    }
    
    @Test
    public void testDifferenciateSquare() { // d(x*x)/dx=x*1+1*x
        Expression started = new Times(new Variable("x"), new Variable("x"));
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("x*1+1*x"));
    }
    
    @Test
    public void testDifferenciateSum() { // d(1+x)/dx=0+1
        Expression started = new Plus(new Number(1), new Variable("x"));
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("0+1"));
    }
    
    @Test
    public void testDifferenciateSumVar() { // d(y+x)/dx=0+1
        Expression started = new Plus(new Variable("y"), new Variable("x"));
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("0+1"));
    }
    
    @Test
    public void testDifferenciatePower() { // d(x*x*x)/dx=x*x*1 + (x*1 + 1*x)*x
        Expression started = new Times(new Times(new Variable("x"), new Variable("x")), new Variable("x"));
        Variable var = new Variable("x");
        Expression tested = started.differentiate(var);
        assertEquals(tested, Expression.parse("x*x*1 + (x*1 + 1*x)*x"));
    }
    
    @Test
    public void testDifferenciateImmutable() { 
        Variable der = new Variable("x");
        Expression leftBefore = new Variable("x");
        leftBefore.differentiate(der);
        assertEquals(leftBefore, new Variable("x"));
        Expression rightBefore = new Number(3);
        rightBefore.differentiate(der);
        assertEquals(rightBefore, new Number(3));
        Expression PlusBefore = new Plus(new Variable("x"), new Number(3));
        PlusBefore.differentiate(der);
        assertEquals(PlusBefore, new Plus(new Variable("x"), new Number(3)));
        Expression TimesBefore = new Plus(new Variable("x"), new Number(3));
        TimesBefore.differentiate(der);
        assertEquals(TimesBefore, new Plus(new Variable("x"), new Number(3)));
    }    
    
    // Tests for simplify()
    @Test
    public void testSymplifyVar() { 
        Variable var1 = new Variable("x");
        Number nb1 = new Number(2);
        Variable var2 = new Variable("y");
        Number nb2 = new Number(3.14);
        HashMap<Variable, Number> env = new HashMap<Variable, Number>();
        
        Variable test1 = new Variable("z");
        Expression result1 = test1.simplify(env);
        assertEquals(result1, Expression.parse("z"));
        
        env.put(var1, nb1);
        Variable test2 = new Variable("z");
        Expression result2 = test2.simplify(env);
        assertEquals(result2, Expression.parse("z"));
        
        env.put(var2, nb2);        
        Variable test3 = new Variable("z");
        Expression result3 = test3.simplify(env);
        assertEquals(result3, Expression.parse("z"));
        Variable test4 = new Variable("y");
        Expression result4 = test4.simplify(env);
        assertEquals(result4, Expression.parse("3.14"));
        
    }
    
    @Test
    public void testSymplifyNum() { 
        Variable var1 = new Variable("x");
        Number nb1 = new Number(2);
        Variable var2 = new Variable("y");
        Number nb2 = new Number(3.14);
        HashMap<Variable, Number> env = new HashMap<Variable, Number>();
        
        Number test1 = new Number(5);
        Expression result1 = test1.simplify(env);
        assertEquals(result1, Expression.parse("5"));
        
        env.put(var1, nb1);
        Number test2 = new Number(5);
        Expression result2 = test2.simplify(env);
        assertEquals(result2, Expression.parse("5"));
        
        env.put(var2, nb2);        
        Number test3 = new Number(5);
        Expression result3 = test3.simplify(env);
        assertEquals(result3, Expression.parse("5"));
    }
    
    @Test
    public void testSymplifyTimes() { 
        Variable var1 = new Variable("x");
        Number nb1 = new Number(2);
        Variable var2 = new Variable("y");
        Number nb2 = new Number(3.14);
        HashMap<Variable, Number> env = new HashMap<Variable, Number>();
        env.put(var1, nb1);
        env.put(var2, nb2);
        
        Expression test1 = new Times(new Variable("x"), new Variable("y"));
        Expression result1 = test1.simplify(env);
        assertEquals(result1, Expression.parse("6.28"));
        Expression test2 = new Times(new Variable("z"), new Variable("y"));
        Expression result2 = test2.simplify(env);
        assertEquals(result2, Expression.parse("z*3.14"));
    }
    
    @Test
    public void testSymplifyPlus() { 
        Variable var1 = new Variable("x");
        Number nb1 = new Number(2);
        Variable var2 = new Variable("y");
        Number nb2 = new Number(5);
        HashMap<Variable, Number> env = new HashMap<Variable, Number>();
        env.put(var1, nb1);
        env.put(var2, nb2);
        
        Expression test1 = new Plus(new Variable("x"), new Variable("y"));
        Expression result1 = test1.simplify(env);
        assertEquals(result1, Expression.parse("7"));
        Expression test2 = new Plus(new Variable("z"), new Variable("y"));
        Expression result2 = test2.simplify(env);
        assertEquals(result2, Expression.parse("z+5"));
    }
    
    @Test
    public void testSymplifyImmutable() { 
        Variable var1 = new Variable("x");
        Number nb1 = new Number(2);
        Variable var2 = new Variable("y");
        Number nb2 = new Number(5);
        HashMap<Variable, Number> env = new HashMap<Variable, Number>();
        env.put(var1, nb1);
        env.put(var2, nb2);
        
        Expression leftBefore = new Variable("x");
        leftBefore.simplify(env);
        assertEquals(leftBefore, new Variable("x"));
        Expression rightBefore = new Number(3);
        rightBefore.simplify(env);
        assertEquals(rightBefore, new Number(3));
        Expression PlusBefore = new Plus(new Variable("x"), new Number(3));
        PlusBefore.simplify(env);
        assertEquals(PlusBefore, new Plus(new Variable("x"), new Number(3)));
        Expression TimesBefore = new Plus(new Variable("x"), new Number(3));
        TimesBefore.simplify(env);
        assertEquals(TimesBefore, new Plus(new Variable("x"), new Number(3)));
    }  
    
    
    
}
