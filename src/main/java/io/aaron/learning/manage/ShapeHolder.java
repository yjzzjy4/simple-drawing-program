package io.aaron.learning.manage;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import javafx.scene.canvas.Canvas;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ShapeHolder extends Canvas {
    public static final List<AbstractShapeDecorator> SHAPES = new CopyOnWriteArrayList<>();

    public static List<AbstractShapeDecorator> getAllShapes() {
        return SHAPES;
    }

    public static List<AbstractShapeDecorator> getSelectedShapes() {
        return SHAPES.stream()
                     .filter(AbstractShape::getSelected)
                     .collect(Collectors.toList());
    }

    public static void add(double x, double y, @NonNull AbstractShapeDecorator decorator) {
        decorator.setX(x);
        decorator.setY(y);
        SHAPES.add(decorator);
    }
}
