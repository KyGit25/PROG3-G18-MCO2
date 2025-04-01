/**
 * The Cat class represents a cat piece in the game, extending the Piece class.
 * A cat has a fixed strength and follows specific movement rules.
 */
public class Cat extends Piece {
    
    /**
     * Constant representing the strength of a cat.
     */
    public static final int CAT_STRENGTH = 2;
    
    /**
     * Constructs a Cat piece with the given attributes.
     * 
     * @param name   The name of the cat piece.
     * @param row    The starting row position.
     * @param col    The starting column position.
     * @param symbol The symbol representing the cat.
     * @param owner  The owner of the cat piece ("Blue" or "Green").
     * 
     * Pre-condition: row and col must be within valid board boundaries.
     * Post-condition: A new Cat piece is created with the given properties.
     */
    public Cat(String name, int row, int col, String symbol, String owner) {
        super(name, row, col, symbol, owner, CAT_STRENGTH);
    }

    /**
     * Determines if the Cat can move to the specified position based on game rules.
     * 
     * @param newRow The row position to move to.
     * @param newCol The column position to move to.
     * @param board  The game board containing all pieces and tiles.
     * 
     * @return true if the cat can move to the specified position, false otherwise.
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
    
        // Cats cannot move into lake tiles
        if (board.isLake(newRow, newCol)) {
            return false;
        }
    
        // Cats can move into the opponent's home base
        if (board.isHomeBase(newRow, newCol, this.getOwner().equals("Blue") ? "Green" : "Blue")) {
            return true;
        }
    
        // Cats can move to an empty tile
        if (board.isEmptyTile(newRow, newCol)) {
            return true;
        }
    
        // Cats can move onto a trap tile
        if (board.isTrap(newRow, newCol)) {
            return true;
        }
    
        // Check if there is an opponent's piece at the new position
        Piece opponent = board.getPieceAt(newRow, newCol);
        if (opponent != null && board.isOpponentPiece(this, newRow, newCol)) {
            // Determine if the cat can capture the opponent's piece
            return this.canCapture(opponent, board);
        }
    
        return false; // Default case: move is not valid
    }
}
