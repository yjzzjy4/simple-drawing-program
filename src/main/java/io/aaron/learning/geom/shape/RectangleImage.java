package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.AbstractShape;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class RectangleImage extends AbstractShape {

    public RectangleImage() {
        super(120.0, 60.0);
    }

    public RectangleImage(RectangleImage rect) {
        super(rect);
    }

    public RectangleImage(double width, double height) {
        super(width, height);
    }

    public RectangleImage(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public Node draw() {
        GraphicsContext context = ((Canvas) super.draw()).getGraphicsContext2D();
        if(isFilled()) {
            context.fillRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        }
        if(isLined()) {
            context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        }
        return getCanvas();
    }

    @Override
    public boolean contains(double x, double y) {
        return x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight();
    }

    @Override
    public RectangleImage clone() {
        return new RectangleImage(this);
    }
}
