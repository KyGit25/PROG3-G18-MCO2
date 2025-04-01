import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * Each player has a name and a collection of pieces.
 */
public class Player 
{
    private String name;
    private List<Piece> pieces;

    /**
     * Constructs a new player with the specified name.
     * 
     * @param name The name of the player.
     * 
     * Pre-condition: The name must be a valid string.
     * Post-condition: A player object is created with an empty list of pieces.
     */
    public Player(String name) 
    {
        this.name = name;
        this.pieces = new ArrayList<>();
    }

    /**
     * Adds a piece to the player's collection.
     * 
     * @param piece The piece to be added.
     * 
     * Pre-condition: The piece must be a valid Piece object.
     * Post-condition: The piece is added to the player's list of pieces.
     */
    public void addPiece(Piece piece) 
    {
        pieces.add(piece);
    }

    /**
     * Retrieves an active piece of the specified type.
     * 
     * @param pieceType The type of piece to retrieve.
     * @return The piece if found and not captured, otherwise null.
     * 
     * Pre-condition: The pieceType must be a valid string.
     * Post-condition: Returns a matching piece if available, otherwise prints a message and returns null.
     */
    public Piece getPiece(String pieceType) 
    {
        for (Piece piece : pieces) 
        {
            if (piece.getName().equalsIgnoreCase(pieceType) && !piece.isCaptured()) 
            {
                return piece;
            }
        }
        System.out.println("\nPiece not found or has been captured! It cannot be moved.");
        return null;
    }

    /**
     * Retrieves all pieces owned by the player.
     * 
     * @return A list of all pieces belonging to the player.
     * 
     * Pre-condition: The player must have a list of pieces (can be empty).
     * Post-condition: Returns the list of pieces owned by the player.
     */
    public List<Piece> getAllPieces() 
    {
        return pieces;
    }

    /**
     * Retrieves all active (not captured) pieces owned by the player.
     * 
     * @return A list of active pieces.
     * 
     * Pre-condition: The player must have a list of pieces (can be empty).
     * Post-condition: Returns a list containing only the pieces that are not captured.
     */
    public List<Piece> getActivePieces() 
    {
        List<Piece> activePieces = new ArrayList<>();
        for (Piece piece : pieces) 
        {
            if (!piece.isCaptured()) 
            {
                activePieces.add(piece);
            }
        }
        return activePieces;
    }

    /**
     * Moves a piece in the specified direction on the board.
     * 
     * @param piece The piece to move.
     * @param direction The direction to move ('W' for up, 'S' for down, 'A' for left, 'D' for right).
     * @param board The game board.
     * 
     * Pre-condition: The piece must exist and belong to the player. The direction must be valid (W, A, S, or D).
     * The board must be a valid Board object.
     * Post-condition: The piece moves if the move is valid. If an opponent's piece is in the target position
     * and can be captured, it is removed. If the piece reaches the opponent's home base, a message is displayed.
     */
    public void movePiece(Piece piece, char direction, Board board) 
    {
        // TEMP: commented out to test views and initial board
        /*
        int newRow = piece.getRow();
        int newCol = piece.getCol();
    
        if (direction == 'W') {
            newRow--;
        } else if (direction == 'S') {
            newRow++;
        } else if (direction == 'A') {
            newCol--;
        } else if (direction == 'D') {
            newCol++;
        } else {
            System.out.println("Invalid direction! Use W, A, S, or D.\n");
            return;
        }
    
        if (piece.canMove(newRow, newCol, board)) {
            Piece opponent = board.getPieceAt(newRow, newCol);
            
            if (opponent != null && opponent.getOwner().equals(piece.getOwner()) && board.isTrap(newRow, newCol)) {
                System.out.println("Invalid move.\n");
                return;
            }

            if (opponent != null && board.isOpponentPiece(piece, newRow, newCol)) {
                if (piece.canCapture(opponent, board)) {
                    System.out.println(piece.getName() + " captured " + opponent.getName() + "!");
                    board.removePiece(opponent);
                } else {
                    System.out.println("Capture not allowed. Try another move.\n");
                    return;
                }
            }
            
            board.updateBoard(piece, newRow, newCol);

            if (board.isHomeBase(newRow, newCol, piece.getOwner().equals("Blue") ? "Green" : "Blue")) {
                System.out.println("\n" + piece.getOwner() + " has captured the opponent's home base!");
            }
        } else {
            System.out.println("Invalid move. Try again.\n");
        }
        */
    }

    /**
     * Checks if the player has lost all their pieces.
     * 
     * @return true if all pieces are captured, false otherwise.
     * 
     * Pre-condition: The player must have a list of pieces (can be empty).
     * Post-condition: Returns true if no active pieces remain, otherwise returns false.
     */
    public boolean hasLostAllPieces() 
    {
        for (Piece piece : pieces) 
        {
            if (!piece.isCaptured()) 
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the name of the player.
     * 
     * @return The player's name.
     * 
     * Pre-condition: The player's name must be initialized.
     * Post-condition: Returns the name of the player.
     */
    public String getName() 
    {
        return name;
    }
}
