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

public class AboutView {
    private Stage stage;
    private MainMenuView mainMenuView;

    public AboutView(Stage stage, MainMenuView mainMenuView) {
        this.stage = stage;
        this.mainMenuView = mainMenuView;
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

        Text title = new Text("ABOUT");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setFill(Color.rgb(255, 100, 100));
        title.setEffect(new DropShadow(8, Color.rgb(255, 0, 0, 0.5)));

        Text info = new Text(
                "Tetris Classic\n\n" +
                        "Version 1.0\n\n" +
                        "Controls:\n" +
                        "← → - Move\n" +
                        "↓ - Down\n" +
                        "↑ - Rotate\n" +
                        "SPACE - Hard drop\n" +
                        "R - Restart\n" +
                        "ESC - Pause\n\n" +
                        "Clear lines to earn points!"
        );
        info.setFont(Font.font("Consolas", FontWeight.NORMAL, 14));
        info.setFill(Color.rgb(220, 220, 240));
        info.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        info.setEffect(new DropShadow(3, Color.rgb(0, 0, 0, 0.5)));

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
            stage.setScene(mainMenuView.getScene());
        });

        root.getChildren().addAll(title, info, backBtn);

        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.setTitle("About");
        stage.setResizable(false);
        stage.show();
    }
}