package model.pieces;

import model.tiles.Tile;
import model.tiles.HomeBase;

public class Elephant extends Piece {
    public Elephant(Tile pos, String owner) {
        super(pos, owner);
    }

    @Override
    public boolean canCapture(Piece opponent) {
        // Elephant can't capture rat (special rule)
        if (opponent instanceof Rat) return false;
        return this.getStrength() >= opponent.getStrength();
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
        return 8; // Strongest piece
    }
}
