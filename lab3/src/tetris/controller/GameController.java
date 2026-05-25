package tetris.controller;

import tetris.model.GameModel;
import tetris.model.HighScoresManager;
import tetris.view.TetrisView;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import java.util.Optional;

public class GameController {
    private GameModel model;
    private TetrisView view;
    private HighScoresManager highScores;
    private boolean paused;
    private boolean gameOverHandled;

    public GameController(GameModel model, TetrisView view) {
        this.model = model;
        this.view = view;
        this.highScores = new HighScoresManager();
        this.paused = false;
        this.gameOverHandled = false;
    }

    public void setupKeyboardControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            System.out.println("Key pressed: " + code);

            if (model.isGameOver()) {
                if (code == KeyCode.R) restartGame();
                else if (code == KeyCode.ESCAPE) view.returnToMenu();
                event.consume();
                return;
            }

            switch (code) {
                case LEFT -> moveLeft();
                case RIGHT -> moveRight();
                case DOWN -> moveDown();
                case UP -> rotate();
                case SPACE -> hardDrop();
                case R -> restartGame();
                case ESCAPE -> pause();
                default -> {}
            }
            event.consume();
        });
    }

    public void moveLeft() {
        if (!paused && !model.isGameOver()) {
            model.moveLeft();
            view.update(model);
        }
    }

    public void moveRight() {
        if (!paused && !model.isGameOver()) {
            model.moveRight();
            view.update(model);
        }
    }

    public void moveDown() {
        if (!paused && !model.isGameOver()) {
            model.moveDown();
            view.update(model);
        }
    }

    public void rotate() {
        if (!paused && !model.isGameOver()) {
            model.rotateShape();
            view.update(model);
        }
    }

    public void hardDrop() {
        if (!paused && !model.isGameOver()) {
            while (model.moveDown()) { }
            view.update(model);
        }
    }

    public void update() {
        if (!paused && !model.isGameOver()) {
            model.moveDown();
            view.update(model);
        }
    }

    public void pause() {
        if (!model.isGameOver()) {
            paused = !paused;
            view.showPauseMessage(paused);
        }
    }

    public void restartGame() {
        model.initGame();
        paused = false;
        gameOverHandled = false;
        view.update(model);
        view.hideGameOverMessage();
        view.hidePauseMessage();
        view.requestFocusForWindow();
    }

    public void handleGameOver() {
        // Защита от повторного вызова
        if (gameOverHandled) return;

        // Проверяем, что игра действительно окончена
        if (!model.isGameOver()) return;

        gameOverHandled = true;

        int finalScore = model.getScore();
        System.out.println("Game Over! Final score: " + finalScore);

        if (highScores.isHighScore(finalScore)) {
            System.out.println("New high score! Showing dialog...");
            Platform.runLater(() -> {
                TextInputDialog dialog = new TextInputDialog("Player");
                dialog.setTitle("New High Score!");
                dialog.setHeaderText("Congratulations! You got " + finalScore + " points!");
                dialog.setContentText("Enter your name:");
                Optional<String> result = dialog.showAndWait();
                String playerName = result.filter(s -> !s.trim().isEmpty()).orElse("Anonymous");
                highScores.addScore(playerName, finalScore);
                System.out.println("Score saved for: " + playerName);
            });
        } else {
            System.out.println("Not a high score.");
        }
    }
}