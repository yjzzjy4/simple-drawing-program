package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.canvas.Canvas;

public class Rectangle extends AbstractShape {

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public void draw(Canvas canvas, double x, double y) {

    }
}
