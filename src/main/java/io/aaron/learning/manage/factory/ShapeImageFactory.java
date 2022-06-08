package io.aaron.learning.manage.factory;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.ShapeImageBoundDecorator;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.geom.shape.ShapeImageGroup;
import io.aaron.learning.manage.ShapePrototypeHolder;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;

import java.util.List;

public class ShapeImageFactory implements AbstractShapeFactory {
    @Override
    public AbstractShape getShape(ShapeType type) {
        return ShapePrototypeHolder.getShape(type);
    }

    @Override
    public AbstractShapeGroup getShapeGroup(List<AbstractShape> shapes) {
        return new ShapeImageGroup(shapes);
    }

    @Override
    public AbstractBoundDecorator getBoundDecorator(AbstractShape shape) {
        return new ShapeImageBoundDecorator(shape);
    }
}
