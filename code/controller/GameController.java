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

/**
 * Constructs a GameController and initializes the main menu.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - MenuController and GameState are initialized.
 * - Menu is displayed.
 */
    public GameController() {
        this.menuController = new MenuController(this);
        this.gameState = new GameState();
    }

/**
 * Starts a new game session, sets up piece selection view.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Game, GameView, and internal flags are reset.
 * - Piece selection view is displayed.
 */
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

/**
 * Displays the shuffled piece selection screen to players.
 *
 * Pre-condition:
 * - Game must be initialized.
 *
 * Post-condition:
 * - GameView shows all shuffled pieces for Player 1 to begin selection.
 */
    private void startPieceSelection() {
        List<String> shuffledPieces = game.getShuffledPieces();
        gameView.showPieceSelection(shuffledPieces);
        gameView.updateStatus("Player 1: Select your piece");
        gameView.setVisible(true);
    }

/**
 * Handles player piece selection for both players.
 *
 * Pre-condition:
 * - piece must be a valid animal name.
 *
 * Post-condition:
 * - Stores selected piece for each player.
 * - Advances to the next step when both players have selected.
 *
 * @param piece The selected piece name.
 * @param currentTurn true if it's Player 1's turn; false otherwise.
 */
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

/**
 * Completes the piece selection phase and starts the actual game.
 *
 * Pre-condition:
 * - player1Piece and player2Piece must be set.
 *
 * Post-condition:
 * - Determines first player.
 * - Displays message with selection results.
 * - Opens game board after delay.
 */
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

/**
 * Restarts the game from scratch.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Board view is disposed.
 * - A new Game instance and piece selection is started.
 */
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

/**
 * Exits the application entirely.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - The application terminates.
 */
    public void exitGame() {
        System.exit(0);
    }

/**
 * Returns to the main menu from any game view.
 *
 * Pre-condition:
 * - Game or GameView may be active.
 *
 * Post-condition:
 * - Active game and board views are disposed.
 * - Main menu is displayed.
 */
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
