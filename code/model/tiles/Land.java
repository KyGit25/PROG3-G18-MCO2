public class Land extends Tile {
    
    public Land(int row, int col) {
        super(row, col);
    }
    
    @Override
    public void render() {
       if(this.currentPiece != null){
        // TODO
       }else{
        System.out.print(" ");
       }
    }
    
    @Override
    protected String getOwner() {
        return null; // Land tiles don't have an owner
    }
}