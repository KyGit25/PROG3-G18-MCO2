package controller;

import view.MenuView;

public class MenuController {
    private MenuView menuView;
    private GameController gameController;

/**
 * Constructs a MenuController and displays the main menu.
 *
 * Pre-condition:
 * - gameController must be a valid instance.
 *
 * Post-condition:
 * - MenuView is created and shown to the user.
 *
 * @param gameController The main GameController instance.
 */
    public MenuController(GameController gameController) {
        this.gameController = gameController;
        this.menuView = new MenuView(this);
        menuView.setVisible(true);
    }

/**
 * Starts a new game from the menu.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Transitions from menu to piece selection screen.
 */
    public void startNewGame() {
        gameController.startNewGame();
    }

/**
 * Displays the instructions window from the menu.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Instruction dialog is shown to the user.
 */
    public void showInstructions() {
        menuView.showInstructions();
    }

/**
 * Exits the entire application from the menu.
 *
 * Pre-condition:
 * - None.
 *
 * Post-condition:
 * - Terminates the program.
 */
    public void exitGame() {
        System.exit(0);
    }

/**
 * Shows or hides the menu based on the boolean flag.
 *
 * Pre-condition:
 * - menuView must be initialized.
 *
 * Post-condition:
 * - Menu window is shown or hidden.
 *
 * @param visible true to show the menu; false to hide it.
 */
    public void setMenuVisible(boolean visible) {
        menuView.setVisible(visible);
    }
}
