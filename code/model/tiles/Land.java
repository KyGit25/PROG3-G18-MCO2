package model.tiles;

public class Land extends Tile {

    /**
 * Constructs a Land tile at a specific row and column.
 *
 * Pre-condition:
 * - row and col are valid tile coordinates.
 *
 * Post-condition:
 * - A Land tile is created at the given location.
 *
 * @param row The row index of the land tile.
 * @param col The column index of the land tile.
 */
    public Land(int row, int col) {
        super(row, col);
    }
}
