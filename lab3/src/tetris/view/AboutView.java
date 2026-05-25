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

    public AboutView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 40 20 20 20;");

        // Градиентный фон как в главном меню
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(20, 30, 50)),
                new Stop(0.5, Color.rgb(40, 20, 60)),
                new Stop(1, Color.rgb(15, 25, 45)));
        root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        // Заголовок
        Text title = new Text("ABOUT TETRIS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setFill(Color.rgb(255, 100, 100));
        title.setEffect(new DropShadow(10, Color.rgb(255, 0, 0, 0.5)));

        // Разделитель
        Text divider = new Text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        divider.setFill(Color.rgb(255, 100, 100));
        divider.setFont(Font.font("Consolas", FontWeight.BOLD, 14));

        // Описание
        Text description = new Text(
                "Classic Tetris game on Java\n\n" +
                        " Features:\n" +
                        "  • Score and lines tracking\n" +
                        "  • High scores saved locally\n" +
                        " Controls:\n" +
                        "  ← →   – Move left/right\n" +
                        "  ↓     – Move down\n" +
                        "  ↑     – Rotate\n" +
                        "  SPACE – Hard drop\n" +
                        "  R     – Restart game\n" +
                        "  ESC   – Pause / Resume\n\n" +
                        " Architecture: MVC Pattern\n" +
                        " Developer: Course Project"
        );
        description.setFont(Font.font("Consolas", FontWeight.NORMAL, 13));
        description.setFill(Color.rgb(220, 220, 240));
        description.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        description.setEffect(new DropShadow(3, Color.rgb(0, 0, 0, 0.5)));

        // Кнопка назад
        Button backBtn = new Button("BACK TO MENU");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 12 40;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 3);"
        );
        backBtn.setOnMouseEntered(e -> backBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #66BB6A, #388E3C);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 12 40;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 12, 0, 0, 4);"
        ));
        backBtn.setOnMouseExited(e -> backBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 12 40;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 3);"
        ));
        backBtn.setOnAction(e -> {
            MainMenuView menu = new MainMenuView(stage);
            stage.setScene(menu.getScene());
        });

        root.getChildren().addAll(title, divider, description, backBtn);

        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.setTitle("About - Tetris");
        stage.setResizable(false);
        stage.show();
    }
}