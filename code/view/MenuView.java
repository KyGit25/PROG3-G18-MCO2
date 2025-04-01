package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.MenuController;

public class MenuView {
    private JFrame mainFrame;
    private MenuController controller;
    private JPanel mainPanel;

/**
 * Constructs the MenuView and displays the main menu UI.
 *
 * Pre-condition:
 * - controller must be a valid MenuController instance.
 *
 * Post-condition:
 * - Initializes menu components and shows the window.
 *
 * @param controller The controller handling menu actions.
 */
    public MenuView(MenuController controller) {
        this.controller = controller;
        initializeFrame();
        createMenuComponents();
    }

/**
 * Initializes the main JFrame for the menu.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Frame properties (size, close op, background) are set.
 */
    private void initializeFrame() {
        mainFrame = new JFrame("Jungle King");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
    }

/**
 * Creates and lays out the components on the menu screen.
 *
 * Pre-condition:
 * - mainFrame must be initialized.
 *
 * Post-condition:
 * - Menu buttons, labels, and decorative images are added to the frame.
 */
    private void createMenuComponents() {
        // main container
        JPanel container = new JPanel(null);
        container.setBackground(new Color(245, 245, 245));
        
        // center panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // load homebase
        JLabel blueHome = createHomeBase("b_homebase.png", true);
        JLabel greenHome = createHomeBase("g_homebase.png", false);
        
        /// main menu
        JLabel titleLabel = new JLabel("Jungle King");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = createMenuButton("Start Game");
        JButton instructionsButton = createMenuButton("Instructions");
        JButton exitButton = createMenuButton("Exit");

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(startButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(instructionsButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(exitButton);

        startButton.addActionListener(e -> controller.startNewGame());
        instructionsButton.addActionListener(e -> controller.showInstructions());
        exitButton.addActionListener(e -> controller.exitGame());

        container.add(blueHome);
        container.add(greenHome);
        container.add(mainPanel);
        
        mainPanel.setBounds(200, 0, 400, 500);
        
        mainFrame.add(container);
    }

/**
 * Creates a styled button with consistent look and feel.
 *
 * Pre-condition:
 * - text must be a valid button label.
 *
 * Post-condition:
 * - Returns a button with preferred size and style.
 *
 * @param text The label for the button.
 * @return A styled JButton instance.
 */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        
        return button;
    }

/**
 * Creates and positions a home base image icon.
 *
 * Pre-condition:
 * - filename must exist in ../resources/ directory.
 *
 * Post-condition:
 * - Returns a JLabel with the scaled image and positioned correctly.
 *
 * @param filename Image file to load.
 * @param isLeft true if it should appear on the left side.
 * @return A JLabel containing the home base image.
 */
    private JLabel createHomeBase(String filename, boolean isLeft) {
        final String RESOURCE_PATH = "../resources/";
        final int HOME_BASE_SIZE = 150;
        final int LEFT_MARGIN = 50;
        final int RIGHT_MARGIN = 600;
        final int VERTICAL_POSITION = 175;

        ImageIcon icon = new ImageIcon(getClass().getResource(RESOURCE_PATH + filename));
        Image scaledImage = icon.getImage().getScaledInstance(HOME_BASE_SIZE, HOME_BASE_SIZE, Image.SCALE_SMOOTH);
        JLabel homeBase = new JLabel(new ImageIcon(scaledImage));
        
        if (isLeft) {
            homeBase.setBounds(50, 175, HOME_BASE_SIZE, HOME_BASE_SIZE);
        } else {
            homeBase.setBounds(600, 175, HOME_BASE_SIZE, HOME_BASE_SIZE);
        }
        
        return homeBase;
    }

/**
 * Displays the game instructions in a formatted dialog.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Shows a modal dialog with HTML-formatted rules and how to play.
 */
    public void showInstructions() {
        final String INSTRUCTIONS = 
            "<html><div style='text-align: left; padding: 20px; width: 500px;'>" +
            "<h2 style='text-align: center;'>How to Play Jungle King</h2>" +
            
            "<h3>Initial Setup</h3>" +
            "<p>Players select their pieces:</p>" +
            "<ul>" +
            "<li>Eight animal pieces are shuffled and presented face down</li>" +
            "<li>Each player selects one piece</li>" +
            "<li>The player with the stronger animal goes first</li>" +
            "</ul>" +
            
            "<h3>Animal Strength (Highest to Lowest)</h3>" +
            "<p>Elephant > Lion > Tiger > Leopard > Wolf > Dog > Cat > Rat</p>" +
            "<p><i>Special rule: Only the Rat can capture the Elephant!</i></p>" +
            
            "<h3>Movement Rules</h3>" +
            "<ul>" +
            "<li>Basic Movement: All pieces can move one square horizontally or vertically</li>" +
            "<li>Lion & Tiger: Can jump across lakes horizontally or vertically</li>" +
            "<li>Rat: Can swim in lakes one square at a time</li>" +
            "</ul>" +
            
            "<h3>Special Rules</h3>" +
            "<ul>" +
            "<li>Lions and Tigers cannot jump over lakes if a Rat is in the way</li>" +
            "<li>Rats in water cannot capture Elephants on land</li>" +
            "<li>Rats can only capture other Rats while in water</li>" +
            "<li>Animals on land cannot capture animals in water (except Rat vs Rat)</li>" +
            "</ul>" +
            
            "<h3>Traps</h3>" +
            "<ul>" +
            "<li>When an opponent's piece lands on a trap, it becomes weak</li>" +
            "<li>Any owned piece next to a trapped opponent can capture it</li>" +
            "<li>Pieces regain strength when leaving traps</li>" +
            "</ul>" +
            
            "<h3>Winning the Game</h3>" +
            "<ul>" +
            "<li>Win by entering the opponent's home base</li>" +
            "<li>Players cannot enter their own home base</li>" +
            "</ul>" +
            "</div></html>";

        JOptionPane.showMessageDialog(
            mainFrame,
            INSTRUCTIONS,
            "Instructions",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

/**
 * Shows or hides the menu window.
 *
 * Pre-condition:
 * - mainFrame must be initialized.
 *
 * Post-condition:
 * - JFrame visibility is updated accordingly.
 *
 * @param visible true to show the menu, false to hide it.
 */
    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }

/**
 * Disposes the menu window and releases resources.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Frame is closed and memory is freed.
 */
    public void dispose() {
        mainFrame.dispose();
    }
} 
