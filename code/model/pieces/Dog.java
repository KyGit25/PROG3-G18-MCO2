package model.pieces;

import model.tiles.*;

public class Dog extends Piece {

/**
 * Constructs a Dog piece with a specified starting tile and owner.
 *
 * Pre-condition:
 * - pos and owner must be valid.
 *
 * Post-condition:
 * - Dog is placed and owned by the given player.
 *
 * @param pos The tile for initial position.
 * @param owner The player owning the Dog.
 */
    public Dog(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Gets the strength of the Dog.
 *
 * @return The strength value: 3.
 */
    @Override
    public int getStrength() {
        return 3;
    }

/**
 * Determines if the Dog can capture the given piece.
 *
 * @param target The piece to capture.
 * @return true if capture is valid.
 */
    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        if (target.getPosition() instanceof Trap &&
            ((Trap) target.getPosition()).getOwner().equals(this.owner)) {
            return true;
        }

        return this.getStrength() >= target.getStrength();
    }

/**
 * Checks if the Dog can move to the destination tile.
 *
 * @param destination The tile to move to.
 * @return true if move is valid.
 */
    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) 
        {
            return false;
        }
        if (destination instanceof Lake) 
        {
            return false;
        }
        if (destination.isOccupied() && destination.getCurrPiece().getOwner().equals(this.owner)) 
        {
            return false;
        }
        int dx = Math.abs(pos.getRow() - destination.getRow());
        int dy = Math.abs(pos.getCol() - destination.getCol());

        return (dx + dy == 1);
    }
}
