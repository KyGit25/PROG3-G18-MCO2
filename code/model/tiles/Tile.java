package model.tiles;

import model.pieces.Piece;

public abstract class Tile {
    protected String tilePos;
    protected int row;
    protected int col;
    protected Piece currPiece;
    protected String type;

/**
 * Constructs a Tile object with a specified row and column.
 *
 * Pre-condition:
 * - row and col must be valid board coordinates.
 *
 * Post-condition:
 * - Initializes tile position, sets currPiece to null.
 *
 * @param row The row index of the tile.
 * @param col The column index of the tile.
 */
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.tilePos = row + "" + col;
        this.currPiece = null;
    }
    
/**
 * Gets the tile's encoded position as a string (row + col).
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns a 2-digit string representing tile's location.
 *
 * @return The tile's position string (e.g., "00", "34").
 */
    public String getTilePos() {
        return tilePos;
    }

/**
 * Retrieves the row index of the tile.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the tile's row value.
 *
 * @return The tile's row index.
 */
    public int getRow() {
        return row;
    }

/**
 * Retrieves the column index of the tile.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the tile's column value.
 *
 * @return The tile's column index.
 */
    public int getCol() {
        return col;
    }

/**
 * Checks if the tile is currently occupied by a piece.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns true if a piece is present on the tile.
 *
 * @return true if occupied, false if empty.
 */
    public boolean isOccupied() {
        return currPiece != null;
    }

/**
 * Retrieves the piece currently on the tile.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the current piece or null if unoccupied.
 *
 * @return The current piece on this tile.
 */
    public Piece getCurrPiece() {
        return currPiece;
    }

/**
 * Sets the piece occupying the tile.
 *
 * Pre-condition:
 * - piece can be null or a valid Piece object.
 *
 * Post-condition:
 * - currPiece is updated to the given piece.
 *
 * @param piece The piece to place on the tile.
 * @return true after assignment.
 */
    public boolean setCurrPiece(Piece piece) {
        this.currPiece = piece;
        return true;
    }

/**
 * Removes the current piece from the tile.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - currPiece is set to null (tile becomes unoccupied).
 */
    public void removePiece() {
        this.currPiece = null;
    }

/**
 * Sets the type of this tile (e.g., "Land", "Lake", "Trap").
 *
 * Pre-condition:
 * - type must be a valid string label.
 *
 * Post-condition:
 * - The tile's type is set.
 *
 * @param type The string label for tile type.
 */
    public void setType(String type) {
        this.type = type;
    }

/**
 * Gets the type label of the tile.
 *
 * Pre-condition:
 * - setType() has been called or default value exists.
 *
 * Post-condition:
 * - Returns the type string of the tile.
 *
 * @return The tile's type.
 */
    public String getType() {
        return type;
    }
}
