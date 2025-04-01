package model;

public class GameState {
    private boolean isGameOver;
    private Player winner;

    public GameState() {
        this.isGameOver = false;
        this.winner = null;
    }

    public boolean checkVictory() {
        return isGameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
