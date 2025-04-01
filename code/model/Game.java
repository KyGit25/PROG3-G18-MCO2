package model;

import model.Board;
import model.pieces.*;
import model.tiles.*;
import model.interfaces.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameState gameState;

/**
 * Constructs a new Game with two players, a board, game state, and initializes all pieces.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Game setup is complete with board and both players having their pieces placed.
 */
    public Game() {
        board = new Board();
        this.player1 = new Player("Blue");
        this.player2 = new Player("Green");
        this.gameState = new GameState();
        initializePieces();
    }
    
/**
 * Initializes and places all animal pieces for both players on the board.
 *
 * Pre-condition:
 * - Board and players must already be initialized.
 *
 * Post-condition:
 * - All pieces are added to their respective players and positioned on the board.
 */
    public void initializePieces() {
        // Blue pieces based on the provided positions
        Tiger blueTiger = new Tiger(board.getTile(0, 0), "Blue");
        player1.addPiece(blueTiger);
        
        Cat blueCat = new Cat(board.getTile(1, 1), "Blue");
        player1.addPiece(blueCat);
        
        Elephant blueElephant = new Elephant(board.getTile(0, 2), "Blue");
        player1.addPiece(blueElephant);
        
        Wolf blueWolf = new Wolf(board.getTile(2, 2), "Blue");
        player1.addPiece(blueWolf);
        
        Leopard blueLeopard = new Leopard(board.getTile(4, 2), "Blue");
        player1.addPiece(blueLeopard);
        
        Dog blueDog = new Dog(board.getTile(5, 1), "Blue");
        player1.addPiece(blueDog);
        
        Lion blueLion = new Lion(board.getTile(6, 0), "Blue");
        player1.addPiece(blueLion);
        
        Rat blueRat = new Rat(board.getTile(6, 2), "Blue");
        player1.addPiece(blueRat);

        // Green pieces based on the provided positions
        Tiger greenTiger = new Tiger(board.getTile(6, 8), "Green");
        player2.addPiece(greenTiger);
        
        Cat greenCat = new Cat(board.getTile(5, 7), "Green");
        player2.addPiece(greenCat);
        
        Elephant greenElephant = new Elephant(board.getTile(6, 6), "Green");
        player2.addPiece(greenElephant);
        
        Wolf greenWolf = new Wolf(board.getTile(4, 6), "Green");
        player2.addPiece(greenWolf);
        
        Leopard greenLeopard = new Leopard(board.getTile(2, 6), "Green");
        player2.addPiece(greenLeopard);
        
        Dog greenDog = new Dog(board.getTile(1, 7), "Green");
        player2.addPiece(greenDog);
        
        Lion greenLion = new Lion(board.getTile(0, 8), "Green");
        player2.addPiece(greenLion);
        
        Rat greenRat = new Rat(board.getTile(0, 6), "Green");
        player2.addPiece(greenRat);
    }

/**
 * Returns a shuffled list of piece names.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns a list containing all 8 animal types in random order.
 *
 * @return A shuffled List of piece names.
 */
    public List<String> getShuffledPieces() {
        List<String> pieces = new ArrayList<>();
        pieces.add("Elephant");
        pieces.add("Lion");
        pieces.add("Tiger");
        pieces.add("Leopard");
        pieces.add("Wolf");
        pieces.add("Dog");
        pieces.add("Cat");
        pieces.add("Rat");
        
        Random rand = new Random();
        for (int i = pieces.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String temp = pieces.get(i);
            pieces.set(i, pieces.get(j));
            pieces.set(j, temp);
        }
        return pieces;
    }

/**
 * Determines which player goes first based on chosen piece strengths.
 *
 * Pre-condition:
 * - Each player selects a valid piece name from the set of defined animals.
 *
 * Post-condition:
 * - The currentPlayer is set to the player with the stronger piece.
 *
 * @param player1Choice The piece name selected by player 1.
 * @param player2Choice The piece name selected by player 2.
 * @return The Player who will start the game.
 */
    public Player determineFirstPlayer(String player1Choice, String player2Choice) {
        int p1Strength = getPieceStrength(player1Choice);
        int p2Strength = getPieceStrength(player2Choice);
        
    if (p1Strength > p2Strength) {
        currentPlayer = player1;
    } else {
        currentPlayer = player2;
    }
        return currentPlayer;
    }

/**
 * Returns the strength value of a given piece name.
 *
 * Pre-condition:
 * - pieceName must match a valid animal name (case-sensitive).
 *
 * Post-condition:
 * - Returns an integer from 1 to 8 representing the piece's strength.
 * - Returns 0 if the name is invalid.
 *
 * @param pieceName The name of the animal piece.
 * @return The strength value of the piece.
 */
    private int getPieceStrength(String pieceName) {
        switch (pieceName) {
            case "Elephant": return 8;
            case "Lion": return 7;
            case "Tiger": return 6;
            case "Leopard": return 5;
            case "Wolf": return 4;
            case "Dog": return 3;
            case "Cat": return 2;
            case "Rat": return 1;
            default: return 0;
        }
    }

