package tetris.controller;

import tetris.model.GameModel;
import tetris.view.TetrisView;
import tetris.view.MainMenuView;
import tetris.view.HighScoresView;
import tetris.view.AboutView;
import javafx.stage.Stage;

public class MainMenuController {
    private Stage stage;
    private MainMenuView view;

    public MainMenuController(Stage stage, MainMenuView view) {
        this.stage = stage;
        this.view = view;

        view.setOnNewGame(this::startNewGame);
        view.setOnHighScores(this::showHighScores);
        view.setOnAbout(this::showAbout);
        view.setOnExit(() -> System.exit(0));
    }

    private void startNewGame() {
        GameModel model = new GameModel();
        TetrisView gameView = new TetrisView(stage, view);
        GameController gameController = new GameController(model, gameView);

        gameView.setController(gameController);
        gameController.setupKeyboardControls(gameView.getScene());
        gameController.startGameLoop();

        stage.setScene(gameView.getScene());
        gameView.show();
        gameView.update(model);
        gameView.requestFocusForWindow();
    }

    private void showHighScores() {
        HighScoresView scoresView = new HighScoresView(stage, view);
        scoresView.show();
    }

    private void showAbout() {
        AboutView aboutView = new AboutView(stage, view);
        aboutView.show();
    }
}