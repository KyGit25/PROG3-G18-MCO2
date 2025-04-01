package controller;

import java.util.List;
import javax.swing.Timer;
import model.Game;
import view.GameView;
import model.GameState;
import model.Player;

public class GameController {
    private Game game;
    private GameView gameView;
    private MenuController menuController;
    private BoardController boardController;
    private GameState gameState;
    
    private String player1Piece;
    private String player2Piece;
    private boolean selectionComplete;

    public GameController() {
        this.menuController = new MenuController(this);
        this.gameState = new GameState();
    }

    public void startNewGame() {
        this.game = new Game();
        this.gameView = new GameView();
    
        this.player1Piece = null;
        this.player2Piece = null;
        this.selectionComplete = false;
        
        gameView.setController(this);
        menuController.setMenuVisible(false);
        startPieceSelection();
    }

    private void startPieceSelection() {
        List<String> shuffledPieces = game.getShuffledPieces();
        gameView.showPieceSelection(shuffledPieces);
        gameView.updateStatus("Player 1: Select your piece");
        gameView.setVisible(true);
    }

    public void onPieceSelected(String piece, boolean currentTurn) {
        if (player1Piece == null) {
            player1Piece = piece;
            gameView.disablePieceButton(piece);
            gameView.updateStatus("Player 2: Select your piece");
        } else if (player2Piece == null) {
            player2Piece = piece;
            gameView.disablePieceButton(piece);
            gameView.disableAllButtons();
            handleSelectionComplete();
        }
    }

    private void handleSelectionComplete() {
        Player firstPlayer = game.determineFirstPlayer(
            player1Piece, 
            player2Piece
        );

        String message = String.format("Player 1 selected %s, Player 2 selected %s. %s goes first!", 
            player1Piece, player2Piece, firstPlayer.getName());
            gameView.updateStatus(message);
        
        Timer timer = new Timer(3000, e -> {
            gameView.dispose();
            boardController = new BoardController(this, game);
            boardController.showBoard();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void restartGame() {
        if (boardController != null) {
            boardController.disposeBoard();
            boardController = null;
        }
        
        game = new Game();
        
        this.player1Piece = null;
        this.player2Piece = null;
        this.selectionComplete = false;
        
        startPieceSelection();
    }

    public void exitGame() {
        System.exit(0);
    }

    public void returnToMenu() {
        if (gameView != null) {
            gameView.dispose();
        }
        
        if (boardController != null) {
            boardController.disposeBoard();
            boardController = null;
        }
        
        menuController.setMenuVisible(true);
    }
}
