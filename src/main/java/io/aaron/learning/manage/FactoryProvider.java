package io.aaron.learning.manage;

import io.aaron.learning.manage.factory.ShapeImageDecoratorFactory;
import io.aaron.learning.manage.factory.base.AbstractShapeDecoratorFactory;

public class FactoryProvider {
    public static final AbstractShapeDecoratorFactory shapeFactory = new ShapeImageDecoratorFactory();

    public static AbstractShapeDecoratorFactory provideAbstractShapeFactory() {
        return shapeFactory;
    }
}
