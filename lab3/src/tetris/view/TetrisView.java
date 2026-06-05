package tetris.view;

import tetris.model.GameModel;
import tetris.controller.GameController;
import tetris.model.RectanglePart;
import tetris.model.Shape;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.application.Platform;

public class TetrisView {
    private final Stage stage;
    private Scene scene;
    private Pane gameArea;
    private Rectangle[][] grid;
    private Rectangle[][] nextShapeGrid;
    private Text scoreText;
    private Text linesText;
    private Text pauseText;
    private boolean isGameOverShown = false;
    private MainMenuView mainMenuView;
    private GameController controller;

    private int[][] mesh;
    private int score;
    private int lines;
    private boolean gameOver;
    private Shape currentShape;
    private Shape nextShape;

    private static final int NEXT_COLS = 6;
    private static final int NEXT_ROWS = 4;
    private static final int CELL_SIZE = 30;


    public TetrisView(Stage stage, MainMenuView mainMenuView) {
        this.stage = stage;
        this.mainMenuView = mainMenuView;
        initUI();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void initUI() {
        BorderPane root = new BorderPane();
        root.setPadding(Insets.EMPTY);

        LinearGradient backgroundGradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(20, 25, 45)),
                new Stop(0.5, Color.rgb(35, 20, 55)),
                new Stop(1, Color.rgb(15, 20, 40)));
        root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(backgroundGradient, null, null)));

        int size = GameModel.SIZE;
        int width = GameModel.WIDTH;
        int height = GameModel.HEIGHT;
        int nextWidth = NEXT_COLS * CELL_SIZE;
        int nextHeight = NEXT_ROWS * CELL_SIZE;
        int xmax = size * width;
        int ymax = size * height;

        gameArea = new Pane();
        gameArea.setStyle("-fx-background-color: #0a0f1e;");
        gameArea.setPrefSize(xmax, ymax);
        gameArea.setEffect(new DropShadow(15, Color.rgb(0, 0, 0, 0.5)));

        grid = new Rectangle[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Rectangle rect = new Rectangle(size - 1, size - 1);
                rect.setX(x * size);
                rect.setY(y * size);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.rgb(100, 110, 140, 0.4));
                rect.setStrokeWidth(0.8);
                grid[x][y] = rect;
                gameArea.getChildren().add(rect);
            }
        }

        root.setCenter(gameArea);
        BorderPane.setAlignment(gameArea, Pos.CENTER_LEFT);
        BorderPane.setMargin(gameArea, Insets.EMPTY);

        VBox rightPanel = new VBox(20);
        rightPanel.setStyle("-fx-padding: 20 30 20 30; -fx-background-color: rgba(0,0,0,0.3);");
        rightPanel.setPrefWidth(240);
        rightPanel.setAlignment(Pos.TOP_CENTER);

        Text gameTitle = new Text("TETRIS");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gameTitle.setFill(Color.rgb(255, 100, 100));
        gameTitle.setEffect(new DropShadow(8, Color.rgb(255, 0, 0, 0.5)));

        scoreText = new Text("Score: 0");
        scoreText.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
        scoreText.setFill(Color.rgb(255, 215, 0));
        scoreText.setEffect(new DropShadow(3, Color.rgb(0, 0, 0, 0.5)));

        linesText = new Text("Lines: 0");
        linesText.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
        linesText.setFill(Color.rgb(100, 200, 255));
        linesText.setEffect(new DropShadow(3, Color.rgb(0, 0, 0, 0.5)));

        Text nextLabel = new Text("NEXT SHAPE");
        nextLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nextLabel.setFill(Color.rgb(200, 200, 200));
        nextLabel.setEffect(new DropShadow(2, Color.rgb(0, 0, 0, 0.5)));

        Pane nextShapeArea = new Pane();
        nextShapeArea.setStyle("-fx-background-color: rgba(20, 30, 50, 0.8); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2;");
        nextShapeArea.setPrefSize(nextWidth, nextHeight);
        nextShapeArea.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.5)));

        for (int x = 0; x < NEXT_COLS; x++) {
            for (int y = 0; y < NEXT_ROWS; y++) {
                Rectangle rect = new Rectangle(CELL_SIZE - 1, CELL_SIZE - 1);
                rect.setX(x * CELL_SIZE);
                rect.setY(y * CELL_SIZE);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.rgb(80, 90, 120, 0.3));
                rect.setStrokeWidth(0.8);
                nextShapeArea.getChildren().add(rect);
            }
        }

        nextShapeGrid = new Rectangle[NEXT_COLS][NEXT_ROWS];
        for (int y = 0; y < NEXT_ROWS; y++) {
            for (int x = 0; x < NEXT_COLS; x++) {
                Rectangle rect = new Rectangle(CELL_SIZE - 1, CELL_SIZE - 1);
                rect.setX(x * CELL_SIZE);
                rect.setY(y * CELL_SIZE);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.rgb(120, 130, 160, 0.5));
                rect.setStrokeWidth(1.0);
                nextShapeGrid[x][y] = rect;
                nextShapeArea.getChildren().add(rect);
            }
        }

        Button menuButton = new Button("MAIN MENU");
        menuButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        menuButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 25;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 5, 0, 0, 2);"
        );
        menuButton.setOnAction(e -> returnToMenu());

        rightPanel.getChildren().addAll(gameTitle, scoreText, linesText, nextLabel, nextShapeArea, menuButton);
        root.setRight(rightPanel);
        BorderPane.setAlignment(rightPanel, Pos.TOP_RIGHT);
        BorderPane.setMargin(rightPanel, Insets.EMPTY);

        scene = new Scene(root, xmax + 280, ymax);
        scene.setFill(Color.TRANSPARENT);
    }

    public void update(GameModel model) {
        this.mesh = model.getMesh();
        this.score = model.getScore();
        this.lines = model.getLines();
        this.gameOver = model.isGameOver();
        this.currentShape = model.getCurrentShape();
        this.nextShape = model.getNextShape();
        render();
    }

    private void render() {
        int size = GameModel.SIZE;
        int width = GameModel.WIDTH;
        int height = GameModel.HEIGHT;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (mesh[x][y] == 1) {
                    grid[x][y].setFill(createBlockGradient(Color.rgb(120, 130, 160)));
                    grid[x][y].setStroke(Color.rgb(180, 190, 220));
                    grid[x][y].setStrokeWidth(1.2);
                    grid[x][y].setEffect(createLightingEffect(5.0));
                } else {
                    grid[x][y].setFill(Color.TRANSPARENT);
                    grid[x][y].setStroke(Color.rgb(100, 110, 140, 0.3));
                    grid[x][y].setEffect(null);
                }
            }
        }

        if (currentShape != null && !gameOver) {
            Color shapeColor = currentShape.getColor();
            Color brighter = shapeColor.brighter().brighter();
            for (RectanglePart rect : currentShape.getRectangles()) {
                int gx = rect.x / size;
                int gy = rect.y / size;
                if (gy >= 0 && gy < height && gx >= 0 && gx < width) {
                    grid[gx][gy].setFill(createBlockGradient(brighter));
                    grid[gx][gy].setStroke(shapeColor.brighter());
                    grid[gx][gy].setStrokeWidth(1.5);
                    grid[gx][gy].setEffect(createLightingEffect(8.0));
                }
            }
        }

        for (int x = 0; x < NEXT_COLS; x++) {
            for (int y = 0; y < NEXT_ROWS; y++) {
                nextShapeGrid[x][y].setFill(Color.TRANSPARENT);
                nextShapeGrid[x][y].setStroke(Color.rgb(120, 130, 160, 0.5));
                nextShapeGrid[x][y].setEffect(null);
            }
        }

        if (nextShape != null) {
            Color shapeColor = nextShape.getColor();
            Color brighter = shapeColor.brighter().brighter();

            int shapeWidth = 4;
            int offsetX = (NEXT_COLS - shapeWidth) / 2;

            for (RectanglePart rect : nextShape.getRectangles()) {
                int gx = (rect.x / size) - (width / 2 - 2) + offsetX;
                int gy = rect.y / size;
                if (gx >= 0 && gx < NEXT_COLS && gy >= 0 && gy < NEXT_ROWS) {
                    nextShapeGrid[gx][gy].setFill(createBlockGradient(brighter));
                    nextShapeGrid[gx][gy].setEffect(createLightingEffect(5.0));
                }
            }
        }

        scoreText.setText("Score: " + score);
        linesText.setText("Lines: " + lines);

        if (gameOver && !isGameOverShown) {
            showGameOver();
            isGameOverShown = true;
        } else if (!gameOver && isGameOverShown) {
            hideGameOverMessage();
            isGameOverShown = false;
        }
    }

    private LinearGradient createBlockGradient(Color baseColor) {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, baseColor),
                new Stop(0.3, baseColor),
                new Stop(0.7, baseColor.darker()),
                new Stop(1, baseColor.darker().darker()));
    }

    private Lighting createLightingEffect(double surfaceScale) {
        Lighting lighting = new Lighting();
        Light.Distant light = new Light.Distant();
        light.setAzimuth(135);
        light.setElevation(30);
        lighting.setLight(light);
        lighting.setSurfaceScale(surfaceScale);
        return lighting;
    }

    private void showGameOver() {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setLayoutX(GameModel.XMAX / 2 - 150);
        box.setLayoutY(GameModel.YMAX / 2 - 80);
        box.setPrefWidth(300);
        box.setUserData("game_over");

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 44));
        gameOverText.setFill(Color.rgb(255, 80, 80));
        gameOverText.setEffect(new DropShadow(10, Color.rgb(255, 0, 0, 0.7)));
        gameOverText.setTextAlignment(TextAlignment.CENTER);

        Text hint = new Text("Press  R  to Restart\nPress ESC to Menu");
        hint.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        hint.setFill(Color.rgb(255, 255, 100));
        hint.setEffect(new DropShadow(4, Color.rgb(0, 0, 0, 0.5)));
        hint.setTextAlignment(TextAlignment.CENTER);

        box.getChildren().addAll(gameOverText, hint);
        gameArea.getChildren().add(box);

        if (controller != null) {
            controller.handleGameOver();
        }
    }

    public void hideGameOverMessage() {
        gameArea.getChildren().removeIf(node -> node instanceof VBox && "game_over".equals(node.getUserData()));
    }

    public void showPauseMessage(boolean paused) {
        if (paused && pauseText == null) {
            pauseText = new Text("PAUSED\nPress ESC to resume");
            pauseText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            pauseText.setFill(Color.YELLOW);
            pauseText.setTextAlignment(TextAlignment.CENTER);
            pauseText.setX(GameModel.XMAX / 2 - 100);
            pauseText.setY(GameModel.YMAX / 2);
            pauseText.setUserData("pause");
            gameArea.getChildren().add(pauseText);
        } else if (!paused && pauseText != null) {
            gameArea.getChildren().remove(pauseText);
            pauseText = null;
        }
    }

    public void hidePauseMessage() {
        if (pauseText != null) {
            gameArea.getChildren().remove(pauseText);
            pauseText = null;
        }
    }

    public void requestFocusForWindow() {
        Platform.runLater(() -> {
            if (stage.getScene() != null && stage.getScene().getWindow() != null) {
                stage.getScene().getWindow().requestFocus();
            }
            stage.requestFocus();
        });
    }

    public void show() {
        stage.setTitle("Tetris");
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.show();
        }
        requestFocusForWindow();
        scene.getRoot().requestFocus();
    }

    public void returnToMenu() {
        if (controller != null) {
            controller.stopGameLoop();
        }
        scene.setOnKeyPressed(null);

        stage.setScene(mainMenuView.getScene());

        Platform.runLater(() -> {
            if (stage.getScene() != null && stage.getScene().getWindow() != null) {
                stage.getScene().getWindow().requestFocus();
            }
        });
    }
    public Scene getScene() {
        return scene;
    }
}