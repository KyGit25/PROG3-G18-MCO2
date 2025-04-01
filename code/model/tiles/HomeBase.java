package model.tiles;

public class HomeBase extends Tile {
    private String owner; // "Blue" or "Green"

    public HomeBase(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

    @Override
    public void render() {
        // Rendering logic for home base tile
    }

    public String getOwner() 
    {
        return owner;
    }
}
