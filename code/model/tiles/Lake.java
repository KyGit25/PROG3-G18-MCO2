package model.tiles;

public class Lake extends Tile {

    /**
 * Constructs a Lake tile at a specific row and column.
 *
 * Pre-condition:
 * - row and col are valid tile coordinates.
 *
 * Post-condition:
 * - A Lake tile is created at the given location.
 *
 * @param row The row index of the lake tile.
 * @param col The column index of the lake tile.
 */
    public Lake(int row, int col) {
        super(row, col);
    }
}
