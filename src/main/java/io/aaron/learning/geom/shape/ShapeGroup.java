package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.AbstractShape;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShapeGroup extends AbstractShape {

    private List<AbstractShape> children;

    public ShapeGroup(@NonNull List<AbstractShape> shapes) {
        this.children = shapes;
        // top left (x, y), bottom right (x, y);
        final double[] anchors = {Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
        shapes.forEach(s -> {
            double x = s.getX(), y = s.getY();
            anchors[0] = Math.min(x, anchors[0]);
            anchors[1] = Math.min(y, anchors[1]);
            anchors[2] = Math.max(x + s.getWidth(), anchors[2]);
            anchors[3] = Math.max(y + s.getHeight(), anchors[3]);
            System.out.println("x: " + x + ", " + ", y: " + y + ", width: " + s.getWidth() + ", height: " + s.getHeight());
        });
        setX(anchors[0]);
        setY(anchors[1]);
        setWidth(Math.abs(anchors[2] - anchors[0]));
        setHeight(Math.abs(anchors[3] - anchors[1]));
        System.out.println(Arrays.toString(anchors));
        // recalibrate shape;
        shapes.forEach(s -> {
            s.getCanvas().layoutXProperty().set(s.getX() - getX());
            s.getCanvas().layoutYProperty().set(s.getY() - getY());
        });
        draw();
    }

    @Override
    public Node draw() {
        GraphicsContext context = ((Canvas) super.draw()).getGraphicsContext2D();
        context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        for(AbstractShape shape: children) {
            shape.draw();
        }
        return getCanvas();
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
