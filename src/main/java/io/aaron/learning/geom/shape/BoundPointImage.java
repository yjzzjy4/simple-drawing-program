package io.aaron.learning.geom.shape;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BoundPointImage extends EllipseImage {

    public BoundPointImage() {
        this(10.0);
    }

    public BoundPointImage(double diameter) {
        super(diameter, diameter);
        init();
    }

    private void init() {
        Paint paint = Color.web("#00b8f0");
        setStrokePaint(Color.web("#fff"));
        setLineWidth(2.0);
        setFillPaint(paint);
        setOpacity(1.0);
        draw();
    }
}
