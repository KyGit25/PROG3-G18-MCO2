package model.pieces;

import model.tiles.*;
import model.interfaces.Swimming;

public class Rat extends Piece implements Swimming {

    public Rat(Tile pos, String owner) {
        super(pos, owner);
    }

    @Override
    public int getStrength() {
        return 1;
    }

    @Override
    public boolean canCapture(Piece target) {
        if (target == null || target.getOwner().equals(this.owner)) return false;

        Tile myTile = this.getPosition();
        Tile theirTile = target.getPosition();

        // Can't capture anything from lake except another rat
        if (myTile instanceof Lake) {
            return target instanceof Rat && theirTile instanceof Lake;
        }

        // Rat can't capture elephant if it's in lake and elephant is on land
        if (target instanceof Elephant && theirTile instanceof Land) {
            return !(myTile instanceof Lake);
        }

        // Trap exception: anyone can capture enemies on traps
        if (theirTile instanceof Trap &&
            !((Trap) theirTile).getOwner().equals(this.owner)) {
            return true;
        }

        return this.getStrength() >= target.getStrength();
    }

    @Override
    public boolean canMove(Tile destination) 
    {
        if (destination.isOccupied() && destination.getCurrPiece().getOwner().equals(this.owner)) return false;
        if (destination instanceof HomeBase && ((HomeBase) destination).getOwner().equals(this.owner)) return false;

        int dx = Math.abs(pos.getRow() - destination.getRow());
        int dy = Math.abs(pos.getCol() - destination.getCol());

        return (dx + dy == 1);
    }

    @Override
    public Tile swim(Tile destination) {
        return (destination instanceof Lake) ? destination : null;
    }
}
