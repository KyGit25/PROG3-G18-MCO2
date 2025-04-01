package model.tiles;

public class HomeBase extends Tile {
    private String owner;

/**
 * Constructs a HomeBase tile with owner designation.
 *
 * Pre-condition:
 * - row and col are valid coordinates.
 * - owner must be a valid team name (e.g., "Blue" or "Green").
 *
 * Post-condition:
 * - A HomeBase tile is created and assigned to the given player.
 *
 * @param row The row index of the home base.
 * @param col The column index of the home base.
 * @param owner The owning player's name.
 */
    public HomeBase(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

/**
 * Gets the owner of this home base tile.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the name of the player who owns this home base.
 *
 * @return The owner's name.
 */
    public String getOwner() {
        return owner;
    }
}
