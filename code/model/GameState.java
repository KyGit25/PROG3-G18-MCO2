public class GameState {
    private String player1Piece;
    private String player2Piece;
    private boolean isPlayer1Turn;

    public GameState() {
        reset();
    }

    public void reset() {
        player1Piece = null;
        player2Piece = null;
        isPlayer1Turn = true;
    }

    public void selectPiece(String piece) {
        if (isPlayer1Turn) {
            player1Piece = piece;
            isPlayer1Turn = false;
        } else {
            player2Piece = piece;
        }
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public boolean isSelectionComplete() {
        return player1Piece != null && player2Piece != null;
    }

    public String getPlayer1Piece() {
        return player1Piece;
    }

    public String getPlayer2Piece() {
        return player2Piece;
    }
} 