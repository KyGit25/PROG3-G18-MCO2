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
        // Standard orthogonal movement (1 square)
        int rowDiff = Math.abs(destination.getRow() - pos.getRow());
        int colDiff = Math.abs(destination.getCol() - pos.getCol());
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }

    @Override
    public int getStrength() {
        return 8; // Strongest piece
    }
}
