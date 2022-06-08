package io.aaron.learning.manage;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.shape.EllipseImage;
import io.aaron.learning.geom.shape.RectangleImage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapePrototypeHolder {
    public static final Map<ShapeType, AbstractShape> PROTOTYPES = new ConcurrentHashMap<>();

    public static AbstractShape getShape(ShapeType type) {
        return PROTOTYPES.get(type).clone();
    }

    static {
        PROTOTYPES.put(ShapeType.RECTANGLE, new RectangleImage());
        PROTOTYPES.put(ShapeType.ELLIPSE, new EllipseImage());
    }
}
