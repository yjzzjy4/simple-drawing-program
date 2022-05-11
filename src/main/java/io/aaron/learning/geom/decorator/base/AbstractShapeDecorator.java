package io.aaron.learning.geom.decorator.base;

import io.aaron.learning.geom.base.AbstractShape;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractShapeDecorator extends AbstractShape {
    private AbstractShape shape;

    protected AbstractShapeDecorator(AbstractShape shape) {
        this.shape = shape;
        setX(shape.getX());
        setY(shape.getY());
        setWidth(shape.getWidth());
        setHeight(shape.getHeight());
        setLineWidth(1.0);
        setFilled(false);
        setStrokePaint(Color.web("#00b8f0"));
        setFillPaint(Color.web("#00b8f0"));
    }
}
