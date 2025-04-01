/**
 * The Dog class represents a dog piece in the game, extending the Piece class.
 * A dog has a fixed strength and follows specific movement rules.
 */
public class Dog extends Piece {
    
    /**
     * Constant representing the strength of a dog.
     */
    public static final int DOG_STRENGTH = 3;
    
    /**
     * Constructs a Dog piece with the given attributes.
     * 
     * @param name   The name of the dog piece.
     * @param row    The starting row position.
     * @param col    The starting column position.
     * @param symbol The symbol representing the dog.
     * @param owner  The owner of the dog piece ("Blue" or "Green").
     * 
     * Pre-condition: row and col must be within valid board boundaries.
     * Post-condition: A new Dog piece is created with the given properties.
     */
    public Dog(String name, int row, int col, String symbol, String owner) {
        super(name, row, col, symbol, owner, DOG_STRENGTH);
    }

    /**
     * Determines if the Dog can move to the specified position based on game rules.
     * 
     * @param newRow The row position to move to.
     * @param newCol The column position to move to.
     * @param board  The game board containing all pieces and tiles.
     * 
     * @return true if the dog can move to the specified position, false otherwise.
     * 
     * Pre-condition: newRow and newCol are within the board dimensions.
     * Post-condition: Returns whether the movement is valid based on board conditions.
     */
    @Override
    public boolean canMove(int newRow, int newCol, Board board) {
        // Check if the new position is outside the board boundaries
        if (newRow < 0 || newRow >= board.getRows() || newCol < 0 || newCol >= board.getCols()) {
            return false; // Out of bounds
        }
    
        // Dogs cannot move into lake tiles
        if (board.isLake(newRow, newCol)) {
            return false;
        }
    
        // Dogs can move into the opponent's home base
        if (board.isHomeBase(newRow, newCol, this.getOwner().equals("Blue") ? "Green" : "Blue")) {
            return true;
        }
    
        // Dogs can move to an empty tile
        if (board.isEmptyTile(newRow, newCol)) {
            return true;
        }
    
        // Dogs can move onto a trap tile and become trapped
        if (board.isTrap(newRow, newCol)) {
            this.setTrapped(true);
            return true;
        }
    
        // Check if there is an opponent's piece at the new position
        Piece opponent = board.getPieceAt(newRow, newCol);
        if (opponent != null && board.isOpponentPiece(this, newRow, newCol)) {
            // Determine if the dog can capture the opponent's piece
            return this.canCapture(opponent, board);
        }
    
        return false; // Default case: move is not valid
    }
}
