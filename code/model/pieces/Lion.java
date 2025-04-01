package model.pieces;

import model.tiles.*;
import model.interfaces.Leaping;
import model.Game;

public class Lion extends Piece implements Leaping {

/**
 * Constructs a Lion piece with given position and owner.
 *
 * Pre-condition:
 * - pos must be a valid tile.
 * - owner must be a valid player name.
 *
 * Post-condition:
 * - The Lion is placed on the board and assigned to the owner.
 *
 * @param pos Starting tile.
 * @param owner Player who owns the Lion.
 */
    public Lion(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Returns the Lion's strength.
 *
 * @return 7
 */
    @Override
    public int getStrength() {
        return 7;
    }

/**
 * Determines if the Lion can capture the target piece.
 *
 * @param target The piece to capture.
 * @return true if valid capture, false otherwise.
 */
    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        if (target.getPosition() instanceof Trap &&
            ((Trap) target.getPosition()).getOwner().equals(this.owner)) {
            return true;
        }

        return this.getStrength() >= target.getStrength();
    }

/**
 * Checks if the Lion can move to the specified destination.
 *
 * Pre-condition:
 * - destination must not be null.
 *
 * Post-condition:
 * - Allows normal adjacent movement or valid lake-leap.
 *
 * @param destination The tile to move to.
 * @return true if move is valid.
 */
    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination instanceof Lake) return false;
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) return false;

        int currRow = pos.getRow();
        int currCol = pos.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();

        int dx = Math.abs(currRow - destRow);
        int dy = Math.abs(currCol - destCol);

        if (dx + dy == 1) {
            // Normal orthogonal move
            return !destination.isOccupied() || 
                    !destination.getCurrPiece().getOwner().equals(this.owner);
        }

        // Try leaping over lake
        return leap(destination) != null;
    }

/**
 * Attempts to leap over lake tiles to the destination.
 *
 * Pre-condition:
 * - destination must be across a lake and in same row or column.
 *
 * Post-condition:
 * - Returns destination if leap path is valid and not blocked.
 * - Returns null if leap is invalid.
 *
 * @param destination The target tile to leap to.
 * @return The destination if leap is successful, else null.
 */
    @Override
    public Tile leap(Tile destination) {
        int currRow = pos.getRow();
        int currCol = pos.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();

        // Horizontal leap
        if (currRow == destRow && Math.abs(destCol - currCol) > 1) {
            int dir = (destCol > currCol) ? 1 : -1;
            for (int c = currCol + dir; c != destCol; c += dir) {
                Tile tile = Game.getBoard().getTile(currRow, c);
                if (!(tile instanceof Lake) || 
                    (tile.isOccupied() && tile.getCurrPiece() instanceof Rat)) {
                    return null;
                }
            }
            return destination;
        }

        // Vertical leap
        if (currCol == destCol && Math.abs(destRow - currRow) > 1) {
            int dir = (destRow > currRow) ? 1 : -1;
            for (int r = currRow + dir; r != destRow; r += dir) {
                Tile tile = Game.getBoard().getTile(r, currCol);
                if (!(tile instanceof Lake) || (tile.isOccupied() && tile.getCurrPiece() instanceof Rat)) {
                    return null;
                }
            }
            return destination;
        }

        return null;
    }
}

