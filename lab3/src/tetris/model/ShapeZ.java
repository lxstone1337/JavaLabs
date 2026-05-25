package tetris.model;

import javafx.scene.paint.Color;

public class ShapeZ extends Shape {
    private int rotation = 0;
    private final int[][][] shapes = {
            {{1, 1, 0}, {0, 1, 1}},
            {{0, 1}, {1, 1}, {1, 0}}
    };

    public ShapeZ() {
        color = Color.RED;
        shapeMatrix = shapes[0];
        x = GameModel.WIDTH / 2 - 1;
        y = 0;
        updateRectangles();
    }

    protected void updateRectangles() {
        rectangles.clear();
        for (int row = 0; row < shapeMatrix.length; row++) {
            for (int col = 0; col < shapeMatrix[0].length; col++) {
                if (shapeMatrix[row][col] == 1) {
                    rectangles.add(new RectanglePart(
                            (x + col) * GameModel.SIZE,
                            (y + row) * GameModel.SIZE
                    ));
                }
            }
        }
    }

    @Override
    public void rotate() {
        rotation = (rotation + 1) % 2;
        shapeMatrix = shapes[rotation];
        updateRectangles();
    }

    @Override
    public Shape clone() {
        ShapeZ clone = new ShapeZ();
        clone.rotation = this.rotation;
        clone.shapeMatrix = this.shapeMatrix;
        clone.x = this.x;
        clone.y = this.y;
        clone.updateRectangles();
        return clone;
    }
}