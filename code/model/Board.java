package model.board;

import model.pieces.*;
import model.Player;
import model.tiles.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Tile[][] tiles;
    private static final int ROWS = 7;
    private static final int COLS = 9;

    // Positions for special tiles
    private static final int[][] LAKE_POSITIONS = {
        {1,3}, {1,4}, {1,5}, {2,3}, {2,4}, {2,5},
        {4,3}, {4,4}, {4,5}, {5,3}, {5,4}, {5,5}
    };
    private static final int[][] BLUE_TRAPS = {{2,0}, {4,0}, {3,1}};
    private static final int[][] GREEN_TRAPS = {{2,8}, {4,8}, {3,7}};
    private static final int[] BLUE_HOME = {3,0};
    private static final int[] GREEN_HOME = {3,8};

    public Board() {
        tiles = new Tile[ROWS][COLS];
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize all tiles as Land first
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                tiles[row][col] = new Land(row, col);
            }
        }

        // Set lakes
        for (int[] pos : LAKE_POSITIONS) {
            tiles[pos[0]][pos[1]] = new Lake(pos[0], pos[1]);
        }

        // Set blue traps
        for (int[] pos : BLUE_TRAPS) {
            tiles[pos[0]][pos[1]] = new Trap(pos[0], pos[1], "Blue");
        }

        // Set green traps
        for (int[] pos : GREEN_TRAPS) {
            tiles[pos[0]][pos[1]] = new Trap(pos[0], pos[1], "Green");
        }

        // Set home bases
        tiles[BLUE_HOME[0]][BLUE_HOME[1]] = new HomeBase(BLUE_HOME[0], BLUE_HOME[1], "Blue");
        tiles[GREEN_HOME[0]][GREEN_HOME[1]] = new HomeBase(GREEN_HOME[0], GREEN_HOME[1], "Green");
    }

    public void render() {
        // Render the board (implementation depends on view)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                tiles[row][col].render();
            }
        }
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            return tiles[row][col];
        }
        return null;
    }

    public Tile getTile(String pos) {
        if (pos.length() == 2) {
            int row = Character.getNumericValue(pos.charAt(0));
            int col = Character.getNumericValue(pos.charAt(1));
            return getTile(row, col);
        }
        return null;
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

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }
}
