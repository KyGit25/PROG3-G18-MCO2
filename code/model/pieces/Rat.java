package model.pieces;

import model.interfaces.Swimming;
import model.tiles.Lake;
import model.tiles.Tile;
import model.tiles.HomeBase;

public class Rat extends Piece implements Swimming {
    public Rat(Tile pos) {
        super(pos);
    }

    @Override
    public boolean canCapture(Piece piece) {
        // Rat can capture elephant and other rats
        if (piece instanceof Elephant) return true;
        return this.getStrength() >= piece.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) {
        // Can move on land or swim in lakes
        // Cannot move into own home base
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.getOwner())) {
            return false;
        }
        return true;
    }

    @Override
    public int getStrength() {
        return 1;
    }

    @Override
    public Tile swim(Tile destination) {
        // Implementation for swimming in lakes
        // Can only move one square at a time in lakes
        return destination;
    }
}
