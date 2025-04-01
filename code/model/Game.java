package model;

import model.Board;
import model.pieces.*;
import model.tiles.Tile;
import model.tiles.HomeBase;
import model.interfaces.Leaping;
import model.interfaces.Swimming;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameState gameState;

   // TEMP
    private static final int[] BLUE_HOME = {3, 0};
    private static final int[] GREEN_HOME = {3, 8};

    public Game() {
        this.board = new Board();
        this.player1 = new Player("Blue");
        this.player2 = new Player("Green");
        this.gameState = new GameState();
        initializePieces();
    }

    public void initializePieces() {
        // Initialize blue pieces
        player1.addPiece(new Elephant(board.getTile(0, 0)));
        player1.addPiece(new Lion(board.getTile(0, 6)));
        player1.addPiece(new Tiger(board.getTile(6, 0)));
        player1.addPiece(new Leopard(board.getTile(6, 6)));
        player1.addPiece(new Wolf(board.getTile(1, 1)));
        player1.addPiece(new Dog(board.getTile(5, 1)));
        player1.addPiece(new Cat(board.getTile(1, 5)));
        player1.addPiece(new Rat(board.getTile(5, 5)));

        // Initialize green pieces (mirrored positions)
        player2.addPiece(new Elephant(board.getTile(6, 8)));
        player2.addPiece(new Lion(board.getTile(6, 2)));
        player2.addPiece(new Tiger(board.getTile(0, 8)));
        player2.addPiece(new Leopard(board.getTile(0, 2)));
        player2.addPiece(new Wolf(board.getTile(5, 7)));
        player2.addPiece(new Dog(board.getTile(1, 7)));
        player2.addPiece(new Cat(board.getTile(5, 3)));
        player2.addPiece(new Rat(board.getTile(1, 3)));

        // Randomly choose starting player
        currentPlayer = new Random().nextBoolean() ? player1 : player2;
    }

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

    public Player determineFirstPlayer(String player1Choice, String player2Choice) {
        int p1Strength = getPieceStrength(player1Choice);
        int p2Strength = getPieceStrength(player2Choice);
        
        currentPlayer = (p1Strength > p2Strength) ? player1 : player2;
        return currentPlayer;
    }

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

    public void switchTurn() 
    {
    currentPlayer = (currentPlayer == player1) ? player2 : player1;
    if (currentPlayer.hasLostAllPieces()) 
    {
        // Forfeit turn if no pieces
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    }

    public boolean movePiece(Piece piece, Tile destination) {
        if (piece == null || destination == null) return false;
        
        // Check if move is valid
        if (piece.canMove(destination)) {
            // Handle special movement (leaping/swimming)
            if (piece instanceof Leaping && needsLeaping(piece.getPosition(), destination)) {
                if (((Leaping)piece).leap(destination) == null) return false;
            } else if (piece instanceof Swimming && ((Swimming)piece).swim(destination) == null) {
                return false;
            }

            // Handle capture
            if (destination.isOccupied()) {
                Piece opponent = destination.getCurrPiece();
                if (piece.canCapture(opponent)) {
                    opponent.setCaptured(true);
                    destination.setCurrPiece(null);
                } else {
                    // Attacker dies
                    piece.setCaptured(true);
                    piece.getPosition().setCurrPiece(null);
                    return false;
                }
            }

            // Update positions
            piece.getPosition().setCurrPiece(null);
            destination.setCurrPiece(piece);
            piece.setPosition(destination);

            // Check win condition
            if (checkWinCondition()) {
                gameState.setGameOver(true);
                gameState.setWinner(currentPlayer);
            }

            return true;
        }
        return false;
    }

    private boolean needsLeaping(Tile current, Tile destination) {
        // Check if path between current and destination is all lakes
        // Implementation depends on exact path checking
        return false;
    }

    private boolean checkWinCondition() {
        // Check if current player reached opponent's home base
        Tile opponentHome = currentPlayer == player1 ? 
            board.getTile(GREEN_HOME[0], GREEN_HOME[1]) : 
            board.getTile(BLUE_HOME[0], BLUE_HOME[1]);
        
        return opponentHome.isOccupied() && 
               opponentHome.getCurrPiece().getOwner().equals(currentPlayer.getName());
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }
}
