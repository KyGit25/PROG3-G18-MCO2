package model.pieces;

import model.tiles.Tile;

public abstract class Piece {
    protected Tile pos;
    protected boolean isCaptured;
    protected String owner;

/**
 * Constructs a piece with a given tile position and owner.
 *
 * Pre-condition:
 * - pos must not be null.
 * - owner must be a valid player name.
 *
 * Post-condition:
 * - The piece is assigned its position and owner.
 * - The tile's current piece is set to this piece.
 *
 * @param pos The tile where the piece starts.
 * @param owner The name of the player who owns the piece.
 */
    public Piece(Tile pos, String owner) {
        this.pos = pos;
        this.owner = owner;
        this.isCaptured = false;
        if (pos != null) {
            pos.setCurrPiece(this);
        }
    }

/**
 * Checks if the piece has been captured.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns true if the piece is marked as captured.
 *
 * @return true if captured; false otherwise.
 */
    public boolean isCaptured() {
        return isCaptured;
    }

/**
 * Sets the capture state of the piece.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - The piece's captured status is updated.
 *
 * @param captured true to mark as captured, false otherwise.
 */
    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }

/**
 * Gets the owner of this piece.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the owner's name.
 *
 * @return The player name who owns this piece.
 */
    public String getOwner() {
        return owner;
    }

/**
 * Updates the owner of the piece.
 *
 * Pre-condition:
 * - owner must be a valid player name.
 *
 * Post-condition:
 * - The owner of the piece is updated.
 *
 * @param owner The new owner of the piece.
 */
    public void setOwner(String owner) {
        this.owner = owner;
    }

/**
 * Determines whether this piece can capture a specific target piece.
 *
 * Pre-condition:
 * - Implemented by subclasses.
 *
 * Post-condition:
 * - Returns true if the piece can capture the target.
 *
 * @param piece The target piece to capture.
 * @return true if capture is possible; false otherwise.
 */
    public abstract boolean canCapture(Piece piece);

/**
 * Checks whether this piece can move to the specified destination tile.
 *
 * Pre-condition:
 * - Implemented by subclasses.
 *
 * Post-condition:
 * - Returns true if movement is allowed.
 *
 * @param destination The target tile.
 * @return true if move is valid; false otherwise.
 */
    public abstract boolean canMove(Tile destination);

/**
 * Gets the strength ranking of the piece.
 *
 * Pre-condition:
 * - Implemented by subclasses.
 *
 * Post-condition:
 * - Returns an integer representing strength (1 to 8).
 *
 * @return The piece's strength.
 */
    public abstract int getStrength();

/**
 * Retrieves the current position of the piece.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the tile the piece is on.
 *
 * @return The tile where the piece is currently located.
 */
    public Tile getPosition() {
        return pos;
    }

/**
 * Sets a new position for the piece.
 *
 * Pre-condition:
 * - newPos must be a valid tile.
 *
 * Post-condition:
 * - The piece's position is updated to the new tile.
 *
 * @param newPos The tile to assign as the new position.
 */
    public void setPosition(Tile newPos) {
        this.pos = newPos;
    }
}

