package io.aaron.learning.manage.factory.base;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;

import java.util.List;

public interface AbstractShapeFactory {
    AbstractShape getShape(ShapeType type);
    AbstractShapeGroup getShapeGroup(List<AbstractShape> shapes);
    AbstractBoundDecorator getBoundDecorator(AbstractShape shape);
}
