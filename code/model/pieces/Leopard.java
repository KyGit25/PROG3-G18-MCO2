package model.pieces;

import model.tiles.*;

public class Leopard extends Piece {

/**
 * Constructs a Leopard with starting position and owner.
 *
 * @param pos Starting tile.
 * @param owner Owner's name.
 */
    public Leopard(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Gets Leopard's strength.
 *
 * @return 5.
 */
    @Override
    public int getStrength() {
        return 5;
    }

/**
 * Determines if Leopard can capture the target piece.
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
 * Checks if the Leopard can move to the destination.
 *
 * @param destination The target tile.
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
