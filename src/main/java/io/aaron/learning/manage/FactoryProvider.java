package io.aaron.learning.manage;

import io.aaron.learning.manage.factory.ShapeImageFactory;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;

public class FactoryProvider {
    public static final AbstractShapeFactory shapeFactory = new ShapeImageFactory();

    public static AbstractShapeFactory provideAbstractShapeFactory() {
        return shapeFactory;
    }
}
