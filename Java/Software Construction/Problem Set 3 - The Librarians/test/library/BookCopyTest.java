package library;

import org.junit.Test;

import library.BookCopy.Condition;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * 
     * 
     * BookCopy : constructor
     *      one example book
     *      condition must be GOOD
     * getBook() : observer
     *      one example book
     * getCondition() : observer
     *      test for GOOD and DAMAGED
     * setCondition() : mutator
     *      test for GOOD and DAMAGED with 1,2,3 changes
     * toString() : observer
     *      contents all book.toString and must contain "good" or "damaged"
     * equals(obj) and hashCode() : oberver
     *      copy = copy 
     *      2 copy of the same book
     *      2 copy of diff book
     */
    
    @Test
    public void testPrivateFields() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        BookCopy copy = new BookCopy(book);
        List <Field> attributs = Arrays.asList(copy.getClass().getFields());
        boolean privateField;
        if (attributs.isEmpty()){
            // Good to be here
            privateField = true;
        }else{
            privateField = false;
        }
        assertTrue(privateField);
    }
    
    @Test
    public void testExampleTest() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed Astair", "Fred"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
        assertTrue(copy.getCondition().equals(Condition.GOOD));
    }
    
    @Test
    public void testCondition() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed Astair", "Fred"), 1990);
        BookCopy copy = new BookCopy(book);
        
        copy.setCondition(Condition.DAMAGED);
        // still the same book but different condition
        assertEquals(book, copy.getBook());
        assertTrue(copy.getCondition().equals(Condition.DAMAGED));
        
        copy.setCondition(Condition.GOOD);
        // Condition change
        assertTrue(copy.getCondition().equals(Condition.GOOD));
        copy.setCondition(Condition.GOOD);
        // Condition don't change
        assertTrue(copy.getCondition().equals(Condition.GOOD));
    }
    
    @Test
    public void testToStringGood() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        String resultOrigin = book.toString();
        BookCopy copy = new BookCopy(book);
        String result = copy.toString();
        
        assertTrue(result.contains(resultOrigin));
        assertTrue(result.contains("good"));
        assertFalse(result.contains("damaged"));
    }
    
    @Test
    public void testToStringDamaged() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        String resultOrigin = book.toString();
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.DAMAGED);
        
        String result = copy.toString();
        assertTrue(result.contains(resultOrigin));
        assertFalse(result.contains("good"));
        assertTrue(result.contains("damaged"));
    }
    
    @Test
    public void testHashEquality() {
        Book book1 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        BookCopy copy0 = new BookCopy(book1);
        BookCopy copy1 = new BookCopy(book1);
        Book book2 = new Book("Python Course", Arrays.asList("Fred"), 1540);
        BookCopy copy2 = new BookCopy(book2);
        // Copy Himself
        assertTrue(copy0.equals(copy0));
        assertEquals(copy0.hashCode(), copy0.hashCode());
        // Copies of the same book
        assertFalse(copy0.equals(copy1));
        assertFalse(copy0.hashCode() == copy1.hashCode());
        // Copies of different books
        assertFalse(copy1.equals(copy2));
        assertFalse(copy1.hashCode() == copy2.hashCode());
        
        
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
