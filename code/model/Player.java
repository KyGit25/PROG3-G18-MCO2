package model;

import model.pieces.Piece;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private String name;
    private List<Piece> pieces;

/**
 * Constructs a new Player with a given name.
 *
 * Pre-condition:
 * - name must not be null.
 *
 * Post-condition:
 * - A Player object is created with the specified name.
 * - The player's list of pieces is initialized as empty.
 *
 * @param name The name or color representing the player.
 */
    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<>();
    }

/**
 * Adds a piece to the player's collection of pieces.
 *
 * Pre-condition:
 * - piece must not be null.
 *
 * Post-condition:
 * - The piece is added to the player's internal list of pieces.
 *
 * @param piece The Piece to be added.
 */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

/**
 * Retrieves an active (not captured) piece by its type name.
 *
 * Pre-condition:
 * - pieceType must match one of the valid animal piece class names.
 *
 * Post-condition:
 * - Returns the first active piece of the specified type owned by the player.
 * - Returns null if the piece is not found or has been captured.
 *
 * @param pieceType The name of the piece type to retrieve (e.g., "Tiger").
 * @return The matching Piece, or null if not available.
 */
    public Piece getPiece(String pieceType) {
        for (Piece piece : pieces) {
            if (piece.getClass().getSimpleName().equalsIgnoreCase(pieceType) && !piece.isCaptured()) {
                return piece;
            }
        }
        return null;
    }

/**
 * Retrieves all pieces owned by the player, including captured ones.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns a list of all Piece objects owned by the player.
 *
 * @return List of all pieces.
 */
    public List<Piece> getAllPieces() {
        return pieces;
    }

/**
 * Retrieves all active (not captured) pieces owned by the player.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns a list of only those pieces that have not been captured.
 *
 * @return List of active (uncaptured) pieces.
 */
    public List<Piece> getActivePieces() {
        List<Piece> activePieces = new ArrayList<>();
        for (Piece piece : pieces) {
            if (!piece.isCaptured()) {
                activePieces.add(piece);
            }
        }
        return activePieces;
    }


/**
 * Checks whether the player has lost all pieces.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns true if all of the player's pieces are captured.
 * - Returns false if at least one piece is still active.
 *
 * @return true if player has no remaining active pieces, false otherwise.
 */
    public boolean hasLostAllPieces() {
        for (Piece piece : pieces) {
            if (!piece.isCaptured()) {
                return false;
            }
        }
        return true;
    }

/**
 * Retrieves the name of the player.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the name assigned to this player.
 *
 * @return The player's name.
 */
    public String getName() {
        return name;
    }
}
