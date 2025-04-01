package model.pieces;

import model.tiles.*;

public class Cat extends Piece {

/**
 * Constructs a Cat piece with a specified starting tile and owner.
 *
 * Pre-condition:
 * - pos must be a valid tile.
 * - owner must be a valid player name.
 *
 * Post-condition:
 * - Cat is placed on the specified tile and assigned to the player.
 *
 * @param pos The tile where the Cat starts.
 * @param owner The owner of the Cat.
 */
    public Cat(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Gets the strength of the Cat.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns 2.
 *
 * @return The Cat's strength.
 */
    @Override
    public int getStrength() {
        return 2;
    }

/**
 * Determines if the Cat can capture the specified target piece.
 *
 * Pre-condition:
 * - target may be null or an enemy piece.
 *
 * Post-condition:
 * - Returns true if the Cat can capture the target based on strength or trap rules.
 *
 * @param target The piece to be captured.
 * @return true if capture is allowed; false otherwise.
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
 * Determines if the Cat can move to the specified tile.
 *
 * Pre-condition:
 * - destination must not be null.
 *
 * Post-condition:
 * - Returns true if the move is orthogonal by one tile and doesn't violate tile restrictions.
 *
 * @param destination The tile to move to.
 * @return true if move is valid; false otherwise.
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