/**
 * Switches the turn to the other player.
 *
 * Pre-condition:
 * - currentPlayer must not be null.
 *
 * Post-condition:
 * - currentPlayer is updated to the other player.
 * - If the other player has no active pieces, switch back to the original player.
 */
    public void switchTurn() 
    {
    if (currentPlayer == player1) {
        currentPlayer = player2;
    } else {
        currentPlayer = player1;
    }

    if (currentPlayer.hasLostAllPieces()) {
        // Forfeit turn if no pieces
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
    }

/**
 * Attempts to move a piece to the specified destination tile.
 *
 * Pre-condition:
 * - piece and destination are not null.
 * - piece must belong to the current player and have a valid move.
 *
 * Post-condition:
 * - Piece is moved if the move is valid.
 * - Capture is resolved if applicable.
 * - Game state is updated if the move results in a win.
 *
 * @param piece The piece to be moved.
 * @param destination The tile to move the piece to.
 * @return true if the move was valid and performed; false otherwise.
 * 
 */
    public boolean movePiece(Piece piece, Tile destination) {
        if (piece == null || destination == null) return false;
        
        // Get current position
        Tile currentPos = piece.getPosition();
        
        // Check if move is valid
        if (!piece.canMove(destination)) {
            // Check specific invalid move cases
            if (destination instanceof Lake) {
                throw new RuntimeException(piece.getClass().getSimpleName() + " cannot swim in lakes!");
            }
            
            // For Lions and Tigers attempting to leap
            if ((piece instanceof Lion || piece instanceof Tiger) && 
                Math.abs(destination.getRow() - currentPos.getRow()) + 
                Math.abs(destination.getCol() - currentPos.getCol()) > 1) {
                throw new RuntimeException(piece.getClass().getSimpleName() + " cannot leap over lake - path is blocked!");
            }
            
            throw new RuntimeException("Invalid move!");
        }

        // Handle capture
        if (destination.isOccupied()) {
            Piece target = destination.getCurrPiece();
            
            // Check for friendly piece
            if (target.getOwner().equals(piece.getOwner())) {
                throw new RuntimeException("Cannot capture your own piece!");
            }
            
            if (target.getPosition() instanceof Lake && !(piece instanceof Rat)) { // target is on lake and piece is not a rat
                throw new RuntimeException("Only rats can capture pieces on the lake!");
            }
            
            if (piece.getPosition() instanceof Lake && piece instanceof Rat) { // piece is a rat and on lake
                if (!(target instanceof Rat && target.getPosition() instanceof Lake)) { // target is not a rat or not on lake
                    throw new RuntimeException("Rat in lake can only capture other rats in lake!");
                }
            }
            
            if (piece.canCapture(target)) {
                target.setCaptured(true);
                destination.setCurrPiece(null);
            } else {
                // Attacker dies
                piece.setCaptured(true);
                currentPos.setCurrPiece(null);
                return true;
            }
        }

        currentPos.setCurrPiece(null);
        destination.setCurrPiece(piece);
        piece.setPosition(destination);

        // Check win condition
        if (checkWinCondition()) {
            gameState.setGameOver(true);
            gameState.setWinner(currentPlayer);
        }

        return true;
    }

/**
 * Checks if the current player has won the game.
 *
 * Pre-condition:
 * - currentPlayer and board must be initialized.
 *
 * Post-condition:
 * - Returns true if the current player occupies the opponent's home base.
 *
 * @return true if win condition is met; false otherwise.
 */
    private boolean checkWinCondition() {
    Tile opponentHome;

    if (currentPlayer == player1) {
        opponentHome = board.getTile(3, 8);
    } else {
        opponentHome = board.getTile(3, 0);
    }
        
        return opponentHome.isOccupied() && 
               opponentHome.getCurrPiece().getOwner().equals(currentPlayer.getName());
    }

/**
 * Gets the current board.
 *
 * Pre-condition:
 * - Game must be initialized.
 *
 * Post-condition:
 * - Returns the board object used in the game.
 *
 * @return The board instance.
 */
    public static Board getBoard() {
        return board;
    }

/**
 * Returns the current player whose turn it is.
 *
 * Pre-condition:
 * - Game must be started and player turns established.
 *
 * Post-condition:
 * - Returns the current Player.
 *
 * @return The player whose turn it currently is.
 */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
/**
 * Returns the current game state.
 *
 * Pre-condition:
 * - Game must be initialized.
 *
 * Post-condition:
 * - Returns the GameState object tracking win condition and game over state.
 *
 * @return The GameState instance.
 */
    public GameState getGameState() {
        return gameState;
    }
}
