package controller;

import view.MenuView;

public class MenuController {
    private MenuView menuView;
    private GameController gameController;
    
    public MenuController(GameController gameController) {
        this.gameController = gameController;
        this.menuView = new MenuView(this);
        menuView.setVisible(true);
    }
    
    public void startNewGame() {
        gameController.startNewGame();
    }
    
    public void showInstructions() {
        menuView.showInstructions();
    }
    
    public void exitGame() {
        System.exit(0);
    }
    
    public void setMenuVisible(boolean visible) {
        menuView.setVisible(visible);
    }
}
