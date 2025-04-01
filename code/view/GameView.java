package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import controller.GameController;

public class GameView {
    private JFrame selectionFrame;
    private GameController controller;
    private boolean isPlayer1Turn;

    // UI Components
    private JPanel mainPanel;
    private JPanel selectionPanel;
    private JLabel statusLabel;
    private JButton[] pieceButtons;
    private ImageIcon[] blueAnimalIcons;
    private ImageIcon[] greenAnimalIcons;

    // Constants
    private static final String[] ANIMAL_NAMES = {
        "Elephant", "Lion", "Tiger", "Leopard", 
        "Wolf", "Dog", "Cat", "Rat"
    };

/**
 * Constructs a GameView for piece selection phase.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Initializes the selection frame and GUI components.
 */
    public GameView() {
        initializeSelectionFrame();
        loadResources();
        loadPieceSelection();
    }

/**
 * Initializes the frame used for piece selection.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Configures frame size, close operation, appearance.
 */
    private void initializeSelectionFrame() {
        selectionFrame = new JFrame("Jungle King - Piece Selection");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(600, 400);
        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setResizable(false);
        selectionFrame.getContentPane().setBackground(new Color(245, 245, 245));
        isPlayer1Turn = true;
    }

/**
 * Loads animal icons (blue and green) for piece selection.
 *
 * Pre-condition:
 * - Image resources must exist under ../resources/.
 *
 * Post-condition:
 * - Loads and stores scaled ImageIcons for each piece.
 */
    private void loadResources() {
        blueAnimalIcons = new ImageIcon[ANIMAL_NAMES.length];
        greenAnimalIcons = new ImageIcon[ANIMAL_NAMES.length];

        for (int i = 0; i < ANIMAL_NAMES.length; i++) {
            blueAnimalIcons[i] = loadScaledIcon("b_" + ANIMAL_NAMES[i].toLowerCase() + ".png");
            greenAnimalIcons[i] = loadScaledIcon("g_" + ANIMAL_NAMES[i].toLowerCase() + ".png");
        }
    }

/**
 * Loads a scaled icon from resources folder.
 *
 * Pre-condition:
 * - filename must be a valid resource path.
 *
 * Post-condition:
 * - Returns an ImageIcon scaled to 60x60.
 *
 * @param filename File name of the icon to load.
 * @return Scaled ImageIcon for GUI.
 */
    private ImageIcon loadScaledIcon(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource("../resources/" + filename)); // Resource path
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Icon size
        return new ImageIcon(scaledImage);
    }

/**
 * Builds and lays out the piece selection interface.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Adds labels, buttons, and panels to selection frame.
 */
    private void loadPieceSelection() {
        selectionFrame.setLayout(new BorderLayout());

        statusLabel = createStyledLabel("Player 1: Select your piece");
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(245, 245, 245));
        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        selectionFrame.add(statusPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        
        selectionPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        selectionPanel.setBackground(new Color(245, 245, 245));
        pieceButtons = new JButton[8];

        JPanel piecePanel = new JPanel(new BorderLayout());
        piecePanel.setBackground(new Color(245, 245, 245));
        piecePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        piecePanel.add(selectionPanel, BorderLayout.CENTER);
        
        mainPanel.add(piecePanel, BorderLayout.CENTER);
        selectionFrame.add(mainPanel, BorderLayout.CENTER);
    }

/**
 * Creates a styled label for status messages.
 *
 * Pre-condition:
 * - text must not be null.
 *
 * Post-condition:
 * - Returns a centered bold label.
 *
 * @param text The label's initial content.
 * @return JLabel with applied styling.
 */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

/**
 * Displays the piece selection options to players.
 *
 * Pre-condition:
 * - pieces must contain 8 valid animal names.
 *
 * Post-condition:
 * - Populates piece buttons with listeners and assigns metadata.
 *
 * @param pieces List of animal names to be shown.
 */
    public void showPieceSelection(List<String> pieces) {
        selectionPanel.removeAll();

        for (int i = 0; i < pieces.size(); i++) {
            pieceButtons[i] = new JButton();
            pieceButtons[i].setPreferredSize(new Dimension(100, 100));
            pieceButtons[i].setBackground(new Color(245,245,245));
            pieceButtons[i].setBorder(BorderFactory.createRaisedBevelBorder());
            pieceButtons[i].putClientProperty("piece", pieces.get(i));
            

            pieceButtons[i].addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                String piece = (String) button.getClientProperty("piece");
                
                for (int j = 0; j < ANIMAL_NAMES.length; j++) {
                    if (piece.equals(ANIMAL_NAMES[j])) {
                        button.setIcon(isPlayer1Turn ? blueAnimalIcons[j] : greenAnimalIcons[j]);
                        button.setBackground(Color.GRAY);
                        break;
                    }
                }
                
                controller.onPieceSelected(piece, isPlayer1Turn);
            });
            
            selectionPanel.add(pieceButtons[i]);
        }
        
        selectionPanel.revalidate();
        selectionPanel.repaint();
    }

/**
 * Disables all piece buttons (after selection is done).
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - All piece buttons become non-clickable.
 */
    public void disableAllButtons() {
        for (JButton button : pieceButtons) {
            button.setEnabled(false);
        }
    }

/**
 * Disables the button for a specific piece.
 *
 * Pre-condition:
 * - piece must match one of the button's labels.
 *
 * Post-condition:
 * - That specific piece button becomes disabled.
 *
 * @param piece The name of the piece to disable.
 */
    public void disablePieceButton(String piece) {
        for (JButton button : pieceButtons) {
            if (button.getText().equals(piece)) {
                button.setEnabled(false);
                break;
            }
        }
    }

/**
 * Updates the status label (e.g., player turn info).
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Displays the new status and updates player turn flag.
 *
 * @param message The status text to display.
 */
    public void updateStatus(String message) {
        statusLabel.setText(message);
        if (message.startsWith("Player 2")) {
            isPlayer1Turn = false;
        }
    }

/**
 * Shows a popup dialog message.
 *
 * Pre-condition:
 * - message must not be null.
 *
 * Post-condition:
 * - Dialog with message is shown to the user.
 *
 * @param message The message to display.
 */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(selectionFrame, message);
    }

/**
 * Assigns the GameController to this view.
 *
 * Pre-condition:
 * - controller must not be null.
 *
 * Post-condition:
 * - View can now trigger events back to controller.
 *
 * @param controller The GameController instance.
 */
    public void setController(GameController controller) {
        this.controller = controller;
    }

/**
 * Sets the visibility of the selection frame.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Frame becomes visible or hidden.
 *
 * @param visible true to show, false to hide.
 */
    public void setVisible(boolean visible) {
        selectionFrame.setVisible(visible);
    }

/**
 * Disposes the selection window and releases resources.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Frame is closed and memory is released.
 */
    public void dispose() {
        selectionFrame.dispose();
    }
}
