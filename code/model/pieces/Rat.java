package model.pieces;

import model.tiles.*;
import model.interfaces.Swimming;

public class Rat extends Piece implements Swimming {

/**
 * Constructs a Rat with position and owner.
 *
 * @param pos Starting tile.
 * @param owner Owner of the Rat.
 */
    public Rat(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Returns the Rat's strength.
 *
 * @return 1
 */
    @Override
    public int getStrength() {
        return 1;
    }

/**
 * Determines if the Rat can capture a target piece.
 *
 * Special rules:
 * - Can capture other rats in lake.
 * - Cannot capture elephants while in lake.
 *
 * @param target The piece to capture.
 * @return true if valid capture.
 */
    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        Tile myTile = this.getPosition();
        Tile theirTile = target.getPosition();

        // Can't capture anything from lake except another rat
        if (myTile instanceof Lake) {
            return target instanceof Rat && theirTile instanceof Lake;
        }

        // Rat can't capture elephant if it's in lake and elephant is on land
        if (target instanceof Elephant && theirTile instanceof Land) {
            return !(myTile instanceof Lake);
        }

        // Trap exception: anyone can capture enemies on traps
        if (theirTile instanceof Trap &&
            ((Trap) theirTile).getOwner().equals(this.owner)) {
            return true;
        }

        return this.getStrength() >= target.getStrength();
    }

/**
 * Checks if the Rat can move to the given destination.
 *
 * @param destination Tile to move to.
 * @return true if move is allowed.
 */
    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination.isOccupied() && destination.getCurrPiece().getOwner().equals(this.owner)) return false;
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) return false;

        int dx = Math.abs(pos.getRow() - destination.getRow());
        int dy = Math.abs(pos.getCol() - destination.getCol());

        return (dx + dy == 1);
    }

/**
 * Allows the Rat to swim into a lake tile.
 *
 * @param destination The target tile.
 * @return destination if it's a lake, else null.
 */
    @Override
    public Tile swim(Tile destination) 
    {
    if (destination instanceof Lake) {
        return destination;
    } else {
        return null;
    }
    }
}
