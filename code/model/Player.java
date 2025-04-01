package model;

import model.pieces.Piece;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private String name;
    private List<Piece> pieces;

    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public Piece getPiece(String pieceType) {
        for (Piece piece : pieces) {
            if (piece.getClass().getSimpleName().equalsIgnoreCase(pieceType) && !piece.isCaptured()) {
                return piece;
            }
        }
        return null;
    }

    public List<Piece> getAllPieces() {
        return pieces;
    }

    public List<Piece> getActivePieces() {
        List<Piece> activePieces = new ArrayList<>();
        for (Piece piece : pieces) {
            if (!piece.isCaptured()) {
                activePieces.add(piece);
            }
        }
        return activePieces;
    }

    public boolean hasLostAllPieces() {
        for (Piece piece : pieces) {
            if (!piece.isCaptured()) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }
}
