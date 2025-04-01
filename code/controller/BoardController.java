package controller;

import model.Game;
import model.Board;
import model.Player;
import model.pieces.Piece;
import model.tiles.Tile;
import view.BoardView;
import java.awt.Color;

public class BoardController {
    private Game game;
    private Board board;
    private BoardView view;
    private GameController gameController;
    
    private Piece selectedPiece;
    private boolean isMoving;

/**
 * Constructs a BoardController to manage game board interactions.
 *
 * Pre-condition:
 * - game and gameController must be valid and initialized.
 *
 * Post-condition:
 * - Initializes board, view, and sets default selection state.
 * - Triggers view update for the initial board display.
 *
 * @param gameController The GameController managing the game flow.
 * @param game The Game model instance.
 */
    public BoardController(GameController gameController, Game game) {
        this.gameController = gameController;
        this.game = game;
        this.board = game.getBoard();
        this.view = new BoardView(this, board);
        
        this.selectedPiece = null;
        this.isMoving = false;
        
        updateView();
    }

/**
 * Handles a tile click on the board.
 *
 * Pre-condition:
 * - row and col are within board bounds.
 *
 * Post-condition:
 * - Selects a piece if not already selected.
 * - Attempts to move the selected piece if in moving state.
 *
 * @param row The row of the clicked tile.
 * @param col The column of the clicked tile.
 */
    public void onTileClicked(int row, int col) {
        Tile clickedTile = board.getTile(row, col);
        
        if (isMoving) {
            handleMove(clickedTile);
        } else {
            handleSelection(clickedTile);
        }
    }

/**
 * Handles the selection of a piece on the given tile.
 *
 * Pre-condition:
 * - Tile must be occupied by a piece owned by the current player.
 *
 * Post-condition:
 * - Sets selectedPiece and enables move state.
 * - Highlights the selected tile and displays message.
 *
 * @param tile The tile containing the selected piece.
 */
    private void handleSelection(Tile tile) {
        if (!tile.isOccupied()) return;
        
        Piece piece = tile.getCurrPiece();
        if (!piece.getOwner().equals(game.getCurrentPlayer().getName())) {
            view.updateEvent("Not your piece!");
            return;
        }

        selectedPiece = piece;
        isMoving = true;
        view.highlightTile(tile.getRow(), tile.getCol(), new Color(50, 150, 50));
        view.updateEvent("Select destination for " + piece.getClass().getSimpleName());
    }

/**
 * Handles the movement of a previously selected piece.
 *
 * Pre-condition:
 * - selectedPiece must be set.
 * - destination must be a valid Tile.
 *
 * Post-condition:
 * - Attempts to move the piece.
 * - Handles capture, updates board and turn.
 * - Displays victory screen if game is over.
 *
 * @param destination The target tile for movement.
 */
    private void handleMove(Tile destination) {
        try {
            if (game.movePiece(selectedPiece, destination)) {
                view.clearHighlights();
                selectedPiece = null;
                isMoving = false;
                
                if (game.getGameState().checkVictory()) {
                    handleVictory();
                } else {
                    game.switchTurn();
                    updateView();
                }
            }
        } catch (RuntimeException e) {
            view.updateEvent(e.getMessage());
        }
    }

/**
 * Handles end-of-game behavior when a player wins.
 *
 * Pre-condition:
 * - A win condition must be met.
 *
 * Post-condition:
 * - Displays a win message and updates the event label.
 */
    private void handleVictory() {
        Player winner = game.getGameState().getWinner();
        view.showMessage(winner.getName() + " wins!");
        view.updateEvent(winner.getName() + " has won!");
    }

/**
 * Updates the board view and turn display.
 *
 * Pre-condition:
 * - Game must be in progress.
 *
 * Post-condition:
 * - Displays the current player's turn and prompts for input.
 */
    private void updateView() {
        Player currentPlayer = game.getCurrentPlayer();
        view.updateTurn(currentPlayer.getName());
        view.updateEvent("Select a piece to move");
        view.updateBoard();
    }

/**
 * Restarts the game by calling GameController.
 *
 * Pre-condition:
 * - GameController must be initialized.
 *
 * Post-condition:
 * - The current game is reset and restarted.
 */
    public void restartGame() {
        gameController.restartGame();
    }

/**
 * Returns to the main menu via GameController.
 *
 * Pre-condition:
 * - GameController must be initialized.
 *
 * Post-condition:
 * - Menu view is displayed and game views are disposed.
 */
    public void returnToMenu() {
        gameController.returnToMenu();
    }

/**
 * Makes the board view visible to the user.
 *
 * Pre-condition:
 * - view must be initialized.
 *
 * Post-condition:
 * - Board window is displayed.
 */
    public void showBoard() {
        view.setVisible(true);
    }

/**
 * Hides the board view from the user.
 *
 * Pre-condition:
 * - view must be initialized.
 *
 * Post-condition:
 * - Board window is hidden.
 */
    public void hideBoard() {
        view.setVisible(false);
    }

/**
 * Disposes of the board view.
 *
 * Pre-condition:
 * - view must be initialized.
 *
 * Post-condition:
 * - Board window is closed and resources are released.
 */
    public void disposeBoard() {
        view.dispose();
    }
}
