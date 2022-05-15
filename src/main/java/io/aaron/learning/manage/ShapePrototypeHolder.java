package io.aaron.learning.manage;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.shape.CircleImage;
import io.aaron.learning.geom.shape.EllipseImage;
import io.aaron.learning.geom.shape.RectangleImage;
import io.aaron.learning.geom.shape.SquareImage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapePrototypeHolder {
    public static final Map<ShapeType, AbstractShape> PROTOTYPES = new ConcurrentHashMap<>();

    public static void add(ShapeType type, AbstractShape shape) {
        PROTOTYPES.put(type, shape);
    }

    static {
        PROTOTYPES.put(ShapeType.RECTANGLE, new RectangleImage());
        PROTOTYPES.put(ShapeType.SQUARE, new SquareImage());
        PROTOTYPES.put(ShapeType.ELLIPSE, new EllipseImage());
        PROTOTYPES.put(ShapeType.CIRCLE, new CircleImage());
    }
}
