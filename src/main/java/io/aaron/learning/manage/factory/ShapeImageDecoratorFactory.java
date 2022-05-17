package io.aaron.learning.manage.factory;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.manage.ShapePrototypeHolder;
import io.aaron.learning.manage.factory.base.AbstractShapeDecoratorFactory;

public class ShapeImageDecoratorFactory implements AbstractShapeDecoratorFactory {

    @Override
    public AbstractShapeDecorator getBoundsShapeDecorator(ShapeType type) {
        return new ShapeImageBoundsDecorator(ShapePrototypeHolder.getShape(type));
    }
}
