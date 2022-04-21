package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RectangleImage extends AbstractShape {
    private final Boolean square;
    private Canvas canvas;

    public RectangleImage() {
        square = false;
        setDefaults(false);
    }

    public RectangleImage(Boolean square) {
        this.square = square;
        setDefaults(square);
    }

    public RectangleImage(Boolean square, Double width, Double height) {
        super(width, height);
        canvas = new Canvas(width, height);
        this.square = square;
    }

    private void setDefaults(Boolean square) {
        if(square) {
            setWidth(80.0);
            setHeight(80.0);
            canvas = new Canvas(80.0, 80.0);
        }
        else {
            setWidth(120.0);
            setHeight(60.0);
            canvas = new Canvas(120.0, 60.0);
        }
    }

    @Override
    public boolean contains(double x, double y) {
        return super.contains(x, y);
    }

    @Override
    public void paint(Group group, double x, double y) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        if(getFilled()) {
            gc.strokeRect(0, 0, getWidth(), getHeight());
            gc.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        }
        else {
            gc.strokeRect(0, 0, getWidth(), getHeight());
        }
        group.getChildren().add(canvas);
    }
}
