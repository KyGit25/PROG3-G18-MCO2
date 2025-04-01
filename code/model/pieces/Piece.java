package model.pieces;

import model.tiles.Tile;

public abstract class Piece {
    protected Tile pos;
    protected boolean isCaptured;

    public Piece(Tile pos) {
        this.pos = pos;
        this.isCaptured = false;
        if (pos != null) {
            pos.setCurrPiece(this);
        }
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public abstract boolean canCapture(Piece piece);

    public abstract boolean canMove(Tile destination);

    public abstract int getStrength();

    public Tile getPosition() {
        return pos;
    }

    public void setPosition(Tile newPos) {
        this.pos = newPos;
    }
}

