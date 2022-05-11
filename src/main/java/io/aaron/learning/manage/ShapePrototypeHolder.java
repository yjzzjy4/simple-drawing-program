package io.aaron.learning.manage;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.shape.OvalImage;
import io.aaron.learning.geom.shape.RectangleImage;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapePrototypeHolder {
    public static final AbstractShapeFactory shapeFactory = FactoryProvider.provideAbstractShapeFactory();

    public static final Map<ShapeType, AbstractShape> PROTOTYPES = new ConcurrentHashMap<>();

    public static void add(ShapeType type, AbstractShape shape) {
        PROTOTYPES.put(type, shape);
    }

    static {
        PROTOTYPES.put(ShapeType.RECTANGLE, shapeFactory.getRectangle());
        PROTOTYPES.put(ShapeType.SQUARE, shapeFactory.getSquare());
        PROTOTYPES.put(ShapeType.OVAL, shapeFactory.getOval());
        PROTOTYPES.put(ShapeType.CIRCLE, shapeFactory.getCircle());
    }
}
