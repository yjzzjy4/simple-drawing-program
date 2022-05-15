package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.AbstractShape;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EllipseImage extends AbstractShape {

    public EllipseImage() {
        super(120.0, 60.0);
    }

    public EllipseImage(EllipseImage ellipse) {
        super(ellipse);
    }

    public EllipseImage(double width, double height) {
        super(width, height);
    }

    public EllipseImage(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public Node draw() {
        GraphicsContext context = ((Canvas) super.draw()).getGraphicsContext2D();
        if(getFilled()) {
            context.fillOval(this.getLineWidth(), this.getLineWidth(), getWidth(), getHeight());
        }
        context.strokeOval(this.getLineWidth(), this.getLineWidth(), getWidth(), getHeight());
        return getCanvas();
    }

    @Override
    public boolean contains(double x, double y) {
        double offsetX = getWidth() / 2, offsetY = getHeight() / 2;
        return (Math.pow((x - offsetX) / offsetX, 2) + Math.pow((y - offsetY) / offsetY, 2)) <= 1;
    }

    @Override
    public EllipseImage clone() {
        return new EllipseImage(this);
    }
}
