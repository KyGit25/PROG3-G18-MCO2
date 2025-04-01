package model.pieces;

import model.tiles.*;
import model.interfaces.Leaping;
import model.Game;

public class Tiger extends Piece implements Leaping {

/**
 * Constructs a Tiger piece.
 *
 * @param pos Initial position.
 * @param owner Owner of the Tiger.
 */
    public Tiger(Tile pos, String owner) {
        super(pos, owner);
    }

/**
 * Returns the Tiger's strength.
 *
 * @return 6
 */
    @Override
    public int getStrength() {
        return 6;
    }

/**
 * Determines if the Tiger can capture the target.
 *
 * @param target The enemy piece.
 * @return true if capture is allowed.
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
 * Checks if the Tiger can move to the destination.
 *
 * - Allows leap over lake or normal 1-tile move.
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
            return !destination.isOccupied() ||
                   !destination.getCurrPiece().getOwner().equals(this.owner);
        }

        return leap(destination) != null;
    }

/**
 * Attempts to leap over lake tiles to the destination.
 *
 * @param destination Target tile.
 * @return destination if leap is valid, else null.
 */
    @Override
    public Tile leap(Tile destination) {
        int currRow = pos.getRow();
        int currCol = pos.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();

        // Horizontal leap
        if (currRow == destRow && Math.abs(destCol - currCol) > 1) 
        {    
            int dir;
            if (destCol > currCol) {
                dir = 1;
            } else {
                dir = -1;
            }

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
        if (currCol == destCol && Math.abs(destRow - currRow) > 1) 
        {
            int dir;
            if (destRow > currRow) {
                dir = 1;
            } else {
                dir = -1;
            }

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
