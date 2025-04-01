package model.tiles;

import model.pieces.Piece;

public abstract class Tile {
    protected String tilePos;
    protected int row;
    protected int col;
    protected Piece currPiece;
    private String type;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.tilePos = row + "" + col;
        this.currPiece = null;
    }

    public String getTilePos() {
        return tilePos;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupied() {
        return currPiece != null;
    }

    public Piece getCurrPiece() {
        return currPiece;
    }

    public boolean setCurrPiece(Piece piece) {
        this.currPiece = piece;
        return true;
    }

    public void removePiece() {
        this.currPiece = null;
    }

    public abstract void render();

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
