/**
 * The Trap class represents a trap tile on the game board.
 * It stores the position, owner, and occupancy status of the trap.
 */
public class Trap extends Tile {
    
    /**
     * The owner of the trap ("Blue" or "Green").
     */
    private String owner;

    /**
     * Constructs a Trap object with the given row, column, and owner.
     * 
     * @param row   The row position of the trap.
     * @param col   The column position of the trap.
     * @param owner The owner of the trap ("Blue" or "Green").
     * 
     * Pre-condition: row and col must be within the board boundaries.
     * Post-condition: A new Trap object is created with the specified attributes.
     */
    public Trap(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

    @Override
    public void render() {
        if (this.currentPiece != null) {
            // TODO
        } else {
            System.out.print("!");
        }
    }

    @Override
    protected String getOwner() {
        return this.owner;
    }

    /**
     * Sets the occupancy status of the trap.
     * 
     * @param occupied true if a piece is occupying the trap, false otherwise.
     * 
     * Pre-condition: Trap object must exist.
     * Post-condition: Updates the occupancy status.
     */
    public void setOccupied(boolean occupied) {
        this.currentPiece = occupied ? this.currentPiece : null;
    }
}
