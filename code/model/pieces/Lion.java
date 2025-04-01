package model.pieces;

import model.tiles.*;
import model.interfaces.Leaping;

public class Lion extends Piece implements Leaping {

    public Lion(Tile pos) {
        super(pos);
    }

    @Override
    public int getStrength() {
        return 7;
    }

    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        if (target.getPosition() instanceof Trap &&
            !((Trap) target.getPosition()).getOwner().equals(this.owner)) {
            return true;
        }

        return this.getStrength() >= target.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) 
        {
            return false;
        }
        if (destination instanceof Lake) return false;

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
                Tile tile = destination.getBoard().getTile(currRow, c);
                if (!(tile instanceof Lake) || tile.isOccupied() && tile.getCurrPiece() instanceof Rat) return null;
            }
            return destination;
        }

        // Vertical leap
        if (currCol == destCol && Math.abs(destRow - currRow) > 1) {
            int dir = (destRow > currRow) ? 1 : -1;
            for (int r = currRow + dir; r != destRow; r += dir) {
                Tile tile = destination.getBoard().getTile(r, currCol);
                if (!(tile instanceof Lake) || tile.isOccupied() && tile.getCurrPiece() instanceof Rat) return null;
            }
            return destination;
        }

        return null;
    }
}

