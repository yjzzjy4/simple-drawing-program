package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Rectangle extends AbstractShape {
    private final Boolean square;

    public Rectangle() {
        setDefaults(square = false);
    }

    public Rectangle(Boolean square) {
        setDefaults(this.square = square);
    }

    public Rectangle(Boolean square, Double width, Double height) {
        super(width, height);
        this.square = square;
    }

    private void setDefaults(Boolean square) {
        if(square) {
            setWidth(80.0);
            setHeight(80.0);
        }
        else {
            setWidth(120.0);
            setHeight(60.0);
        }
    }

    @Override
    public void paint(Canvas canvas, double x, double y) {
        javafx.scene.shape.Rectangle rec = new javafx.scene.shape.Rectangle();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        // wrong approach...
        if(getFilled()) {
            gc.fillRect(x, y, getWidth(), getHeight());
        }
        else {
            gc.strokeRect(x, y, getWidth(), getHeight());
        }
    }
}
