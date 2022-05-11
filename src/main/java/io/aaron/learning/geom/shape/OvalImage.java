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
public class OvalImage extends AbstractShape {
    private Boolean circle;

    public OvalImage() {
        super(120.0, 60.0);
        this.circle = false;
    }

    public OvalImage(Double diameter) {
        super(diameter, diameter);
        this.circle = true;
    }

    public OvalImage(Double x, Double y, Double width, Double height) {
        super(x, y, width, height);
        this.circle = false;
    }

    @Override
    public Node draw() {
        Canvas canvas = getCanvas();
        if(canvas == null) {
            setCanvas(new Canvas(getWidth() + this.getLineWidth() * 2, getHeight() + this.getLineWidth() * 2));
            canvas = getCanvas();
        }
        else {
            canvas.setHeight(getHeight() + this.getLineWidth() * 2);
            canvas.setWidth(getWidth() + this.getLineWidth() * 2);
        }
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(this.getFillPaint());
        context.setStroke(this.getStrokePaint());
        if(getFilled()) {
            context.fillOval(this.getLineWidth(), this.getLineWidth(), getWidth(), getHeight());
        }
        context.strokeOval(this.getLineWidth(), this.getLineWidth(), getWidth(), getHeight());
        return canvas;
    }

    @Override
    public AbstractShape getDefault() {
        return new OvalImage();
    }

    public AbstractShape getCircleDefault(boolean circle) {
        return new OvalImage(80.0);
    }
}
