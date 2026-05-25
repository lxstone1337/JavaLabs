package tetris.view;

import tetris.model.GameModel;
import tetris.model.RectanglePart;
import tetris.controller.GameController;
import javafx.animation.AnimationTimer;
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
import javafx.scene.input.KeyCode;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class TetrisView {
    private Stage stage;
    private BorderPane root;
    private Scene scene;
    private GameController controller;
    private GameModel model;

    private Text scoreText;
    private Text linesText;
    private Rectangle[][] grid;
    private AnimationTimer gameLoop;
    private long lastUpdate;
    private Button menuButton;
    private Text gameOverText;
    private Text gameOverHintText;
    private boolean isGameOverShown = false;
    private Pane gameArea;

    // Для отображения следующей фигуры
    private Rectangle[][] nextShapeGrid;
    private Pane nextShapeArea;
    private Rectangle[][] nextShapeBackgroundGrid;

    public TetrisView(Stage stage) {
        this.stage = stage;
        this.model = new GameModel();
        this.controller = new GameController(model, this);
        initUI();
    }

    private void initUI() {
        root = new BorderPane();
        root.setPadding(new Insets(0)); // Убираем отступы

        // Градиентный фон
        LinearGradient backgroundGradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(20, 25, 45)),
                new Stop(0.5, Color.rgb(35, 20, 55)),
                new Stop(1, Color.rgb(15, 20, 40))
        );
        root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(backgroundGradient, null, null)
        ));

        // Устанавливаем размеры
        GameModel.SIZE = 30;
        GameModel.XMAX = GameModel.SIZE * GameModel.WIDTH;
        GameModel.YMAX = GameModel.SIZE * GameModel.HEIGHT;

        // Создание игрового поля - без отступов
        gameArea = new Pane();
        gameArea.setStyle("-fx-background-color: #0a0f1e;");
        gameArea.setPrefSize(GameModel.XMAX, GameModel.YMAX);

        // Тень для игрового поля
        DropShadow shadow = new DropShadow();
        shadow.setRadius(15);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));
        gameArea.setEffect(shadow);

        // Создание сетки для основного поля
        grid = new Rectangle[GameModel.WIDTH][GameModel.HEIGHT];
        for (int x = 0; x < GameModel.WIDTH; x++) {
            for (int y = 0; y < GameModel.HEIGHT; y++) {
                Rectangle rect = new Rectangle(GameModel.SIZE - 1, GameModel.SIZE - 1);
                rect.setX(x * GameModel.SIZE);
                rect.setY(y * GameModel.SIZE);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.rgb(100, 110, 140, 0.4));
                rect.setStrokeWidth(0.8);
                grid[x][y] = rect;
                gameArea.getChildren().add(rect);
            }
        }

        root.setCenter(gameArea);
        // Прижимаем игровое поле к левому краю
        BorderPane.setAlignment(gameArea, Pos.TOP_LEFT);

        // Правая панель с информацией
        VBox rightPanel = new VBox(20);
        rightPanel.setStyle("-fx-padding: 20 25 20 25; -fx-background-color: rgba(0,0,0,0.3);");
        rightPanel.setPrefWidth(240);
        rightPanel.setAlignment(Pos.TOP_CENTER);

        // Красивый шрифт для заголовка
        Text gameTitle = new Text("TETRIS");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gameTitle.setFill(Color.rgb(255, 100, 100));
        gameTitle.setEffect(new DropShadow(8, Color.rgb(255, 0, 0, 0.5)));

        // Текстовая информация
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

        // Область для следующей фигуры - фиксированный размер 120x120
        nextShapeArea = new Pane();
        nextShapeArea.setStyle("-fx-background-color: rgba(20, 30, 50, 0.8); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2;");
        nextShapeArea.setPrefSize(120, 120);
        nextShapeArea.setMaxSize(120, 120);
        nextShapeArea.setMinSize(120, 120);
        nextShapeArea.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.5)));

        // Создаем полную сетку 4x4 для фона следующей фигуры (120/4 = 30px на клетку)
        int nextCellSize = 30;
        nextShapeBackgroundGrid = new Rectangle[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Rectangle rect = new Rectangle(nextCellSize - 1, nextCellSize - 1);
                rect.setX(x * nextCellSize);
                rect.setY(y * nextCellSize);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.rgb(80, 90, 120, 0.4));
                rect.setStrokeWidth(0.8);
                nextShapeBackgroundGrid[x][y] = rect;
                nextShapeArea.getChildren().add(rect);
            }
        }

        // Создаем сетку для отображения фигуры
        nextShapeGrid = new Rectangle[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Rectangle rect = new Rectangle(nextCellSize - 1, nextCellSize - 1);
                rect.setX(x * nextCellSize);
                rect.setY(y * nextCellSize);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.TRANSPARENT);
                nextShapeGrid[x][y] = rect;
                nextShapeArea.getChildren().add(rect);
            }
        }

        // Кнопка меню
        menuButton = new Button("MAIN MENU");
        menuButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        menuButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 25;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 5, 0, 0, 2);"
        );
        menuButton.setOnMouseEntered(e -> menuButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #c0392b, #a93226);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 25;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 2);"
        ));
        menuButton.setOnMouseExited(e -> menuButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10 25;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 5, 0, 0, 2);"
        ));
        menuButton.setOnAction(e -> returnToMenu());

        rightPanel.getChildren().addAll(gameTitle, scoreText, linesText, nextLabel, nextShapeArea, menuButton);
        root.setRight(rightPanel);
        // Прижимаем правую панель к верху
        BorderPane.setAlignment(rightPanel, Pos.TOP_CENTER);

        // Создаем сцену с точными размерами
        scene = new Scene(root, GameModel.XMAX + 240, GameModel.YMAX);
        scene.setFill(Color.TRANSPARENT);

        setupKeyboardControls();
        startGameLoop();

        System.out.println("TetrisView initialized with size: " + GameModel.XMAX + "x" + GameModel.YMAX);
    }

    private void setupKeyboardControls() {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (model.isGameOver()) {
                if (code == KeyCode.R) {
                    restartGame();
                } else if (code == KeyCode.ESCAPE) {
                    returnToMenu();
                }
                event.consume();
                return;
            }

            switch (code) {
                case LEFT:
                    controller.moveLeft();
                    break;
                case RIGHT:
                    controller.moveRight();
                    break;
                case DOWN:
                    controller.moveDown();
                    break;
                case UP:
                    controller.rotate();
                    break;
                case SPACE:
                    controller.hardDrop();
                    break;
                case R:
                    restartGame();
                    break;
                case ESCAPE:
                    controller.pause();
                    break;
                default:
                    break;
            }
            event.consume();
        });

        gameArea.setOnMouseClicked(event -> {
            gameArea.requestFocus();
        });

        gameArea.setFocusTraversable(true);
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                if (now - lastUpdate > 300_000_000) {
                    if (!model.isGameOver()) {
                        controller.update();
                    }
                    lastUpdate = now;
                }
                render();
            }
        };
        gameLoop.start();
    }

    public void render() {
        // Отрисовка основного поля
        for (int x = 0; x < GameModel.WIDTH; x++) {
            for (int y = 0; y < GameModel.HEIGHT; y++) {
                if (model.getMesh()[x][y] == 1) {
                    grid[x][y].setFill(createBlockGradient(Color.rgb(120, 130, 160)));
                    grid[x][y].setStroke(Color.rgb(180, 190, 220));
                    grid[x][y].setStrokeWidth(1.2);

                    Lighting lighting = new Lighting();
                    Light.Distant light = new Light.Distant();
                    light.setAzimuth(135);
                    light.setElevation(30);
                    lighting.setLight(light);
                    lighting.setSurfaceScale(5.0);
                    grid[x][y].setEffect(lighting);
                } else {
                    grid[x][y].setFill(Color.TRANSPARENT);
                    grid[x][y].setStroke(Color.rgb(100, 110, 140, 0.3));
                    grid[x][y].setEffect(null);
                }
            }
        }

        // Отрисовка текущей фигуры
        if (model.getCurrentShape() != null && !model.isGameOver()) {
            Color shapeColor = model.getCurrentShape().getColor();
            Color brighterColor = shapeColor.brighter().brighter();

            for (RectanglePart rect : model.getCurrentShape().getRectangles()) {
                int gridX = rect.x / GameModel.SIZE;
                int gridY = rect.y / GameModel.SIZE;
                if (gridY >= 0 && gridY < GameModel.HEIGHT && gridX >= 0 && gridX < GameModel.WIDTH) {
                    grid[gridX][gridY].setFill(createBlockGradient(brighterColor));
                    grid[gridX][gridY].setStroke(shapeColor.brighter());
                    grid[gridX][gridY].setStrokeWidth(1.5);

                    Lighting lighting = new Lighting();
                    Light.Distant light = new Light.Distant();
                    light.setAzimuth(135);
                    light.setElevation(30);
                    lighting.setLight(light);
                    lighting.setSurfaceScale(8.0);
                    grid[gridX][gridY].setEffect(lighting);
                }
            }
        }

        // Отрисовка следующей фигуры
        drawNextShape();

        // Обновление текста
        scoreText.setText("Score: " + model.getScore());
        linesText.setText("Lines: " + model.getLines());

        // Проверка Game Over
        if (model.isGameOver() && !isGameOverShown) {
            showGameOver();
        }
    }

    private LinearGradient createBlockGradient(Color baseColor) {
        return new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, baseColor),
                new Stop(0.3, baseColor),
                new Stop(0.7, baseColor.darker()),
                new Stop(1, baseColor.darker().darker())
        );
    }

    private void drawNextShape() {
        // Очищаем область следующей фигуры
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                nextShapeGrid[x][y].setFill(Color.TRANSPARENT);
                nextShapeGrid[x][y].setEffect(null);
            }
        }

        // Рисуем следующую фигуру
        if (model.getNextShape() != null) {
            Color shapeColor = model.getNextShape().getColor();
            Color brighterColor = shapeColor.brighter().brighter();

            for (RectanglePart rect : model.getNextShape().getRectangles()) {
                int gridX = (rect.x / GameModel.SIZE) - (GameModel.WIDTH / 2 - 2);
                int gridY = rect.y / GameModel.SIZE;
                if (gridX >= 0 && gridX < 4 && gridY >= 0 && gridY < 4) {
                    nextShapeGrid[gridX][gridY].setFill(createBlockGradient(brighterColor));

                    Lighting lighting = new Lighting();
                    Light.Distant light = new Light.Distant();
                    light.setAzimuth(135);
                    light.setElevation(30);
                    lighting.setLight(light);
                    lighting.setSurfaceScale(5.0);
                    nextShapeGrid[gridX][gridY].setEffect(lighting);
                }
            }
        }
    }

    private void showGameOver() {
        isGameOverShown = true;

        VBox gameOverBox = new VBox(15);
        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.setLayoutX(GameModel.XMAX / 2 - 150);
        gameOverBox.setLayoutY(GameModel.YMAX / 2 - 80);
        gameOverBox.setPrefWidth(300);

        gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 44));
        gameOverText.setFill(Color.rgb(255, 80, 80));
        gameOverText.setEffect(new DropShadow(10, Color.rgb(255, 0, 0, 0.7)));
        gameOverText.setTextAlignment(TextAlignment.CENTER);

        gameOverHintText = new Text("Press  R  to Restart\nPress ESC to Menu");
        gameOverHintText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gameOverHintText.setFill(Color.rgb(255, 255, 100));
        gameOverHintText.setEffect(new DropShadow(4, Color.rgb(0, 0, 0, 0.5)));
        gameOverHintText.setTextAlignment(TextAlignment.CENTER);

        gameOverBox.getChildren().addAll(gameOverText, gameOverHintText);
        gameArea.getChildren().add(gameOverBox);

        controller.handleGameOver();

        javafx.animation.AnimationTimer blinkTimer = new javafx.animation.AnimationTimer() {
            private long lastBlink = 0;
            private boolean visible = true;

            @Override
            public void handle(long now) {
                if (lastBlink == 0) {
                    lastBlink = now;
                    return;
                }
                if (now - lastBlink > 500_000_000) {
                    visible = !visible;
                    gameOverText.setVisible(visible);
                    lastBlink = now;
                }
            }
        };
        blinkTimer.start();
    }

    private void restartGame() {
        System.out.println("Restarting game...");

        if (gameLoop != null) {
            gameLoop.stop();
        }

        model = new GameModel();
        controller = new GameController(model, this);

        gameArea.getChildren().removeIf(node -> node instanceof VBox);

        isGameOverShown = false;

        lastUpdate = 0;
        startGameLoop();

        render();
    }

    public void start() {
        stage.setScene(scene);
        stage.setTitle("Tetris Premium");
        stage.setResizable(false);
        stage.show();
        System.out.println("Game started - click on game area to enable controls");
    }

    public void returnToMenu() {
        System.out.println("Returning to menu...");
        if (gameLoop != null) {
            gameLoop.stop();
        }
        MainMenuView menu = new MainMenuView(stage);
        stage.setScene(menu.getScene());
    }

    public void update() {
        render();
    }
}