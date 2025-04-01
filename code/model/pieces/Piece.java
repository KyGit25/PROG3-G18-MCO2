/**
 * The Piece class is an abstract class that represents a piece in the game.
 * It contains the common properties and methods for all pieces.
 * */
public abstract class Piece {
    /**
     * The name of the piece.
     */
    protected String name;
    
    /**
     * The symbol representation of the piece on the board.
     */
    protected String symbol;
    
    /**
     * The owner of the piece ("Blue" or "Green").
     */
    protected String owner;
    
    /**
     * The row position of the piece on the board.
     */
    protected int row;
    
    /**
     * The column position of the piece on the board.
     */
    protected int col;
    
    /**
     * Indicates whether the piece is trapped.
     */
    protected boolean isTrapped;
    
    /**
     * Indicates whether the piece is in a lake.
     */
    protected boolean inLake;
    
    /**
     * Indicates whether the piece has been captured.
     */
    protected boolean isCaptured;
    
    /**
     * The strength value of the piece.
     */
    protected int strength;

    /**
     * Constructs a Piece object with the given attributes.
     * 
     * @param name     The name of the piece.
     * @param row      The row position of the piece.
     * @param col      The column position of the piece.
     * @param symbol   The symbol representing the piece.
     * @param owner    The owner of the piece.
     * @param strength The strength of the piece.
     */
    public Piece(String name, int row, int col, String symbol, String owner, int strength) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.symbol = symbol;
        this.owner = owner;
        this.isTrapped = false;
        this.inLake = false;
        this.isCaptured = false;
        this.strength = strength;
    }

    /**
     * Abstract method to check if a piece can move to a new position.
     * 
     * @param newRow The new row position.
     * @param newCol The new column position.
     * @param board  The game board.
     * 
     * Pre-condition: The new row and column must be within the board grid.
     * Post-condition: The piece's position is updated to the new row and column.
     * 
     * @return true if the piece can move to the new position, false otherwise.
     */
    public abstract boolean canMove(int newRow, int newCol, Board board);

    /**
     * Moves the piece to a new position.
     *  
     * Pre-condition: The new row and column must be within the board grid.
     * Post-condition: The piece's position is updated to the new row and column.
     * 
     * @param newRow The new row position.
     * @param newCol The new column position.
     */
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    /**
     * Checks if the piece can capture another piece.
     * 
     * @param opponent The piece to capture.
     * @param board    The game board.
     * 
     * Pre-condition: The opponent piece must be a valid Piece object.
     * Post-condition: The piece's position is updated to the new row and column.
     * 
     * @return true if the piece can capture the opponent piece, false otherwise.
     */
    public boolean canCapture(Piece opponent, Board board) {
        // TEMP: commented out to test views and initial board
        /*
        if (opponent == null) {
            return false;
        }

        if (this.owner.equals(opponent.owner)) {
            System.out.println("Cannot capture own piece.\n");
            return false; // Cannot capture own pieces
        }
        
        // Check if opponent is in a trap
        if (board.isTrap(opponent.getRow(), opponent.getCol())) {
            String trapOwner = board.getTrapOwner(opponent.getRow(), opponent.getCol());
            if (trapOwner != null && trapOwner.equals(this.owner)) {
                return true; // capture opponent in owned trap
            }
        }
        
        // Lake rules
        if (this.inLake && !opponent.inLake) {
            return false; // lake piece CANT capture land piece
        }
        
        if (!this.inLake && opponent.inLake) {
            return false; // land piece CANT capture lake piece
        }
        
        return this.strength >= opponent.strength;
        */
       return this.strength >= opponent.strength;
    }

    /**
     * Gets the piece's owner.
     * 
     * @return The owner of the piece.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets the piece's strength.
     * 
     * @return The strength of the piece.
     */ 
    public int getStrength() {
        return strength;
    }

    /**
     * Gets the piece's symbol.
     * 
     * @return The symbol of the piece.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the piece's name.
     * 
     * @return The name of the piece.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the piece's row position.
     * 
     * Pre-condition: The piece must be on the board.
     * @return The row position of the piece.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the piece's column position.
     * 
     * @return The column position of the piece.
     */
    public int getCol() {
        return col;
    }

    /**
     * Checks if the piece is trapped.
     * 
     * @return True if the piece is trapped, false otherwise.
     */
    public boolean isTrapped() {
        return isTrapped;
    }
    
    /**
     * Checks if the piece is in a lake.
     * 
     * @return True if the piece is in a lake, false otherwise.
     */
    public boolean isInLake() {
        return inLake;
    }
    
    /**
     * Checks if the piece is captured.
     * 
     * @return True if the piece is captured, false otherwise.
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Sets the trapped status of the piece.
     * 
     * @param trapped The new trapped status.
     */
    public void setTrapped(boolean trapped) {
        this.isTrapped = trapped;
    }

    /**
     * Sets the in lake status of the piece.
     * 
     * @param inLake The new in lake status.
     */
    public void setInLake(boolean inLake) {
        this.inLake = inLake;
    }
    
    /**
     * Sets the captured status of the piece.
     * 
     * @param captured The new captured status.
     */
    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }
}