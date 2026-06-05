package tetris.view;

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

public class MainMenuView {
    private Stage stage;
    private Scene scene;

    private Button newGameBtn;
    private Button highScoresBtn;
    private Button aboutBtn;
    private Button exitBtn;

    public MainMenuView(Stage stage) {
        this.stage = stage;
        createMenu();
    }

    private void createMenu() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);

        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(20, 30, 50)),
                new Stop(0.5, Color.rgb(40, 20, 60)),
                new Stop(1, Color.rgb(15, 25, 45)));
        root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        Text title = new Text("TETRIS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 72));
        title.setFill(Color.rgb(255, 100, 100));
        title.setEffect(new DropShadow(15, Color.rgb(255, 0, 0, 0.7)));

        newGameBtn = createStyledButton("NEW GAME", "#4CAF50");
        highScoresBtn = createStyledButton("HIGH SCORES", "#2196F3");
        aboutBtn = createStyledButton("ABOUT", "#FF9800");
        exitBtn = createStyledButton("EXIT", "#f44336");

        newGameBtn.setOnAction(e -> {});
        highScoresBtn.setOnAction(e -> {});
        aboutBtn.setOnAction(e -> {});
        exitBtn.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(title, newGameBtn, highScoresBtn, aboutBtn, exitBtn);
        scene = new Scene(root, 500, 600);
    }

    public void setOnNewGame(Runnable action) {
        newGameBtn.setOnAction(e -> action.run());
    }

    public void setOnHighScores(Runnable action) {
        highScoresBtn.setOnAction(e -> action.run());
    }

    public void setOnAbout(Runnable action) {
        aboutBtn.setOnAction(e -> action.run());
    }

    public void setOnExit(Runnable action) {
        exitBtn.setOnAction(e -> action.run());
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        btn.setStyle("-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-padding: 12 35;" +
                "-fx-cursor: hand;" +
                "-fx-background-radius: 25;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 3);");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + adjustColor(color) + ";" +
                "-fx-text-fill: white;" +
                "-fx-padding: 12 35;" +
                "-fx-cursor: hand;" +
                "-fx-background-radius: 25;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 15, 0, 0, 5);"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-padding: 12 35;" +
                "-fx-cursor: hand;" +
                "-fx-background-radius: 25;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 3);"));
        return btn;
    }

    private String adjustColor(String color) {
        switch (color) {
            case "#4CAF50": return "#45a049";
            case "#2196F3": return "#0b7dda";
            case "#FF9800": return "#e68900";
            case "#f44336": return "#da190b";
            default: return color;
        }
    }

    public Scene getScene() {
        return scene;
    }
}