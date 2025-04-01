package model.tiles;

public class Trap extends Tile {
    private String owner;

/**
 * Constructs a Trap tile with owner designation.
 *
 * Pre-condition:
 * - row and col are valid coordinates.
 * - owner must be a valid team name (e.g., "Blue" or "Green").
 *
 * Post-condition:
 * - A Trap tile is created and owned by the specified player.
 *
 * @param row The row index of the trap.
 * @param col The column index of the trap.
 * @param owner The owning team's name.
 */
    public Trap(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

/**
 * Gets the owner of this trap tile.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the name of the player who owns this trap.
 *
 * @return The owner's name.
 */
    public String getOwner() {
        return owner;
    }
}
