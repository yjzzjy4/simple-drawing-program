package io.aaron.learning.manage.factory.base;

import io.aaron.learning.geom.base.AbstractShape;

public interface AbstractShapeFactory {
    AbstractShape getRectangle();
    AbstractShape getSquare();
    AbstractShape getOval();
    AbstractShape getCircle();
    AbstractShape getBoundsShape(AbstractShape shape);
}
