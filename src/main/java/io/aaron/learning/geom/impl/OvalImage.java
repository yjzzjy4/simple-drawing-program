package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
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
    private Canvas canvas;

    public OvalImage() {
        this(false);
    }

    public OvalImage(Boolean circle) {
        this.circle = circle;
        init(circle);
    }

    public OvalImage(Double diameter) {
        super(diameter, diameter);
        this.circle = true;
        super.encapsulateShape();
    }

    public OvalImage(Double x, Double y, Double width, Double height) {
        super(x, y, width, height);
        this.circle = false;
        super.encapsulateShape();
    }

    private void init(Boolean circle) {
        if(circle) {
            setWidth(80.0);
            setHeight(80.0);
        }
        else {
            setWidth(120.0);
            setHeight(60.0);
        }
        super.encapsulateShape();
    }

    @Override
    public Node draw() {
        Canvas canvas = new Canvas(getWidth() + 2, getHeight() + 2);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(getFill());
        context.setStroke(getStroke());
        if(getFilled()) {
            context.fillOval(0, 0, getWidth(), getHeight());
        }
        context.strokeOval(0, 0, getWidth(), getHeight());
        this.canvas = canvas;
        return canvas;
    }
}
