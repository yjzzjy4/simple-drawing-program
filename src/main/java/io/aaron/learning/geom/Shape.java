package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.Rectangle;
import javafx.scene.canvas.Canvas;

public interface Shape {
    Rectangle getBounds();
    boolean contains(double x, double y);
    void draw(Canvas canvas, double x, double y);
}
