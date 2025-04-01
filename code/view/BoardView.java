package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.BoardController;
import model.Board;
import model.tiles.Tile;
import model.tiles.Lake;
import model.tiles.Trap;
import model.tiles.HomeBase;
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

    public BoardView(BoardController controller, Board board) {
        this.controller = controller;
        this.board = board;
        initializeFrame();
        createBoard();
    }

    private void initializeFrame() {
        displayFrame = new JFrame("Jungle King");
        displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayFrame.setSize(board.getCols() * TILE_SIZE + 100, board.getRows() * TILE_SIZE + 100);
        displayFrame.setLocationRelativeTo(null);
        displayFrame.setResizable(false);
    }

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

    private ImageIcon loadScaledIcon(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource("../resources/" + filename));
        Image image = icon.getImage().getScaledInstance(TILE_SIZE - 20, TILE_SIZE - 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

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

    public void highlightTile(int row, int col, Color color) {
        if (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length) {
            tiles[row][col].setBorder(BorderFactory.createLineBorder(color, 3));
        }
    }

    public void clearHighlights() {
        for (JButton[] row : tiles) {
            for (JButton tile : row) {
                tile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }
        }
    }

    public void updateTurn(String player) {
        turnLabel.setText(player + "'s Turn");
    }

    public void updateEvent(String message) {
        eventLabel.setText(message);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(displayFrame, message);
    }

    public void setVisible(boolean visible) {
        displayFrame.setVisible(visible);
    }

    public void dispose() {
        displayFrame.dispose();
    }
}
