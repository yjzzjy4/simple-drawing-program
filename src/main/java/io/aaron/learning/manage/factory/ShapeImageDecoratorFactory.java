package io.aaron.learning.manage.factory;

import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.decorator.ShapeImageGroupDecorator;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.manage.ShapePrototypeHolder;
import io.aaron.learning.manage.factory.base.AbstractShapeDecoratorFactory;

import java.util.List;

public class ShapeImageDecoratorFactory implements AbstractShapeDecoratorFactory {

    @Override
    public AbstractShapeDecorator getBoundsShapeDecorator(ShapeType type) {
        return new ShapeImageBoundsDecorator(ShapePrototypeHolder.getShape(type));
    }

    @Override
    public AbstractShapeDecorator getShapeGroupDecorator(List<AbstractShapeDecorator> shapes) {
        return new ShapeImageGroupDecorator(shapes);
    }
}
