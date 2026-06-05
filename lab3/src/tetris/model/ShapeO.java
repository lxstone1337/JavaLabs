package tetris.model;

import javafx.scene.paint.Color;

public class ShapeO extends Shape {
    private final int[][] shape = {
            {1, 1}, {1, 1}
    };

    public ShapeO() {
        color = Color.YELLOW;
        shapeMatrix = shape;
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
    }
}