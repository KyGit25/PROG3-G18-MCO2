package model.interfaces;

import model.tiles.Tile;

/**
 * Interface for pieces that can swim in lake tiles.
 */
public interface Swimming 
{
    /**
     * Determines if a piece can swim into a specified tile.
     *
     * Pre-condition:
     * - The destination must be a tile adjacent to current position.
     *
     * Post-condition:
     * - Returns the destination if it is a valid lake tile.
     * - Returns null if the tile cannot be swum into.
     *
     * @param destination The target tile.
     * @return The destination if swim is allowed; otherwise, null.
     */
    Tile swim(Tile destination);
}
