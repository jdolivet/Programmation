package library;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * Test suite for BigLibrary's stronger specs.
 */
public class BigLibraryTest {
    
    /* 
     * NOTE: use this file only for tests of BigLibrary.find()'s stronger spec.
     * Tests of all other Library operations should be in LibraryTest.java 
     */

    /*
     * Testing strategy
     * ==================
     * 
     * Testing find :
     *      exact match : exact title or exact author
     *      word matching : no, one, more
     *      copies : 0, 1, more
     *      
     */
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        // this is just an example test, you should delete it
        Library library = new BigLibrary();
        assertEquals(Collections.emptyList(), library.find("This Test Is Just An Example"));
        
        // one book
        Book book1 = new Book("Java Course", Arrays.asList("John", "FRed Astair"), 1990);        
        library.buy(book1);
        assertFalse(library.find("Jav").contains(book1));
        assertTrue(library.find("Java").contains(book1));
        assertTrue(library.find("Course").contains(book1));
        assertTrue(library.find("FRed").contains(book1));
        assertTrue(library.find("FRed Astair").contains(book1));
        
        // Two books same numbers of copies
        Book book2 = new Book("Python Course", Arrays.asList("Allan", "FRed Astair"), 1990);        
        library.buy(book2);
        assertTrue(library.find("FRed").containsAll(Arrays.asList(book1, book2)));
        assertTrue(library.find("Course").containsAll(Arrays.asList(book1, book2)));
        
        // Two books diff numbers of copies
        library.buy(book2);
        assertTrue(library.find("Course").indexOf(book2) < library.find("Course").indexOf(book1));
        
        // Ordered (matches)
        Book book3 = new Book("C Course", Arrays.asList("Allan", "FRed Astair"), 1990);
        library.buy(book3);
        library.buy(book3);
        library.buy(book3);
        assertTrue(library.find("Course").indexOf(book3) < library.find("Java Course").indexOf(book2));
        assertTrue(library.find("Course").indexOf(book3) < library.find("Java Course").indexOf(book1));
        
        // Ordered (date)
        Book book4 = new Book("Java Course", Arrays.asList("Henry", "FRed"), 1990);        
        library.buy(book4);   
        Book book5 = new Book("Java Course", Arrays.asList("Henry", "FRed"), 1996);        
        library.buy(book5);        
        Book book6 = new Book("Java Course", Arrays.asList("Henry", "Allan"), 1984);        
        library.buy(book6); 
        assertTrue(library.find("Henry").indexOf(book5) < library.find("Henry").indexOf(book4));
        assertTrue(library.find("Henry").indexOf(book4) < library.find("Henry").indexOf(book6));
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
