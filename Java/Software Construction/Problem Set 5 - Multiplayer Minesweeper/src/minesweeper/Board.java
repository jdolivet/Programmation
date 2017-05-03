/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * An immutable data type representing the board
 *      It represents a rectangle, each cell has a square type
 */
public class Board {
    
    private static final double DEFAULT_IS_BOMB_PROBABILITY = 0.25;

    
    private final Square[][] board;
    private final int height;
    private final int width;
    
    // Abstraction function
    //      represent the rectangular board of the game with width and height dimensions
    //      each cell is a square
    // Rep invariant
    //      the width and height are strictly positive and according with the board
    // Rep exposure
    //      all fields are private and final
    // Thread safety
    //      Immutability : observer methods return immutable fields
    //      Synchronization : the modifier methods are synchronized
    
    private void checkRep() {
        assert this.height > 0;
        assert this.width > 0;
        assert this.height == board.length;
        assert this.width == board[0].length;
    }
    
    /**
     * Helper function 
     * Create a random skeleton of boolean
     * Each cell is true or false if there is a bomb
     * The probability is 0.25
     * @param height 
     * @param width 
     */
    private boolean[][] skeleton(int width, int height){
        boolean[][] skeleton = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean bomb = false;
                Random randomBomb = new Random();
                if (randomBomb.nextDouble() < DEFAULT_IS_BOMB_PROBABILITY){
                    bomb = true;
                }
                skeleton[i][j] = bomb;
            }
        }
        return skeleton;
    }
    
    /**
     * Helper function 
     * Create a skeleton from a file 
     * Each cell is true or false if there is a 1
     * @param file 
     */
    private boolean[][] skeleton(File fichier) throws RuntimeException{
        boolean[][] result;
        try {
            Scanner in = new Scanner(new FileReader(fichier));
            int width = in.nextInt();
            int height = in.nextInt();
            result = new boolean[height][width];
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (in.nextInt() == 1) {
                        result[i][j] = true;
                    }
                    else{
                        result[i][j] = false;
                    }
                }
            }
            in.close();
            } catch (IOException e) {
                throw new RuntimeException("File illegal format.");
            }
        return result;
    }
    
    /**
     * Helper function 
     * Create a board from skeleton
     * Each cell is untouched and count the neighbors bombs
     * @param skeleton
     */
    private Square[][] constructBoard(boolean[][] skeleton){
        int height = skeleton.length;
        int width = skeleton[0].length;
        Square[][] board = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean isBomb = skeleton[i][j];
                // count the neighbors bombs
                int numBombNear = 0;
                for (int k = Math.max(0,i-1); k <= Math.min(height-1, i+1); k++){
                    for (int l = Math.max(0,j-1); l <= Math.min(width-1, j+1); l++){
                        if ((k != i) || (l != j)){
                            if (skeleton[k][l] == true){
                                numBombNear += 1;
                            }
                        }
                    }
                }
                board[i][j] = new Square(Square.State.UNTOUCHED, isBomb, numBombNear);
            }
        }
        return board;
    }
    
    
    /**
     * Constructor
     * Create a board using a skeleton of bombs
     * Each square is untouched and count the neighborhood bombs
     * @param boolean[][] skeleton 
     */
    public Board(boolean[][] skeleton){
        this.height = skeleton.length;
        this.width = skeleton[0].length;
        this.board = constructBoard(skeleton);
        checkRep();
    }
    
    /**
     * Constructor
     * Create a board with dimension of the board
     * Each square is untouched and count the neighborhood bombs
     * @param height 
     * @param width 
     */
    public Board(int width, int height){
        this.height = height;
        this.width = width;
        // Generate a skeleton
        boolean[][] squelette = skeleton(width, height);
        this.board = constructBoard(squelette);
        checkRep();
    }
    
    /**
     * Constructor
     * Create a board from a file
     * Each square is untouched and count the neighborhood bombs
     * @param file
     */
    public Board(File fichier){
        boolean[][] squelette = skeleton(fichier);
        this.height = squelette.length;
        this.width = squelette[0].length;
        this.board = constructBoard(squelette);
        checkRep();
    }
    
    
    /**
     * Observer
     * @returns the number of columns
     **/
    public int getCol(){
        return this.width;
    }
    
    /**
     * Observer
     * @returns the number of rows
     **/
    public int getRow(){
        return this.height;
    }
    
    
    /**
     * Helper function
     * @returns the list of coordonates of adjacent cells of Square at position x=i and y=j
     **/
    private List<int[]> adjacentCoord(int i, int j){
        ArrayList<int[]> neighbors = new ArrayList<int[]>();
        for (int k = Math.max(0,j-1); k <= Math.min(height-1, j+1); k++){
            for (int l = Math.max(0,i-1); l <= Math.min(width-1, i+1); l++){
                if ((k != j) || (l != i)){
                    int[] coord = {l,k};
                    neighbors.add(coord);
                }
            }
        }
        return neighbors;
    }
    
    /**
     * Observer
     * @returns the Square at position x=i and y=j
     **/
    public Square cell(int i, int j){
        Square result = new Square(board[j][i]);
        return result;
    }
    
    /**
     * Modifier
     * Dig a square, at position x=i and y=j, on the board.
     * Need 0 <= i < width AND 0 <= j < height AND state is UNTOUCHED.
     * @param i col
     * @param j row 
     * @return a string with the due message
     */
    public synchronized String dig(int i, int j) {
        String message = "";
        if (i < this.width && j < this.height
                && i >= 0 && j >= 0
                && board[j][i].getState() == Square.State.UNTOUCHED) {
            board[j][i] = board[j][i].changeState(Square.State.DUG);
            if (board[j][i].getBomb()){
                board[j][i] = board[j][i].noBomb();
                message += "BOOM!\n";
                // if debug flag missing, terminate the connection
                List<int[]> neighbors = this.adjacentCoord(i,j);                
                for(int[] neighbor : neighbors){
                    board[neighbor[1]][neighbor[0]] = board[neighbor[1]][neighbor[0]].decreaseNumBombNear();
                }
            }
            if (board[j][i].getNumBombNear() == 0){
                List<int[]> neighbors = this.adjacentCoord(i,j);
                for(int[] neighbor : neighbors){
                    if (board[neighbor[1]][neighbor[0]].getState() != Square.State.DUG){
                        dig(neighbor[0], neighbor[1]);
                    }
                }
            }
        }
        message+=this.toString();
        return message;
    }
    
    /**
     * Modifier
     * Flag a square on the board.
     * Need 0 <= i < width AND 0 <= j < height AND state is UNTOUCHED.
     * @param i col
     * @param j row 
     * @return a string with the due message
     */
    public synchronized String flag(int i, int j) {
        String message = "";
        if (i < this.width && j < this.height
                && i >= 0 && j >= 0
                && board[j][i].getState() == Square.State.UNTOUCHED) {
            board[j][i] = board[j][i].changeState(Square.State.FLAGGED);
        }
        message+=this.toString();
        return message;
    }
    
    /**
     * Modifier
     * Deflag a square on the board.
     * Need 0 <= i < width AND 0 <= j < height AND state is FLAGGED.
     * @param i col
     * @param j row 
     * @return a string with the due message
     */
    public synchronized String deflag(int i, int j) {
        String message = "";
        if (i < this.width && j < this.height
                && i >= 0 && j >= 0
                && board[j][i].getState() == Square.State.FLAGGED) {
            board[j][i] = board[j][i].changeState(Square.State.UNTOUCHED);
        }
        message+=this.toString();
        return message;
    }
    
    /**
     * Observer
     * @returns the string representation of the board
     **/
    public synchronized String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                output.append(this.board[i][j].toString());
                if (j != this.width - 1) {
                    output.append(' ');
                } else {
                    if (i != this.height - 1){
                        output.append('\n');
                    }
                    
                }
            }
        }
        return output.toString();
    }
    
    
}
