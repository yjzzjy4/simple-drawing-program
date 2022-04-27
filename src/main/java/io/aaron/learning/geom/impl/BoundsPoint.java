package io.aaron.learning.geom.impl;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BoundsPoint extends OvalImage {
    public BoundsPoint() {
        super(true);
        init();
    }

    public BoundsPoint(Double diameter) {
        super(diameter);
        init();
    }

    private void init() {
        Paint paint = Color.web("#00b8f0");
        setStroke(paint);
        setFill(paint);
        applyChange();
    }
}
