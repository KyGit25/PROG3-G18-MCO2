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

    public BoardController(GameController gameController, Game game) {
        this.gameController = gameController;
        this.game = game;
        this.board = game.getBoard();
        this.view = new BoardView(this, board);
        
        this.selectedPiece = null;
        this.isMoving = false;
        
        updateView();
    }

    public void onTileClicked(int row, int col) {
        Tile clickedTile = board.getTile(row, col);
        
        if (isMoving) {
            handleMove(clickedTile);
        } else {
            handleSelection(clickedTile);
        }
    }

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

    private void handleVictory() {
        Player winner = game.getGameState().getWinner();
        view.showMessage(winner.getName() + " wins!");
        view.updateEvent(winner.getName() + " has won!");
    }

    private void updateView() {
        Player currentPlayer = game.getCurrentPlayer();
        view.updateTurn(currentPlayer.getName());
        view.updateEvent("Select a piece to move");
        view.updateBoard();
    }

    public void restartGame() {
        gameController.restartGame();
    }

    public void returnToMenu() {
        gameController.returnToMenu();
    }

    public void showBoard() {
        view.setVisible(true);
    }

    public void hideBoard() {
        view.setVisible(false);
    }

    public void disposeBoard() {
        view.dispose();
    }
}
