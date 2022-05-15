package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.AbstractShape;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShapeGroup extends AbstractShape {

    private List<AbstractShape> children;

    @Override
    public Node draw() {
        for(AbstractShape shape: children) {
            shape.draw();
        }
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public AbstractShape clone() {
        return null;
    }
}
