import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * The Game class manages the overall game flow, player turns, 
 * piece selection, movement, and win conditions.
 */
public class Game 
{
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    public Game(){
        this.board = new Board();
        this.player1 = new Player("Blue");
        this.player2 = new Player("Green");
        this.gameOver = false;
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

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void setGameOver (boolean gameOver){
        this.gameOver = gameOver;
    }

    // Pre-game
    public Player determineFirstPlayer(String player1Choice, String player2Choice) {
        int player1Strength = getPieceStrength(player1Choice);
        int player2Strength = getPieceStrength(player2Choice);
        
        if (player1Strength > player2Strength) {
            currentPlayer = player1;
            return player1;
        } else {
            currentPlayer = player2;
            return player2;
        }
    }

    private int getPieceStrength(String pieceName) {
        // Higher number means stronger piece
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

    // Game Proper
    public void switchTurn(){
        if(currentPlayer == player1){
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
    
    public void initializePieces(){
        Random random = new Random();
        currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    public Board getBoard() {
        return board;
    }
}