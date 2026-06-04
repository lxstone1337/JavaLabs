package tetris.view;

import tetris.model.HighScoresManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

public class HighScoresView {
    private final Stage stage;
    private final HighScoresManager highScores;

    public HighScoresView(Stage stage) {
        this.stage = stage;
        this.highScores = new HighScoresManager();
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");

        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(20, 30, 50)),
                new Stop(0.5, Color.rgb(40, 20, 60)),
                new Stop(1, Color.rgb(15, 25, 45)));
        root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        Text title = new Text("HIGH SCORES");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        title.setFill(Color.rgb(255, 215, 0));
        title.setEffect(new DropShadow(8, Color.rgb(255, 200, 0, 0.5)));

        VBox scoresBox = new VBox(5);
        scoresBox.setAlignment(Pos.CENTER);
        scoresBox.setStyle("-fx-padding: 15; -fx-background-color: rgba(0,0,0,0.4); -fx-background-radius: 10;");

        var scores = highScores.getTopScores();

        if (scores.isEmpty()) {
            Text emptyText = new Text("No scores yet!");
            emptyText.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            emptyText.setFill(Color.rgb(200, 200, 200));
            scoresBox.getChildren().add(emptyText);
        } else {
            int rank = 1;
            for (HighScoresManager.ScoreEntry entry : scores) {
                Text scoreText = new Text(rank + ". " + entry.name + " - " + entry.score);
                scoreText.setFont(Font.font("Consolas", FontWeight.NORMAL, 16));
                scoreText.setFill(Color.rgb(220, 220, 240));
                if (rank == 1) scoreText.setFill(Color.rgb(255, 215, 0));
                if (rank == 2) scoreText.setFill(Color.rgb(192, 192, 192));
                if (rank == 3) scoreText.setFill(Color.rgb(205, 127, 50));
                scoresBox.getChildren().add(scoreText);
                rank++;
            }
        }

        Button backBtn = new Button("BACK");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 35;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 3);"
        );
        backBtn.setOnMouseEntered(e -> backBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #66BB6A, #388E3C);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 35;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 12, 0, 0, 4);"
        ));
        backBtn.setOnMouseExited(e -> backBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 35;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 3);"
        ));
        backBtn.setOnAction(e -> {
            MainMenuView menu = new MainMenuView(stage);
            stage.setScene(menu.getScene());
        });

        root.getChildren().addAll(title, scoresBox, backBtn);

        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.setTitle("High Scores");
        stage.setResizable(false);
        stage.show();
    }
}