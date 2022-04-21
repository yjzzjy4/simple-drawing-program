package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author lishuang
 * @since 2022/04/21 15:00:34
 */
public class Circle extends AbstractShape {
    @Override
    protected Canvas draw() {
        Canvas canvas = new Canvas();
        canvas.setWidth(getWidth() + 2);
        canvas.setHeight(getHeight() + 2);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(getFill());
        context.setStroke(getStroke());
        if (isFilled()) {
            context.fillOval(getX(), getY(), getWidth(), getHeight());
        }
        context.strokeOval(getX(), getY(), getWidth(), getHeight());
        return canvas;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }
}
