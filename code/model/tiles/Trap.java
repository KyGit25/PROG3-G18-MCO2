package model.tiles;

public class Trap extends Tile {
    private String owner;

    public Trap(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public void render() 
    {
        // Render logic customize in view
    }
}
