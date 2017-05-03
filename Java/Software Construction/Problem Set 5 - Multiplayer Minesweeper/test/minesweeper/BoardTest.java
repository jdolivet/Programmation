/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

/**
 * Tests for the Board class
 */
public class BoardTest {
    
    // Testing strategy
    //  dimensions : width and height adequate for the format
    //  from file, from skeleton, randomly
    //  the fields : is there a bomb, number of bomb next to the cell
    //  the methods : dig with or without bomb, flag, deflag
    
    /**
     * squelette 1 
     * 0100
     * 0100
     * 0001
     * 
     * squelette 2
     * 101
     * 001
     * 110
     * 000
     */
    private static final boolean[][] squelette1 = {{false,true,false,false},{false,true,false,false},{false,false,false,true}};
    private static final boolean[][] squelette2 = {{true,false,true},{false,false,true},{true,true,false},{false,false,false}};
    public static final File BOARD_FILE1 = new File("test/minesweeper/boards/board_file_1");
    public static final File BOARD_FILE2 = new File("test/minesweeper/boards/board_file_2");

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Initialization
    
    @Test //print the 4*3 board on the right size
    public void testToString4x3() {
        Board board = new Board(squelette2);
        //System.out.println(board);
        assertTrue(board.toString().equals("- - -\n- - -\n- - -\n- - -")); 
    }
    
    @Test //print the 3*4 board on the right size
    public void testToString3x4() {
        Board board = new Board(squelette1);
        assertTrue(board.toString().equals("- - - -\n- - - -\n- - - -")); 
    }
    
    @Test //print the board from the file
    public void testToStringFile() {        
        Board board = new Board(BOARD_FILE1);
      //System.out.println(board);
        assertTrue(board.toString().equals("- - - - - -\n- - - - - -\n- - - - - -\n- - - - - -\n- - - - - -\n- - - - - -\n- - - - - -"));
    }
    
    @Test //print the board from the file
    public void testToStringFile2() {        
        Board board = new Board(BOARD_FILE2);
        //System.out.println(board);
        assertTrue(board.toString().equals("- - - - - - -\n- - - - - - -\n- - - - - - -\n- - - - - - -\n- - - - - - -\n- - - - - - -"));
    }
    
    @Test //print the board on the right size
    public void testToStringRandom() {
        Board board = new Board(4,3);
        assertTrue(board.toString().equals("- - - -\n- - - -\n- - - -")); 
    }
    
    
    @Test //check if all square are UNTOUCHED
    public void testUnflagged() {
        Board board = new Board(squelette1);
        boolean untouched = true;
        int height = squelette1.length;
        int width = squelette1[0].length;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                untouched = untouched && board.cell(i,j).getState().equals(Square.State.UNTOUCHED);
            }
        }
        assertTrue(untouched); 
    }
    
    @Test //check some bomb
    public void testIsBomb() {
        Board board = new Board(squelette1);
        assertEquals(board.cell(0, 0).getBomb(), false);
        assertEquals(board.cell(1, 0).getBomb(), true);
        assertEquals(board.cell(1, 2).getBomb(), false);
        assertEquals(board.cell(2, 1).getBomb(), false);
        assertEquals(board.cell(3, 2).getBomb(), true);
    }
    
    @Test //check some NumBomb
    public void testNumBomb() {
        Board board = new Board(squelette1);
        assertEquals(board.cell(0, 0).getNumBombNear(), 2);
        assertEquals(board.cell(1, 0).getNumBombNear(), 1);
        assertEquals(board.cell(1, 2).getNumBombNear(), 1);
        assertEquals(board.cell(2, 1).getNumBombNear(), 3);
        assertEquals(board.cell(3, 2).getNumBombNear(), 0);
    }
    
    @Test //check the dig method with bomb
    public void testDigBomb() {
        Board board = new Board(squelette1);
        board.dig(1, 0);
        //System.out.println(board);
        assertTrue(board.cell(1, 0).getState() == Square.State.DUG);
        assertEquals(board.cell(1, 0).getBomb(), false);
        assertEquals(board.cell(2, 0).getNumBombNear(), 1);
        assertEquals(board.cell(1, 1).getNumBombNear(), 0);
    }
    
    @Test //check the dig method recursion
    public void testDigNoBomb() {
        Board board = new Board(squelette1);
        board.dig(3,0);
        //System.out.println(board);
        assertTrue(board.cell(3,0).getState().equals(Square.State.DUG));
        assertTrue(board.cell(2,0).getState().equals(Square.State.DUG));
        assertTrue(board.cell(3,1).getState().equals(Square.State.DUG));
        assertTrue(board.cell(2,1).getState().equals(Square.State.DUG));
    }
    
    @Test //check the flag method
    public void testFlag() {
        Board board = new Board(squelette1);        
        board.flag(3, 0);
        //System.out.println(board);
        assertTrue(board.cell(3,0).getState().equals(Square.State.FLAGGED));
        board.dig(3, 0);
        //System.out.println(board);
        assertTrue(board.cell(3,0).getState().equals(Square.State.FLAGGED));
    }
    
    @Test //check the deflag method
    public void testDeFlag() {
        Board board = new Board(squelette1);        
        board.flag(3, 0);
        //System.out.println(board);
        assertTrue(board.cell(3,0).getState().equals(Square.State.FLAGGED));
        board.deflag(3, 0);
        //System.out.println(board);
        assertTrue(board.cell(3,0).getState().equals(Square.State.UNTOUCHED));
    }
    
    
}
