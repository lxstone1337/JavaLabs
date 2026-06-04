package tetris.model;

import java.util.*;

public class GameModel {
    public static int SIZE = 30;
    public static final int WIDTH = 12;
    public static final int HEIGHT = 24;
    public static int XMAX = SIZE * WIDTH;
    public static int YMAX = SIZE * HEIGHT;

    private int[][] mesh;
    private int score;
    private int lines;
    private boolean gameOver;
    private Shape currentShape;
    private Shape nextShape;
    private Random random;

    public GameModel() {
        mesh = new int[WIDTH][HEIGHT];
        random = new Random();
        initGame();
    }

    public void initGame() {
        for (int i = 0; i < WIDTH; i++) Arrays.fill(mesh[i], 0);
        score = 0;
        lines = 0;
        gameOver = false;
        nextShape = createRandomShape();
        spawnNewShape();
    }

    private Shape createRandomShape() {
        int block = random.nextInt(7);
        switch (block) {
            case 0: return new ShapeI();
            case 1: return new ShapeJ();
            case 2: return new ShapeL();
            case 3: return new ShapeO();
            case 4: return new ShapeS();
            case 5: return new ShapeZ();
            default: return new ShapeT();
        }
    }

    public void spawnNewShape() {
        currentShape = nextShape;
        nextShape = createRandomShape();
        currentShape.setPosition(WIDTH / 2 - 1, 0);
        if (checkCollision()) gameOver = true;
    }

    public boolean moveDown() {
        if (gameOver) return false;
        currentShape.move(0, 1);
        if (checkCollision()) {
            currentShape.move(0, -1);
            mergeShape();
            clearLines();
            spawnNewShape();
            return false;
        }
        return true;
    }

    public void moveLeft() {
        if (gameOver) return;
        currentShape.move(-1, 0);
        if (checkCollision()) currentShape.move(1, 0);
    }

    public void moveRight() {
        if (gameOver) return;
        currentShape.move(1, 0);
        if (checkCollision()) currentShape.move(-1, 0);
    }

    public void rotateShape() {
        if (gameOver) return;
        currentShape.rotate();
        if (checkCollision()) {
            currentShape.rotate();
            currentShape.rotate();
            currentShape.rotate();
        }
    }

    private boolean checkCollision() {
        for (RectanglePart rect : currentShape.getRectangles()) {
            int gx = rect.x / SIZE;
            int gy = rect.y / SIZE;
            if (gx < 0 || gx >= WIDTH || gy >= HEIGHT) return true;
            if (gy >= 0 && mesh[gx][gy] == 1) return true;
        }
        return false;
    }

    private void mergeShape() {
        for (RectanglePart rect : currentShape.getRectangles()) {
            int gx = rect.x / SIZE;
            int gy = rect.y / SIZE;
            if (gy >= 0 && gy < HEIGHT && gx >= 0 && gx < WIDTH)
                mesh[gx][gy] = 1;
        }
    }

    private void clearLines() {
        int cleared = 0;
        for (int y = HEIGHT - 1; y >= 0; y--) {
            boolean full = true;
            for (int x = 0; x < WIDTH; x++) {
                if (mesh[x][y] == 0) { full = false; break; }
            }
            if (full) {
                for (int yy = y; yy > 0; yy--)
                    for (int x = 0; x < WIDTH; x++)
                        mesh[x][yy] = mesh[x][yy-1];
                for (int x = 0; x < WIDTH; x++) mesh[x][0] = 0;
                cleared++;
                y++;
            }
        }
        if (cleared > 0) {
            lines += cleared;
            score += cleared * 100;
        }
    }

    public int[][] getMesh() { return mesh; }
    public int getScore() { return score; }
    public int getLines() { return lines; }
    public boolean isGameOver() { return gameOver; }
    public Shape getCurrentShape() { return currentShape; }
    public Shape getNextShape() { return nextShape; }
}