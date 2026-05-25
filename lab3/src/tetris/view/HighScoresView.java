package tetris.view;

import tetris.model.HighScoresManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HighScoresView {
    private Stage stage;
    private HighScoresManager highScores;

    public HighScoresView(Stage stage) {
        this.stage = stage;
        this.highScores = new HighScoresManager();
    }

    public void show() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2b2b2b;");

        Text title = new Text("HIGH SCORES");
        title.setStyle("-fx-font-size: 32px; -fx-fill: white; -fx-font-weight: bold;");

        VBox scoresBox = new VBox(5);
        scoresBox.setAlignment(Pos.CENTER);

        int rank = 1;
        for (HighScoresManager.ScoreEntry entry : highScores.getTopScores()) {
            Text scoreText = new Text(rank + ". " + entry.name + " - " + entry.score);
            scoreText.setStyle("-fx-font-size: 18px; -fx-fill: lightgray;");
            scoresBox.getChildren().add(scoreText);
            rank++;
        }

        if (highScores.getTopScores().isEmpty()) {
            Text emptyText = new Text("No scores yet!");
            emptyText.setStyle("-fx-font-size: 18px; -fx-fill: lightgray;");
            scoresBox.getChildren().add(emptyText);
        }

        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px; -fx-background-color: #4a4a4a; -fx-text-fill: white;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px; -fx-background-color: #6a6a6a; -fx-text-fill: white;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px; -fx-background-color: #4a4a4a; -fx-text-fill: white;"));
        backBtn.setOnAction(e -> {
            MainMenuView menu = new MainMenuView(stage);
            stage.setScene(menu.getScene());
        });

        root.getChildren().addAll(title, scoresBox, backBtn);

        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("High Scores");
        stage.show();
    }
}