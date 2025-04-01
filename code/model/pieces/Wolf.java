package model.pieces;

import model.tiles.Tile;
import model.tiles.HomeBase;

public class Wolf extends Piece {
    public Wolf(Tile pos, String owner) {
        super(pos, owner);
    }

    @Override
    public boolean canCapture(Piece piece) {
        return this.getStrength() >= piece.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) {
        // Standard movement rules
        // Cannot move into own home base
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.getOwner())) {
            return false;
        }
        return true;
    }

    @Override
    public int getStrength() {
        return 4;
    }
}
