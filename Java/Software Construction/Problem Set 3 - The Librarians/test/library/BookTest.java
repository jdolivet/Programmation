package library;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;


/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * Tests for private fields
     * 
     * Book : constructor
     *      title : different case, test for immutability
     *      author list : empty list,tests for immutability (of the field and of the list)
     *      year : 0, 1000, other type, test for immutability
     * getTitle() : observer
     *      different case, test for immutability
     * getAuthors() : observer
     *      size 1, n 
     *      different cases
     *      tests for immutability (of the list)
     *      list ordened
     * getYear() : observer
     *      0, n
     * toString() : observer
     *      contents all the fields of Book
     * equals(obj) and  hashCode() : oberver
     *      book1 = book1 
     *      same title, other fields diff
     *      diff title, same others fields
     *      diff fields
     *      diff order on authors
     * 
     */
    
    @Test
    public void testPrivateFields() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        List <Field> attributs = Arrays.asList(book.getClass().getFields());
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
    public void testExampleBook() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed Astair", "Fred"), 1990);
        assertEquals("Java Course", book.getTitle());
        assertEquals(1990, book.getYear());
        assertEquals(3, book.getAuthors().size());
        assertTrue(book.getAuthors().containsAll(Arrays.asList("John", "FRed Astair", "Fred")));
    }
    
    @Test
    public void testTitleCase() {
        Book book = new Book("Java", Arrays.asList("John", "FRed", "Fred"), 1990);
        assertFalse("JaVa".equals(book.getTitle()));
    }
    
    @Test
    public void testAuthorListGetAuthorsImmutable() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        List<String> auteurs = book.getAuthors();
        auteurs.add("Allan");
        assertFalse(book.getAuthors().contains("Allan"));
        assertEquals(3, book.getAuthors().size());
        assertTrue(book.getAuthors().containsAll(Arrays.asList("John", "FRed", "Fred")));
    }
    
    @Test
    public void testListOrder() {
        Book book = new Book("Java Course", Arrays.asList("John", "Vern", "Adam"), 1990);
        assertEquals(0,book.getAuthors().indexOf("John"));
        assertEquals(1,book.getAuthors().indexOf("Vern"));
        assertEquals(2,book.getAuthors().indexOf("Adam"));
    }
    
    @Test
    public void testListConstructorImmutable() {
        List<String> auteurs = new ArrayList <String> (Arrays.asList("John", "FRed", "Fred"));
        Book book = new Book("Java Course", auteurs, 1990);
        auteurs.add("Allan");
        assertFalse(book.getAuthors().contains("Allan"));
        assertEquals(3, book.getAuthors().size());
        assertTrue(book.getAuthors().containsAll(Arrays.asList("John", "FRed", "Fred")));
    }
    
    @Test
    public void testYearZero() {
        Book book = new Book("Bible", Arrays.asList("Jesus"), 0);
        assertEquals(0, book.getYear());
    }    
    
    @Test
    public void testToString() {
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        String result = book.toString();        
        assertTrue(result.contains("Java Course"));
        assertTrue(result.contains("John"));
        assertTrue(result.contains("FRed"));
        assertTrue(result.contains("Fred"));
        assertTrue(result.contains("1990"));
    }
    
    @Test
    public void testHashEquality() {
        Book book1 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        Book book2 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        Book book3 = new Book("Java Course", Arrays.asList("Fred"), 1540);
        Book book4 = new Book("Python Course", Arrays.asList("Fred"), 1540);
        Book book5 = new Book("Python Course", Arrays.asList("FrEd"), 1540);
        Book book6 = new Book("Java Course", Arrays.asList("John", "Fred", "FRed"), 1990);
        
        assertTrue(book1.equals(book1));
        assertEquals(book1.hashCode(), book1.hashCode());
        
        assertTrue(book1.equals(book2));
        assertTrue(book2.equals(book1));
        assertEquals(book1.hashCode(), book2.hashCode());
        
        assertFalse(book2.equals(book3));
        assertFalse(book2.hashCode() == book3.hashCode());
        
        assertFalse(book3.equals(book4));
        assertFalse(book4.hashCode() == book3.hashCode());
        
        assertFalse(book5.equals(book4));
        assertFalse(book4.hashCode() == book5.hashCode());
        
        assertFalse(book1.equals(book6));
        assertFalse(book1.hashCode() == book6.hashCode());
        
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
