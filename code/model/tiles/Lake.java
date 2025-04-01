/**
 * The Lake class represents a lake tile on the game board.
 * It stores the position of the lake and provides methods to check its location.
 */
public class Lake extends Tile {
    
    /**
     * Constructs a Lake object with the given row and column position.
     * 
     * @param row The row position of the lake.
     * @param col The column position of the lake.
     * 
     * Pre-condition: row and col must be within the board boundaries.
     * Post-condition: A new Lake object is created at the specified location.
     */
    public Lake(int row, int col) {
        super(row, col);
    }

    @Override
    public void render() {
        if (this.currentPiece != null) {
            // TODO
            this.currentPiece.render();
        } else {
            System.out.print("~");
        }
    }

    @Override
    protected String getOwner() {
        return null;
    }

    /**
     * Checks if the given row and column match the lake's position.
     * 
     * @param r The row position to check.
     * @param c The column position to check.
     * 
     * @return true if the given position matches the lake's location, false otherwise.
     * 
     * Pre-condition: r and c must be valid board positions.
     * Post-condition: Returns whether the given position is a lake tile.
     */
    public boolean isLakeTile(int r, int c) {
        return this.row == r && this.col == c;
    }
}
