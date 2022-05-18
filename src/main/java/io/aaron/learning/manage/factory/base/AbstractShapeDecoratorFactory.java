package io.aaron.learning.manage.factory.base;

import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;

import java.util.List;

public interface AbstractShapeDecoratorFactory {
    AbstractShapeDecorator getBoundsShapeDecorator(ShapeType type);
    AbstractShapeDecorator getShapeGroupDecorator(List<AbstractShapeDecorator> shapes);
}
