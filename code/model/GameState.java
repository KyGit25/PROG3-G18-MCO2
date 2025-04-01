package model;

public class GameState {
    private boolean isGameOver;
    private Player winner;

/**
 * Constructs a new GameState with default values.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - isGameOver is set to false.
 * - winner is set to null.
 */
    public GameState() {
        this.isGameOver = false;
        this.winner = null;
    }

/**
 * Checks whether the game has ended.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the current value of isGameOver.
 *
 * @return true if the game is over, false otherwise.
 */
    public boolean checkVictory() {
        return isGameOver;
    }

/**
 * Retrieves the player who won the game.
 *
 * Pre-condition:
 * - The game must be over and a winner must be set.
 *
 * Post-condition:
 * - Returns the winner player, or null if no winner has been set.
 *
 * @return The Player who won, or null if not yet determined.
 */
    public Player getWinner() {
        return winner;
    }

/**
 * Sets the game over state.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - isGameOver is updated to the specified value.
 *
 * @param gameOver Boolean value indicating whether the game has ended.
 */
    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }

/**
 * Sets the winning player of the game.
 *
 * Pre-condition:
 * - winner must be a valid Player object (can be null to reset).
 *
 * Post-condition:
 * - The winner field is updated to the specified player.
 *
 * @param winner The Player who has won the game.
 */
    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
