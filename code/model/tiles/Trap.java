package model.tiles;

public class Trap extends Tile {
    private String owner; // "Blue" or "Green"

    public Trap(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

    @Override
    public void render() {
        // Rendering logic for trap tile
    }

    public String getOwner() {
        return owner;
    }
}
