package io.aaron.learning.manage.factory;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.OvalImage;
import io.aaron.learning.geom.shape.RectangleImage;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;

public class ShapeImageFactory implements AbstractShapeFactory {

    @Override
    public AbstractShape getRectangle() {
        return new RectangleImage();
    }

    @Override
    public AbstractShape getSquare() {
        return new RectangleImage(80.0);
    }

    @Override
    public AbstractShape getOval() {
        return new OvalImage();
    }

    @Override
    public AbstractShape getCircle() {
        return new OvalImage(80.0);
    }

    @Override
    public AbstractShape getBoundsShape(AbstractShape shape) {
        return new ShapeImageBoundsDecorator(shape);
    }
}
