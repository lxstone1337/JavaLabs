package tetris;

import tetris.view.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class TetrisApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenuView menu = new MainMenuView(primaryStage);
        primaryStage.setScene(menu.getScene());
        primaryStage.setTitle("Tetris");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}