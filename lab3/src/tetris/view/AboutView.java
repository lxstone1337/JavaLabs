package tetris.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AboutView {
    private Stage stage;

    public AboutView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2b2b2b;");

        Text title = new Text("About Tetris");
        title.setStyle("-fx-font-size: 28px; -fx-fill: white; -fx-font-weight: bold;");

        Text info = new Text(
                "Tetris Classic\n\n" +
                        "Version 1.0\n\n" +
                        "Controls:\n" +
                        "← → - Move left/right\n" +
                        "↓ - Move down\n" +
                        "↑ - Rotate\n" +
                        "SPACE - Hard drop\n" +
                        "ESC - Pause\n\n" +
                        "Clear lines to earn points!"
        );
        info.setStyle("-fx-font-size: 14px; -fx-fill: lightgray;");
        info.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px; -fx-background-color: #4a4a4a; -fx-text-fill: white;");
        backBtn.setOnAction(e -> {
            MainMenuView menu = new MainMenuView(stage);
            stage.setScene(menu.getScene());
        });

        root.getChildren().addAll(title, info, backBtn);

        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("About");
        stage.show();
    }
}