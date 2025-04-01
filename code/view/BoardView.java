package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.GameController;
import model.Board;
import model.tiles.Tile;
import model.tiles.Lake;
import model.tiles.Trap;
import model.tiles.HomeBase;
import model.pieces.Piece;

public class BoardView {
    private static final int TILE_SIZE = 80;

    // piece positions
    private static final Object[][] BLUE_PIECES = {
        {"Tiger", 0, 0},
        {"Cat", 1, 1}, 
        {"Elephant", 0, 2},
        {"Wolf", 2, 2}, 
        {"Leopard", 4, 2}, 
        {"Dog", 5, 1},
        {"Lion", 6, 0}, 
        {"Rat", 6, 2}
    };
    
    private static final Object[][] GREEN_PIECES = {
        {"Tiger", 6, 8},
        {"Cat", 5, 7}, 
        {"Elephant", 6, 6},
        {"Wolf", 4, 6}, 
        {"Leopard", 2, 6}, 
        {"Dog", 1, 7},
        {"Lion", 0, 8}, 
        {"Rat", 0, 6}
    };

    private JFrame displayFrame;
    private JPanel boardPanel;
    private JPanel statusPanel;
    private JLabel turnLabel;
    private JLabel eventLabel;
    private JButton[][] tiles;
    private GameController controller;
    private Board board;

    public BoardView(GameController controller, Board board) {
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

        displayFrame.add(container);
    }

    private void createStatusPanel() {
        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setPreferredSize(new Dimension(displayFrame.getWidth(), 60));
        statusPanel.setBackground(new Color(245, 245, 245));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // turn
        // TEMP
        turnLabel = new JLabel("Blue's Turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setForeground(new Color(50, 50, 50));
        
        // event
        // TEMP
        eventLabel = new JLabel("Select a piece to move", SwingConstants.CENTER);
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        eventLabel.setForeground(new Color(100, 100, 100));

        // load labels
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
                tile.setBackground(new Color(200, 200, 200));
                
                String tileType = board.getTileAt(row, col);
                switch (tileType) {
                    case "LAKE":
                        tile.setBackground(new Color(165, 206, 230));
                        break;
                    case "BLUE_TRAP":
                    case "GREEN_TRAP":
                        tile.setIcon(loadScaledIcon("trap.png"));
                        break;
                    case "BLUE_HOME":
                        tile.setIcon(loadScaledIcon("b_homebase.png"));
                        break;
                    case "GREEN_HOME":
                        tile.setIcon(loadScaledIcon("g_homebase.png"));
                        break;
                }

                addPieceToTile(tile, row, col);
                
                tiles[row][col] = tile;
                boardPanel.add(tile);
            }
        }
    }

    private void addPieceToTile(JButton tile, int row, int col) {
        for (Object[] piece : BLUE_PIECES) {
            int pieceRow = (int)piece[1];
            int pieceCol = (int)piece[2];
            if (pieceRow == row && pieceCol == col) {
                String pieceName = (String)piece[0];
                tile.setIcon(loadScaledIcon("b_" + pieceName.toLowerCase() + ".png"));
                return;
            }
        }
        
        for (Object[] piece : GREEN_PIECES) {
            int pieceRow = (int)piece[1];
            int pieceCol = (int)piece[2];
            if (pieceRow == row && pieceCol == col) {
                String pieceName = (String)piece[0];
                tile.setIcon(loadScaledIcon("g_" + pieceName.toLowerCase() + ".png"));
                return;
            }
        }
    }

    private ImageIcon loadScaledIcon(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource("./resources/" + filename));
        Image image = icon.getImage().getScaledInstance(TILE_SIZE - 20, TILE_SIZE - 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    // MOVE TO CONTROLLER
    public void updateTurn(String player) {
        turnLabel.setText(player + "'s Turn");
    }

    public void updateEvent(String message) {
        eventLabel.setText(message);
    }

    public void setVisible(boolean visible) {
        displayFrame.setVisible(visible);
    }

    public void dispose() {
        displayFrame.dispose();
    }
}
