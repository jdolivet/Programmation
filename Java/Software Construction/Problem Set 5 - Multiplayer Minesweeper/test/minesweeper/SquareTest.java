package minesweeper;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Tests for the Square class
 */
public class SquareTest {
    
    // Testing strategy
    //  the methods : constructor, observer and modifier
    
    
   
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Initialization
    
    @Test 
    public void testObservers() {
        Square cell = new Square(Square.State.UNTOUCHED, true, 2);
        assertEquals(cell.getState(), Square.State.UNTOUCHED); 
        assertEquals(cell.getBomb(), true); 
        assertEquals(cell.getNumBombNear(), 2); 
    }
    
    @Test 
    public void testModifiers() {
        Square cell = new Square(Square.State.UNTOUCHED, true, 2);
        cell = cell.changeState(Square.State.DUG);
        assertEquals(cell.getState(), Square.State.DUG); 
        cell = cell.decreaseNumBombNear();
        assertEquals(cell.getNumBombNear(), 1); 
    }
    
    
}