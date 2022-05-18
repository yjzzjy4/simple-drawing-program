package io.aaron.learning.geom.decorator.base;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.shape.ShapeGroup;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractShapeDecorator extends AbstractShape {
    private AbstractShape shape;
    private Group container;

    {
        container = new Group();
    }

    protected AbstractShapeDecorator(AbstractShape shape) {
        this.shape = shape;
        setX(shape.getX());
        setY(shape.getY());
        setWidth(shape.getWidth());
        setHeight(shape.getHeight());
        setLineDashes(new Double[] {5d});
        setLineWidth(1.0);
        setFilled(false);
        setStrokePaint(Color.web("#00b8f0"));
        setFillPaint(Color.web("#00b8f0"));
        setOpacity(1.0);
        if(shape instanceof ShapeGroup) {
            container.getChildren()
                     .addAll(((ShapeGroup) shape).getChildren().stream().map(AbstractShape::draw).collect(Collectors.toList()));
        }
        else {
            container.getChildren().add(shape.draw());
        }
    }

//    @Override
//    public void setX(Double x) {
//        super.setX(x);
//        shape.setX(x);
//    }
//
//    @Override
//    public void setY(Double y) {
//        super.setY(y);
//        shape.setY(y);
//    }
}
