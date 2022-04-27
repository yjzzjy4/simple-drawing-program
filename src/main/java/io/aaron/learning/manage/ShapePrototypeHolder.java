package io.aaron.learning.manage;

import io.aaron.learning.geom.AbstractShape;
import io.aaron.learning.geom.impl.OvalImage;
import io.aaron.learning.geom.impl.RectangleImage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapePrototypeHolder {
    public static final Map<ShapeType, AbstractShape> PROTOTYPES = new ConcurrentHashMap<>();

    public static void add(ShapeType type, AbstractShape shape) {
        PROTOTYPES.put(type, shape);
    }

    static {
        PROTOTYPES.put(ShapeType.RECTANGLE, new RectangleImage());
        PROTOTYPES.put(ShapeType.SQUARE, new RectangleImage(true));
        PROTOTYPES.put(ShapeType.OVAL, new OvalImage());
        PROTOTYPES.put(ShapeType.CIRCLE, new OvalImage(true));
    }
}
