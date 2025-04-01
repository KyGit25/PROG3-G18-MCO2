/**
 * The HomeBase class represents a player's home base in the game.
 * It stores the position and owner of the home base.
 */
public class HomeBase extends Tile {
    
    /**
     * The owner of the home base ("Blue" or "Green").
     */
    private String owner;

    /**
     * Constructs a HomeBase with the given row, column, and owner.
     * 
     * @param row   The row position of the home base.
     * @param col   The column position of the home base.
     * @param owner The owner of the home base ("Blue" or "Green").
     * 
     * Pre-condition: row and col must be within the board boundaries.
     * Post-condition: A new HomeBase object is created with the specified attributes.
     */
    public HomeBase(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

    @Override
    public void render() {
        // TODO
    }

    @Override
    protected String getOwner() {
        return this.owner;
    }
}
