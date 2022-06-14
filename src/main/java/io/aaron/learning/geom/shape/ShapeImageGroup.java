package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import javafx.scene.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShapeImageGroup extends AbstractShapeGroup {

    public ShapeImageGroup(@NonNull List<AbstractShape> shapes) {
        setChildren(shapes);
        // top left (x, y), bottom right (x, y);
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for(AbstractShape s: shapes) {
            double x = s.getX(), y = s.getY();
            minX = Math.min(x, minX);
            minY = Math.min(y, minY);
            maxX = Math.max(x + s.getWidth(), maxX);
            maxY = Math.max(y + s.getHeight(), maxY);
        }
        setX(minX);
        setY(minY);
        setWidth(Math.abs(maxX - minX));
        setHeight(Math.abs(maxY - minY));
        draw();
    }

    @Override
    public Node draw() {
        for(AbstractShape shape: getChildren()) {
            shape.draw();
        }
        return super.draw();
    }

    @Override
    public boolean contains(double x, double y) {
        for(AbstractShape c : getChildren()) {
            if(c.contains(x + getX() - c.getX(), y + getY() - c.getY())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ShapeImageGroup clone() {
        return new ShapeImageGroup(getChildren().stream()
                                                .map(AbstractShape::clone)
                                                .collect(Collectors.toList()));
    }
}
