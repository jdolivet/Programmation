package minesweeper;

/**
 * Class of square
 * Element of the board
 * 
 */
public final class Square {
    
    enum State{FLAGGED, DUG, UNTOUCHED};
    
    private final State state;
    private final boolean isBomb;
    private final int numBombNear;
    
    // Abstraction function
    //    represent a cell of the board with :
    //      a bomb or not inside
    //      a state : Untouched, Dug or Flagged
    //      the number of bomb in adjacent cells
    // Rep invariant
    //    0 <= numBombNear <= 8
    // Rep exposure
    //    fields are private, final and immutable and modifier methods return new element
    // Thread safety
    //      Immutability : observer methods return immutable fields
    //      Synchronization : the modifier methods are synchronized
    
    private void checkRep() {
        assert numBombNear >= 0 && numBombNear <= 8;
    }
    
    /**
     * Constructor
     * @param whichState : the state of the cell
     * @param isItBomb : is it a bomb
     * @param numBombNeighborhood : number of bomb next to the cell
     */
    public Square(State whichState, boolean isItBomb, int numBombNeighborhood){
        this.state = whichState;
        this.isBomb = isItBomb;
        this.numBombNear = numBombNeighborhood;
        checkRep();
    }
    
    /**
     * Constructor (copy)
     * @param Square 
     */
    public Square(Square cell){
        this.state = cell.state;
        this.isBomb = cell.isBomb;
        this.numBombNear = cell.numBombNear;
        checkRep();
    }
    
    /**
     * Observer
     * @returns the state of the square
     **/
    public State getState() {
        return state;
    }
    
    /**
     * Observer
     * @returns if there's a bomb or not
     **/
    public boolean getBomb() {
        return this.isBomb;
    }
    
    /**
     * Observer
     * @returns numbers of bomb in the neighborhood
     **/
    public int getNumBombNear() {
        return this.numBombNear;
    }
    
    /**
     * Modifier : change the state of the square
     * @param state The new Square's state.
     * @return The new Square object with changed state.
     */
    public Square changeState(State state) {
        return new Square(state, this.isBomb, this.numBombNear);
    }
    
    /**
     * Modifier : change the containing of the square
     * @return The new Square object with no bomb.
     */
    public Square noBomb() {
        return new Square(this.state, false, this.numBombNear);
    }
    
    /**
     * Modifier : decrease the number of bombs in the neighborhoof
     * @return The new Square object with less bomb
     */
    public Square decreaseNumBombNear() {
        if (this.numBombNear > 0){
            return new Square(this.state, this.isBomb, this.numBombNear - 1);
        }
        return new Square(this.state, this.isBomb, this.numBombNear);
    }
    
    
    /**
     * Observer
     * @returns the string representation of the square
     **/
    @Override
    public String toString() {
        switch(this.state){
        case DUG:
            if (this.isBomb){
                return "*";
            }
            else if (this.numBombNear == 0){
                return " ";
            }
            else{
                return String.valueOf(numBombNear);
            }
        case UNTOUCHED:
            return "-";
        case FLAGGED:
            return "F";
        }
        throw new RuntimeException("The state of the cell is not defined!");
    }

}
