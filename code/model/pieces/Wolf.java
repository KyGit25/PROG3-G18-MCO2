package model.pieces;

import model.tiles.*;

public class Wolf extends Piece {

/**
 * Constructs a Wolf piece.
 *
 * @param pos Starting tile.
 * @param owner Owner of the piece.
 */
    public Wolf(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Gets the strength of the Wolf.
 *
 * @return 4
 */
    @Override
    public int getStrength() {
        return 4;
    }

/**
 * Determines if the Wolf can capture a target piece.
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
 * Checks if the Wolf can move to the destination.
 *
 * @param destination Tile to move to.
 * @return true if move is valid.
 */
    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination instanceof Lake) return false;
        if (destination.isOccupied() && destination.getCurrPiece().getOwner().equals(this.owner)) return false;
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) return false;

        int dx = Math.abs(pos.getRow() - destination.getRow());
        int dy = Math.abs(pos.getCol() - destination.getCol());

        return (dx + dy == 1);
    }
}
