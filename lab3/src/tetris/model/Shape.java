package tetris.model;

import javafx.scene.paint.Color;
import java.util.*;

public abstract class Shape {
    protected List<RectanglePart> rectangles;
    protected Color color;
    protected int[][] shapeMatrix;
    protected int x, y;

    public Shape() {
        rectangles = new ArrayList<>();
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        updateRectangles();
    }

    protected abstract void updateRectangles();

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        updateRectangles();
    }

    public abstract void rotate();
    public abstract Shape clone();

    public List<RectanglePart> getRectangles() { return rectangles; }
    public Color getColor() { return color; }
    public int getX() { return x; }
    public int getY() { return y; }
}