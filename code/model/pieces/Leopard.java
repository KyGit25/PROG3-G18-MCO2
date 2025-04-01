package model.pieces;

import model.tiles.*;

public class Leopard extends Piece {

    public Leopard(Tile pos) {
        super(pos);
    }

    @Override
    public int getStrength() {
        return 5;
    }

    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        if (target.getPosition() instanceof Trap &&
            !((Trap) target.getPosition()).getOwner().equals(this.owner)) {
            return true;
        }

        return this.getStrength() >= target.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) {
        if (destination instanceof Lake) return false;
        if (destination.isOccupied() && destination.getCurrPiece().getOwner().equals(this.owner)) return false;

        int dx = Math.abs(pos.getRow() - destination.getRow());
        int dy = Math.abs(pos.getCol() - destination.getCol());

        return (dx + dy == 1);
    }
}
