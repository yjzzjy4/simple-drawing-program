package io.aaron.learning.manage;

import io.aaron.learning.geom.base.AbstractShape;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapePreviewHolder {
    public static final Map<ShapeType, AbstractShape> SHAPES = new ConcurrentHashMap<>();

    public static void add(ShapeType type, AbstractShape shape) {
        SHAPES.put(type, shape);
    }
}
