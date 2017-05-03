package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * ==================
     * 
     * 
     * buy() : producer
     *      one book, no copy then one copy
     *      one book two copies
     *      two books
     * checkout() : mutator
     *      in -> out
     * checkin() : mutator
     *      out -> in
     * isAvailable() : observer
     *      in, out, nowhere
     * allCopies() : producer
     *      in, out, nowhere
     * availableCopies() : observer
     *      in, out, nowhere
     * find() : producer
     *      one copy, most copies, ordered by matches, then ordered by date
     *      available or checked out
     *      lost
     * lose() : mutator
     *      in, out
     * equals(obj) and hashCode() : oberver
     *      lib = lib 
     *      2 lib with the same copies
     *      2 lib with the diff books
     */
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    
    
    @Test
    public void testPrivateFields() {
        Library library = makeLibrary();
        Book book = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        library.buy(book);
        List <Field> attributs = Arrays.asList(library.getClass().getFields());
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
    public void testOneBookBuy() {
        Library library = makeLibrary();
        
        // Initialization all is empty
        Book book = new Book("Java Course", Arrays.asList("John", "FRed"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book));
        assertEquals(Collections.emptySet(), library.allCopies(book));
        assertEquals(Collections.emptyList(), library.find("Java Course"));
        assertEquals(Collections.emptyList(), library.find("FRed"));
        
        // Copy which can't enter
        BookCopy copy0 = new BookCopy(book);
        assertFalse(library.isAvailable(copy0));
        assertFalse(library.allCopies(book).contains(copy0));
        
        // Buy it so let's start
        BookCopy copy = library.buy(book);        
        // Condition good
        copy.getCondition().equals(copy0.getCondition());        
        // available
        assertTrue(library.isAvailable(copy));        
        assertTrue(library.availableCopies(book).contains(copy));
        assertTrue(library.allCopies(book).contains(copy));
        // We can find it
        assertTrue(library.find("Java Course").contains(book));
        assertTrue(library.find("FRed").contains(book));
        assertTrue(library.find("John").contains(book));
    }
    
    @Test
    public void testOneBookTwoCopiesSteps() {
        Library library = makeLibrary();
        
        // Two copies of a the same book bought
        Book book = new Book("Java Course", Arrays.asList("John", "FRed"), 1990);        
        BookCopy copy1 = library.buy(book);
        BookCopy copy2 = library.buy(book);
        
        // Check if all is in
        assertTrue(library.isAvailable(copy1));
        assertTrue(library.isAvailable(copy2));
        assertTrue(library.availableCopies(book).containsAll(Arrays.asList(copy1, copy2)));
        assertTrue(library.allCopies(book).containsAll(Arrays.asList(copy1, copy2)));
        
        // copy2 out
        library.checkout(copy2);
        assertFalse(library.isAvailable(copy2));
        assertFalse(library.availableCopies(book).contains(copy2));
        assertTrue(library.allCopies(book).contains(copy2));
        
        // copy2 in
        library.checkin(copy2);
        assertTrue(library.isAvailable(copy2));
        assertTrue(library.availableCopies(book).contains(copy2));
        
        // copy2 (inside) lost
        library.lose(copy2);
        assertFalse(library.isAvailable(copy2));
        assertFalse(library.availableCopies(book).contains(copy2));
        assertFalse(library.allCopies(book).contains(copy2));
        
        // copy1 (outside) lost
        library.checkout(copy1);
        library.lose(copy1);
        assertFalse(library.availableCopies(book).contains(copy1));
        assertFalse(library.allCopies(book).contains(copy1));
        
        // Library is empty!
        assertTrue(library.availableCopies(book).isEmpty());
        assertTrue(library.allCopies(book).isEmpty());
    }
    
    @Test
    public void testMultiBooksFind() {
        Library library = makeLibrary();
        
        // Empty library
        assertEquals(library.find("Java Course"), Collections.emptyList());
        
        // List of books
        Book book1 = new Book("Java Course", Arrays.asList("John", "FRed"), 1990);        
        library.buy(book1);   
        library.buy(book1); 
        Book book2 = new Book("Java Course", Arrays.asList("John", "FRed"), 1996);        
        library.buy(book2);        
        Book book3 = new Book("Java Course", Arrays.asList("John", "Allan"), 1984);        
        BookCopy copy3 = library.buy(book3); 
        BookCopy copy3bis = library.buy(book3); 
        Book book4 = new Book("Python Course", Arrays.asList("John", "Allan"), 1990);        
        BookCopy copy4 =library.buy(book4);  
        library.buy(book4);
        Book book5 = new Book("Python Course", Arrays.asList("FRed", "Allan"), 1990);        
        library.buy(book5);        
        
        assertTrue(library.find("Java Course").containsAll(Arrays.asList(book1, book2, book3)));
        assertTrue(library.find("Python Course").containsAll(Arrays.asList(book4, book5)));
        assertTrue(library.find("FRed").containsAll(Arrays.asList(book1, book2, book5)));
        assertTrue(library.find("John").containsAll(Arrays.asList(book1, book2, book3, book4)));
        
        // cannot repeat the same book
        Book book6 = new Book("Programming", Arrays.asList("FRed", "Allan"), 1990);        
        library.buy(book6);
        Book book7 = new Book("Programming", Arrays.asList("FRed", "Allan"), 1990);        
        library.buy(book7);
        assertTrue(library.find("Programming").size() == 1);
        
        // Ordered by matches
        library.buy(book5);
        assertTrue(library.find("Java Course").indexOf(book1) < library.find("Java Course").indexOf(book2));
        assertTrue(library.find("Java Course").indexOf(book3) < library.find("Java Course").indexOf(book2));
        assertTrue(library.find("Allan").indexOf(book4) < library.find("Allan").indexOf(book3));
        
        // Ordered by dates
        library.buy(book2); 
        assertTrue(library.find("Java Course").indexOf(book2) < library.find("Java Course").indexOf(book1));
        assertTrue(library.find("Java Course").indexOf(book1) < library.find("Java Course").indexOf(book3));
        
        // if outside must be in the result
        library.checkout(copy4);
        assertTrue(library.find("Python Course").contains(book4));
        
        // if lost can't be in the result
        library.lose(copy3);
        library.lose(copy3bis);
        assertFalse(library.find("Java Course").contains(book3));
        
        // lost one but 2 copies one outside one inside
        BookCopy copy7 = library.buy(book3);
        library.checkout(copy7);
        BookCopy copy8 = library.buy(book3);
        library.lose(copy8);
        assertTrue(library.find("Java Course").contains(book3));
        
    }
        
    @Test
    public void testHashEquality() {
        Library library1 = makeLibrary();
        Library library2 = makeLibrary();
        
        Book book1 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        Book book2 = new Book("Python Course", Arrays.asList("Fred"), 1540);
        library1.buy(book1);
        library2.buy(book1);
        
        // Library Himself
        assertTrue(library1.equals(library1));
        assertEquals(library1.hashCode(), library1.hashCode());
        
        // Libraries with the same book
        assertFalse(library1.equals(library2));
        assertFalse(library1.hashCode() == library2.hashCode());
        
        // Libraries with different books
        library2.buy(book2);
        assertFalse(library1.equals(library2));
        assertFalse(library1.hashCode() == library2.hashCode());
        
        
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
