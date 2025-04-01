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

    public GameView() {
        initializeSelectionFrame();
        loadResources();
        loadPieceSelection();
    }

    private void initializeSelectionFrame() {
        selectionFrame = new JFrame("Jungle King - Piece Selection");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(600, 400);
        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setResizable(false);
        selectionFrame.getContentPane().setBackground(new Color(245, 245, 245));
        isPlayer1Turn = true;
    }

    private void loadResources() {
        blueAnimalIcons = new ImageIcon[ANIMAL_NAMES.length];
        greenAnimalIcons = new ImageIcon[ANIMAL_NAMES.length];

        for (int i = 0; i < ANIMAL_NAMES.length; i++) {
            blueAnimalIcons[i] = loadScaledIcon("b_" + ANIMAL_NAMES[i].toLowerCase() + ".png");
            greenAnimalIcons[i] = loadScaledIcon("g_" + ANIMAL_NAMES[i].toLowerCase() + ".png");
        }
    }

    private ImageIcon loadScaledIcon(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource("../resources/" + filename)); // Resource path
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Icon size
        return new ImageIcon(scaledImage);
    }

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

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

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

    public void disableAllButtons() {
        for (JButton button : pieceButtons) {
            disableButton(button);
        }
    }

    public void disablePieceButton(String piece) {
        for (JButton button : pieceButtons) {
            if (piece.equals(button.getClientProperty("piece"))) {
                disableButton(button);
                break;
            }
        }
    }

    private void disableButton(JButton button) {
        if (button.isEnabled()) {
            button.setEnabled(false);
            button.setDisabledIcon(button.getIcon());
        }
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
        if (message.startsWith("Player 2")) {
            isPlayer1Turn = false;
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(selectionFrame, message);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setVisible(boolean visible) {
        selectionFrame.setVisible(visible);
    }

    public void dispose() {
        selectionFrame.dispose();
    }
}
