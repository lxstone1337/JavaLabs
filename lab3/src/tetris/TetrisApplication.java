package tetris;

import tetris.view.MainMenuView;
import tetris.controller.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class TetrisApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenuView menuView = new MainMenuView(primaryStage);

        MainMenuController menuController = new MainMenuController(primaryStage, menuView);

        primaryStage.setScene(menuView.getScene());
        primaryStage.setTitle("Tetris");
        primaryStage.show();
    }
}