package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RectangleWrapper extends AbstractShape {
    private final Boolean square;
    private final Rectangle rectangle;

    {
        rectangle = new Rectangle();
    }

    public RectangleWrapper() {
        square = false;
        setDefaults(false);
    }

    public RectangleWrapper(Boolean square) {
        this.square = square;
        setDefaults(square);
    }

    public RectangleWrapper(Boolean square, Double width, Double height) {
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
    public void paint(Group group, double x, double y) {

    }
}
