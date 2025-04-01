import java.util.ArrayList;
import java.util.List;


/**
 * The Board class represents the game board for a strategy game. It contains
 * a grid of tiles where pieces, traps, lakes, and home bases are placed.
 * */
public class Board 
{
    private static final int ROWS = 7;
    private static final int COLS = 9;
    private String[][] grid;

    // TEMP
    private static final int[][] LAKE_POSITIONS = {
        {1,3}, {1,4}, {1,5}, {2,3}, {2,4}, {2,5},
        {4,3}, {4,4}, {4,5}, {5,3}, {5,4}, {5,5}
    };
    private static final int[][] BLUE_TRAPS = {{2,0}, {4,0}, {3,1}};
    private static final int[][] GREEN_TRAPS = {{2,8}, {4,8}, {3,7}};
    private static final int[] BLUE_HOME = {3,0};
    private static final int[] GREEN_HOME = {3,8};

    public Board() {
        grid = new String[ROWS][COLS];
        initializeBoard();
    }

    private void initializeBoard() {
        // init all tiles as land
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = "LAND";
            }
        }

        // set lakes, traps, and bases
        for (int[] pos : LAKE_POSITIONS) {
            grid[pos[0]][pos[1]] = "LAKE";
        }

        for (int[] pos : BLUE_TRAPS) {
            grid[pos[0]][pos[1]] = "BLUE_TRAP";
        }
        for (int[] pos : GREEN_TRAPS) {
            grid[pos[0]][pos[1]] = "GREEN_TRAP";
        }

        grid[BLUE_HOME[0]][BLUE_HOME[1]] = "BLUE_HOME";
        grid[GREEN_HOME[0]][GREEN_HOME[1]] = "GREEN_HOME";
    }

    public boolean isLake(int row, int col) {
        for (int[] pos : LAKE_POSITIONS) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        return false;
    }

    public boolean isTrap(int row, int col) {
        for (int[] pos : BLUE_TRAPS) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        for (int[] pos : GREEN_TRAPS) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        return false;
    }

    public boolean isHomeBase(int row, int col, String owner) {
        if (owner.equals("Blue")) {
            return row == BLUE_HOME[0] && col == BLUE_HOME[1];
        } else {
            return row == GREEN_HOME[0] && col == GREEN_HOME[1];
        }
    }

    public String getTileAt(int row, int col) {
        return grid[row][col];
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }
}