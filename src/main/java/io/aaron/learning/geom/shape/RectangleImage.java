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
    private Boolean square;

    public RectangleImage() {
        super(120.0, 60.0);
        this.square = false;
    }

    public RectangleImage(Double length) {
        super(length, length);
        this.square = true;
    }

    public RectangleImage(Double x, Double y, Double width, Double height) {
        super(x, y, width, height);
        this.square = false;
    }

    @Override
    public Node draw() {
        Canvas canvas = getCanvas();
        if(canvas == null) {
            setCanvas(new Canvas(getWidth() + 2 * getLineWidth(), getHeight() + 2 * getLineWidth()));
            canvas = getCanvas();
        }
        else {
            canvas.setHeight(getHeight() + 2 * getLineWidth());
            canvas.setWidth(getWidth() + 2 * getLineWidth());
        }
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(getFillPaint());
        context.setStroke(getStrokePaint());
        if(getFilled()) {
            context.fillRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        }
        context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        return canvas;
    }

    @Override
    public AbstractShape getDefault() {
        return new RectangleImage();
    }

    public AbstractShape getSquareDefault(boolean square) {
        return new RectangleImage(80.0);
    }

    @Override
    public boolean contains(double x, double y) {
        return super.contains(x, y);
    }
}
