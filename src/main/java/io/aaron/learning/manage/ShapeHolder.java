package io.aaron.learning.manage;

import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import javafx.scene.canvas.Canvas;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShapeHolder extends Canvas {
    public static final List<AbstractShapeDecorator> SHAPES = new CopyOnWriteArrayList<>();

    public static List<AbstractShapeDecorator> getAllShapes() {
        return SHAPES;
    }

    public static void add(AbstractShapeDecorator shape) {
        SHAPES.add(shape);
    }
}
