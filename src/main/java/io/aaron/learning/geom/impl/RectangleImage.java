package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
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
    private Canvas canvas;

    public RectangleImage() {
        this(false);
    }

    public RectangleImage(Boolean square) {
        super();
        this.square = square;
        init(square);
    }

    public RectangleImage(Double x, Double y, Double width, Double height) {
        super(x, y, width, height);
        this.square = false;
        super.encapsulateShape();
    }

    private void init(Boolean square) {
        if(square) {
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
            context.fillRect(0, 0, getWidth(), getHeight());
        }
        context.strokeRect(0, 0, getWidth(), getHeight());
        this.canvas = canvas;
        return canvas;
    }

    @Override
    public boolean contains(double x, double y) {
        return super.contains(x, y);
    }
}
