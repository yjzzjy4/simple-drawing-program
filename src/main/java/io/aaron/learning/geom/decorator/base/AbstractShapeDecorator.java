package io.aaron.learning.geom.decorator.base;

import io.aaron.learning.geom.base.AbstractShape;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
        xProperty().bindBidirectional(shape.xProperty());
        yProperty().bindBidirectional(shape.yProperty());
        widthProperty().bindBidirectional(shape.widthProperty());
        heightProperty().bindBidirectional(shape.heightProperty());
        setLineDashes(new Double[] {5d});
        setLineWidth(1.0);
        setFilled(false);
        setStrokePaint(Color.web("#00b8f0"));
        setFillPaint(Color.web("#00b8f0"));
        setOpacity(1.0);
//        if(shape instanceof ShapeImageGroup) {
//            container.getChildren()
//                     .addAll(((ShapeImageGroup) shape).getChildren().stream().map(AbstractShape::draw).collect(Collectors.toList()));
//        }
//        else {
////            container.getChildren().add(shape.draw());
//            shape.draw();
//        }
        shape.draw();
    }
}
