package model.pieces;

import model.tiles.Tile;
import model.tiles.HomeBase;

public class Elephant extends Piece {
    public Elephant(Tile pos) {
        super(pos);
    }

    @Override
    public boolean canCapture(Piece piece) {
        if (piece instanceof Rat) return false;
        return this.getStrength() >= piece.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) {

        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.getOwner())) {
            return false;
        }
        return true;
    }

    @Override
    public int getStrength() {
        return 8;
    }
}
