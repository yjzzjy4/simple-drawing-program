package io.aaron.learning.manage.factory.base;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;

public interface AbstractShapeDecoratorFactory {
    AbstractShapeDecorator getBoundsShapeDecorator(ShapeType type);
}
