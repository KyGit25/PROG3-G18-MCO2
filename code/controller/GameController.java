package controller;

import java.util.List;
import javax.swing.Timer;
import model.Game;
import model.GameState;
import model.Player;
import view.GameView;
import view.MenuView;
import view.BoardView;

public class GameController {
    private Game game;
    private GameView gameView;
    private MenuView menuView;
    private GameState gameState;

    public GameController() {
        this.menuView = new MenuView(this);
        this.gameState = new GameState();
        menuView.setVisible(true);
    }

    public void startNewGame() {
        this.game = new Game();
        this.gameView = new GameView();
        this.gameState.reset();
        gameView.setController(this);
        menuView.setVisible(false);
        startPieceSelection();
    }

    private void startPieceSelection() {
        List<String> shuffledPieces = game.getShuffledPieces();
        gameView.showPieceSelection(shuffledPieces);
        gameView.updateStatus("Player 1: Select your piece");
        gameView.setVisible(true);
    }

    public void onPieceSelected(String piece, boolean currentTurn) {
        gameState.selectPiece(piece);
        gameView.disablePieceButton(piece);

        if (gameState.isSelectionComplete()) {
            gameView.disableAllButtons();
            handleSelectionComplete();
        } else {
            gameView.updateStatus("Player 2: Select your piece");
        }
    }

    private void handleSelectionComplete() {
        Player firstPlayer = game.determineFirstPlayer(
            gameState.getPlayer1Piece(), 
            gameState.getPlayer2Piece()
        );

        String message = String.format("Player 1 selected %s, Player 2 selected %s. %s goes first!", 
            gameState.getPlayer1Piece(), gameState.getPlayer2Piece(), firstPlayer.getName());
        gameView.updateStatus(message);
        
        Timer timer = new Timer(3000, e -> {
            gameView.dispose();
            BoardView boardView = new BoardView(this, game.getBoard());
            boardView.setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void restartGame() {
        game = new Game();
        gameState.reset();
        startPieceSelection();
    }

    public void exitGame() {
        System.exit(0);
    }

    public void returnToMenu() {
        if (gameView != null) {
            gameView.dispose();
        }
        menuView.setVisible(true);
    }
}
