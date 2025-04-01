package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.BoardController;
import model.Board;
import model.tiles.*;
import model.pieces.Piece;

public class BoardView {
    private static final int TILE_SIZE = 80;

    private JFrame displayFrame;
    private JPanel boardPanel;
    private JPanel statusPanel;
    private JLabel turnLabel;
    private JLabel eventLabel;
    private JButton[][] tiles;
    private BoardController controller;
    private Board board;

/**
 * Constructs the BoardView for rendering the game board UI.
 *
 * Pre-condition:
 * - controller and board must be initialized.
 *
 * Post-condition:
 * - Creates and prepares the GUI components but does not display them yet.
 *
 * @param controller The BoardController managing user interactions.
 * @param board The Board model for current game state.
 */
    public BoardView(BoardController controller, Board board) {
        this.controller = controller;
        this.board = board;
        initializeFrame();
        createBoard();
    }

/**
 * Initializes the display frame with layout and sizing.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - A JFrame is prepared with a fixed size and close behavior.
 */
    private void initializeFrame() {
        displayFrame = new JFrame("Jungle King");
        displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayFrame.setSize(board.getCols() * TILE_SIZE + 100, board.getRows() * TILE_SIZE + 100);
        displayFrame.setLocationRelativeTo(null);
        displayFrame.setResizable(false);
    }

/**
 * Creates and lays out the main components: board, status, and buttons.
 *
 * Pre-condition:
 * - board must be initialized.
 *
 * Post-condition:
 * - Board tiles, labels, and control buttons are added to the frame.
 */
    private void createBoard() {
        JPanel container = new JPanel(new BorderLayout(0, 10));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        container.setBackground(new Color(245, 245, 245));

        // player turns and event messages
        createStatusPanel();
        container.add(statusPanel, BorderLayout.NORTH);

        // game board
        boardPanel = new JPanel(new GridLayout(board.getRows(), board.getCols(), 2, 2));
        createBoardTiles();
        container.add(boardPanel, BorderLayout.CENTER);

        // Add menu buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton restartButton = new JButton("Restart");
        JButton menuButton = new JButton("Menu");
        
        restartButton.addActionListener(e -> controller.restartGame());
        menuButton.addActionListener(e -> controller.returnToMenu());
        
        buttonPanel.add(restartButton);
        buttonPanel.add(menuButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        displayFrame.add(container);
    }

/**
 * Creates the top panel showing player turn and game events.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Initializes and configures status labels and panel appearance.
 */
    private void createStatusPanel() {
        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setPreferredSize(new Dimension(displayFrame.getWidth(), 60));
        statusPanel.setBackground(new Color(245, 245, 245));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        turnLabel = new JLabel("", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setForeground(new Color(50, 50, 50));
        
        eventLabel = new JLabel("", SwingConstants.CENTER);
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        eventLabel.setForeground(new Color(100, 100, 100));

        JPanel labelsPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        labelsPanel.setBackground(new Color(245, 245, 245));
        labelsPanel.add(turnLabel);
        labelsPanel.add(eventLabel);
        
        statusPanel.add(labelsPanel, BorderLayout.CENTER);
    }

/**
 * Initializes the board's tile buttons and applies listeners and visuals.
 *
 * Pre-condition:
 * - board must be valid and initialized.
 *
 * Post-condition:
 * - A grid of buttons is created to represent the game board.
 */
    private void createBoardTiles() {
        tiles = new JButton[board.getRows()][board.getCols()];

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                JButton tile = new JButton();
                tile.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                tile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                tile.setContentAreaFilled(true);
                tile.setOpaque(true);
                
                final int finalRow = row;
                final int finalCol = col;
                tile.addActionListener(e -> controller.onTileClicked(finalRow, finalCol));
                
                Tile boardTile = board.getTile(row, col);
                setupTileAppearance(tile, boardTile);
                
                tiles[row][col] = tile;
                boardPanel.add(tile);
            }
        }
    }

/**
 * Updates the visual appearance of a tile based on its type and piece.
 *
 * Pre-condition:
 * - tile and boardTile must be valid.
 *
 * Post-condition:
 * - Button is styled with correct color and icon based on game state.
 *
 * @param tile The JButton to modify.
 * @param boardTile The Tile model tied to that button.
 */
    private void setupTileAppearance(JButton tile, Tile boardTile) {
        // reset default tile
        tile.setBackground(new Color(200, 200, 200));
        tile.setIcon(null);

        // setup special tiles
        if (boardTile instanceof Lake) {
            tile.setBackground(new Color(165, 206, 230));
        } else if (boardTile instanceof Trap) {
            Trap trap = (Trap) boardTile;
            String owner = trap.getOwner();
            if (owner != null) {
                if (!boardTile.isOccupied()) {
                    tile.setIcon(loadScaledIcon("trap.png"));
                }
            }
        } else if (boardTile instanceof HomeBase) {
            HomeBase home = (HomeBase) boardTile;
            String owner = home.getOwner();
            if (owner != null && !boardTile.isOccupied()) {
                String prefix = owner.equals("Blue") ? "b_" : "g_";
                tile.setIcon(loadScaledIcon(prefix + "homebase.png"));
            }
        }

        // put piece on tile
        if (boardTile.isOccupied()) {
            Piece piece = boardTile.getCurrPiece();
            if (piece != null) {
                String pieceName = piece.getClass().getSimpleName();
                String owner = piece.getOwner();
                if (owner != null) {
                    String prefix = owner.equals("Blue") ? "b_" : "g_";
                    tile.setIcon(loadScaledIcon(prefix + pieceName.toLowerCase() + ".png"));
                }
            }
        }
    }

/**
 * Loads a scaled image icon from the resources folder.
 *
 * Pre-condition:
 * - filename must exist in ../resources/.
 *
 * Post-condition:
 * - Returns a scaled ImageIcon of the specified file.
 *
 * @param filename The file name of the image.
 * @return Scaled ImageIcon instance.
 */
    private ImageIcon loadScaledIcon(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource("../resources/" + filename));
        Image image = icon.getImage().getScaledInstance(TILE_SIZE - 20, TILE_SIZE - 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

/**
 * Updates the visual state of all tiles based on the board model.
 *
 * Pre-condition:
 * - board must be initialized.
 *
 * Post-condition:
 * - Refreshes all tiles to match the game state.
 */
    public void updateBoard() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                JButton tile = tiles[row][col];
                Tile boardTile = board.getTile(row, col);
                
                setupTileAppearance(tile, boardTile);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

/**
 * Highlights a tile with a custom border color.
 *
 * Pre-condition:
 * - row and col must be within valid range.
 *
 * Post-condition:
 * - Applies a colored border to the specified tile.
 *
 * @param row The row index.
 * @param col The column index.
 * @param color The highlight color.
 */
    public void highlightTile(int row, int col, Color color) {
        if (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length) {
            tiles[row][col].setBorder(BorderFactory.createLineBorder(color, 3));
        }
    }

/**
 * Clears all tile highlights (borders) on the board.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - All tiles revert to default borders.
 */
    public void clearHighlights() {
        for (JButton[] row : tiles) {
            for (JButton tile : row) {
                tile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }
        }
    }

/**
 * Updates the displayed current turn label.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Turn label text is updated with current player.
 *
 * @param player The name of the current player.
 */
    public void updateTurn(String player) {
        turnLabel.setText(player + "'s Turn");
    }

/**
 * Updates the event message label shown to the user.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Event label is updated with the provided message.
 *
 * @param message The message to show.
 */
    public void updateEvent(String message) {
        eventLabel.setText(message);
    }

/**
 * Displays a popup message dialog to the user.
 *
 * Pre-condition:
 * - message must be non-null.
 *
 * Post-condition:
 * - Shows a blocking message dialog.
 *
 * @param message The message to display.
 */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(displayFrame, message);
    }

/**
 * Shows or hides the board frame.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - JFrame visibility is updated.
 *
 * @param visible true to show, false to hide.
 */
    public void setVisible(boolean visible) {
        displayFrame.setVisible(visible);
    }

/**
 * Closes the board view window and releases resources.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - JFrame is disposed.
 */
    public void dispose() {
        displayFrame.dispose();
    }
}
