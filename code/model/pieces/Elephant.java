package model.pieces;

import model.tiles.*;

public class Elephant extends Piece {

    public Elephant(Tile pos, String owner) {
        super(pos, owner);
    }

    @Override
    public int getStrength() {
        return 8;
    }

    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        // Trap rule: if target is in trap, it can be captured by anyone
        if (target.getPosition() instanceof Trap &&
            ((Trap) target.getPosition()).getOwner().equals(this.owner)) {
            return true;
        }

        // Rat exception: elephant cannot capture rat in lake
        if (target instanceof Rat && target.getPosition() instanceof Lake) return false;

        return this.getStrength() >= target.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination instanceof Lake) return false;
        if (destination.isOccupied() && destination.getCurrPiece().getOwner().equals(this.owner)) return false;
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) return false;

        int dx = Math.abs(pos.getRow() - destination.getRow());
        int dy = Math.abs(pos.getCol() - destination.getCol());

        return (dx + dy == 1); // move 1 tile orthogonally
    }
}

