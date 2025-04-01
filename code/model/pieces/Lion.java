package model.pieces;

import model.interfaces.Leaping;
import model.tiles.Lake;
import model.tiles.Tile;
import model.tiles.HomeBase;

public class Lion extends Piece implements Leaping {
    public Lion(Tile pos) {
        super(pos);
    }

    @Override
    public boolean canCapture(Piece piece) {
        return this.getStrength() >= piece.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) {
        // Standard movement or leaping over lakes
        // Cannot move into own home base
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.getOwner())) {
            return false;
        }
        return true;
    }

    @Override
    public int getStrength() {
        return 7;
    }

    @Override
    public Tile leap(Tile destination) {
        // Implementation for leaping over lakes
        // Check if destination is valid for leaping
        // Check if rat is blocking the path
        return destination;
    }
}
