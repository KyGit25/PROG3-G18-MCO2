public abstract class Tile{
	protected String tilePosition;
	protected int row;
	protected int col;
	protected Piece currentPiece;
	protected String owner;

	public Tile(int row, int col){
		this.row = row;
		this.col = col;
		this.tilePosition = row + "" + col;
		this.currentPiece = null;
		this.owner = owner;
	}

	public abstract void render();

	public String getTilePosition(){
		return this.tilePosition;
	}

	public int getRow(){
		return this.row;
	}

	public int getCol(){
		return this.col;
	}

	public Piece getCurrentPiece(){
		return this.currentPiece;
	}

	public boolean setCurrentPiece(Piece piece){
		if(this.currentPiece == null){
			this.currentPiece = piece;
			return true;
		}
		return false;
	}

	public boolean isOccupied(){
		return this.currentPiece != null;
	}

	protected abstract String getOwner();
}
