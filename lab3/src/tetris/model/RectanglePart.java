package tetris.model;

import java.io.Serializable;

public class RectanglePart implements Serializable {
    public int x;
    public int y;

    public RectanglePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}