package model;

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

/**
 * Constructs a new Board and initializes all tiles including land, lake, traps, and home bases.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - A fully initialized board with appropriate tile types is created.
 */
    public Board() {
        tiles = new Tile[ROWS][COLS];
        initializeBoard();
    }
/**
 * Initializes all tiles on the board with their corresponding types.
 *
 * Pre-condition:
 * - The 2D Tile array must be instantiated.
 *
 * Post-condition:
 * - Each tile in the board is assigned the correct type and team color (if applicable).
 * - Trap, Lake, HomeBase, and Land tiles are placed appropriately.
 */
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

        // Store the tile types
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile tile = tiles[row][col];
                if (tile instanceof Trap) {
                    ((Trap) tile).setType("Trap");
                } else if (tile instanceof HomeBase) {
                    ((HomeBase) tile).setType("HomeBase");
                } else if (tile instanceof Lake) {
                    tile.setType("Lake");
                } else {
                    tile.setType("Land");
                }
            }
        }
    }
/**
 * Retrieves the tile at a specific row and column on the board.
 *
 * Pre-condition:
 * - row and col must be within the valid bounds of the board (0 ≤ row < 7, 0 ≤ col < 9).
 *
 * Post-condition:
 * - Returns the Tile at the given position if valid.
 * - Returns null if the position is invalid.
 *
 * @param row The row index of the board.
 * @param col The column index of the board.
 * @return The Tile at the specified location or null if invalid.
 */
    public Tile getTile(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            return tiles[row][col];
        }
        return null;
    }
/**
 * Gets the total number of rows on the board.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the number of rows (7).
 *
 * @return Total number of rows.
 */
    public static int getRows() {
        return ROWS;
    }

/**
 * Gets the total number of columns on the board.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Returns the number of columns (9).
 *
 * @return Total number of columns.
 */
    public static int getCols() {
        return COLS;
    }
}
