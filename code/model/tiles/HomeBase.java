package model.tiles;

public class HomeBase extends Tile {
    private String owner;

    public HomeBase(int row, int col, String owner) {
        super(row, col);
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
