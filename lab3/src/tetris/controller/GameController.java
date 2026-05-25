package tetris.controller;

import tetris.model.GameModel;
import tetris.model.HighScoresManager;
import tetris.view.TetrisView;
import javafx.scene.control.TextInputDialog;
import javafx.application.Platform;
import java.util.Optional;

public class GameController {
    private GameModel model;
    private TetrisView view;
    private HighScoresManager highScores;
    private boolean paused;

    public GameController(GameModel model, TetrisView view) {
        this.model = model;
        this.view = view;
        this.highScores = new HighScoresManager();
        this.paused = false;
    }

    public void moveLeft() {
        if (!paused && !model.isGameOver()) {
            model.moveLeft();
            view.update();
        }
    }

    public void moveRight() {
        if (!paused && !model.isGameOver()) {
            model.moveRight();
            view.update();
        }
    }

    public void moveDown() {
        if (!paused && !model.isGameOver()) {
            model.moveDown();
            view.update();
        }
    }

    public void rotate() {
        if (!paused && !model.isGameOver()) {
            model.rotateShape();
            view.update();
        }
    }

    public void hardDrop() {
        if (!paused && !model.isGameOver()) {
            System.out.println("HARD DROP - starting");
            while (model.moveDown()) {
                // Быстрое падение до конца
            }
            System.out.println("HARD DROP - finished");
            view.update();
        }
    }

    public void update() {
        if (!paused && !model.isGameOver()) {
            model.moveDown();
            view.update();
        }
    }

    public void pause() {
        if (!model.isGameOver()) {
            paused = !paused;
            if (paused) {
                System.out.println("Game Paused - Press ESC to resume");
            } else {
                System.out.println("Game Resumed");
            }
        }
    }

    public void handleGameOver() {
        if (model.isGameOver()) {
            int finalScore = model.getScore();
            System.out.println("=== GAME OVER ===");
            System.out.println("Final score: " + finalScore);

            // ВСЕГДА показываем диалог для нового рекорда, если это высокий результат
            if (highScores.isHighScore(finalScore)) {
                System.out.println("NEW HIGH SCORE! Showing dialog...");

                // Запускаем диалог в потоке JavaFX
                Platform.runLater(() -> {
                    TextInputDialog dialog = new TextInputDialog("Player");
                    dialog.setTitle("New High Score!");
                    dialog.setHeaderText("Congratulations! You got " + finalScore + " points!");
                    dialog.setContentText("Enter your name:");

                    // Показываем диалог и ждем результат
                    Optional<String> result = dialog.showAndWait();

                    String playerName;
                    if (result.isPresent() && !result.get().trim().isEmpty()) {
                        playerName = result.get().trim();
                    } else {
                        playerName = "Anonymous";
                    }

                    // Сохраняем рекорд
                    highScores.addScore(playerName, finalScore);
                    System.out.println("Score saved for: " + playerName);

                    // Проверяем, что рекорд действительно сохранился
                    System.out.println("Top scores now:");
                    for (var entry : highScores.getTopScores()) {
                        System.out.println("  " + entry.name + ": " + entry.score);
                    }
                });
            } else {
                System.out.println("Not a high score. Score: " + finalScore);
                System.out.println("Need at least " + (highScores.getTopScores().isEmpty() ? 0 :
                        highScores.getTopScores().get(highScores.getTopScores().size() - 1).score) + " points for top 10");
            }
        }
    }

    public void newGame() {
        model.initGame();
        paused = false;
        view.update();
    }
}