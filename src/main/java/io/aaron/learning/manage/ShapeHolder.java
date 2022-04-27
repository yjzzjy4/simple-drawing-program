package io.aaron.learning.manage;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.canvas.Canvas;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShapeHolder extends Canvas {
    public static final List<AbstractShape> SHAPES = new CopyOnWriteArrayList<>();

    public static List<AbstractShape> getAllShapes() {
        return SHAPES;
    }

    public static void add(AbstractShape shape) {
        SHAPES.add(shape);
    }
}
