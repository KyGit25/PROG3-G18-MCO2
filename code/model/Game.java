package model;

import model.Board;
import model.pieces.*;
import model.tiles.Tile;
import model.tiles.HomeBase;
import model.tiles.Trap;
import model.tiles.Lake;
import model.interfaces.Leaping;
import model.interfaces.Swimming;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameState gameState;

   // TEMP
    private static final int[] BLUE_HOME = {3, 0};
    private static final int[] GREEN_HOME = {3, 8};

    public Game() {
        board = new Board();
        this.player1 = new Player("Blue");
        this.player2 = new Player("Green");
        this.gameState = new GameState();
        initializePieces();
    }

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
        
        // Get current position
        Tile currentPos = piece.getPosition();
        
        // Check if move is valid
        if (!piece.canMove(destination)) {
            return false;
        }

        // Handle capture
        if (destination.isOccupied()) {
            Piece target = destination.getCurrPiece();
            
            if (piece.canCapture(target)) {
                target.setCaptured(true);
                destination.setCurrPiece(null);
            } else {
                // Attacker dies
                piece.setCaptured(true);
                currentPos.setCurrPiece(null);
                // destination.setCurrPiece(null);
                // target.setPosition(currentPos);
                // currentPos.setCurrPiece(target);
                return true;
            }
        }

        // Update positions
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

    private boolean checkWinCondition() {
        // Check if current player reached opponent's home base
        Tile opponentHome = currentPlayer == player1 ? 
            board.getTile(GREEN_HOME[0], GREEN_HOME[1]) : 
            board.getTile(BLUE_HOME[0], BLUE_HOME[1]);
        
        return opponentHome.isOccupied() && 
               opponentHome.getCurrPiece().getOwner().equals(currentPlayer.getName());
    }

    public static Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }
}
