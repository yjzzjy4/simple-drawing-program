package io.aaron.learning.geom.shape;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BoundsPoint extends OvalImage {
    public BoundsPoint() {
        super(10.0);
        init();
    }

    public BoundsPoint(Double diameter) {
        super(diameter);
        init();
    }

    private void init() {
        Paint paint = Color.web("#00b8f0");
        setStrokePaint(paint);
        setFillPaint(paint);
        applyChange();
    }
}
