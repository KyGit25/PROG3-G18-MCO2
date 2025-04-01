package model.interfaces;

import model.tiles.Tile;

/**
 * Interface for pieces that can leap over tiles (e.g., lakes).
 */
public interface Leaping 
{
    /**
     * Attempts to leap over a series of tiles to reach a destination.
     *
     * Pre-condition:
     * - The destination must be in a straight line with the current position.
     * - Path between current position and destination must be unobstructed.
     *
     * Post-condition:
     * - Returns the destination tile if leap is valid.
     * - Returns null if leap is not possible.
     *
     * @param destination The tile to leap to.
     * @return The destination if leap is allowed; otherwise, null.
     */
    Tile leap(Tile destination);
}


