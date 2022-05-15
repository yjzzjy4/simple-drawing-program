package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.EqualProportional;

public class CircleImage extends EllipseImage implements EqualProportional {

    public CircleImage() {
        super(80.0, 80.0);
    }

    public CircleImage(CircleImage circle) {
        super(circle);
    }

    public CircleImage(double diameter) {
        super(diameter, diameter);
    }

    public CircleImage(double x, double y, double diameter) {
        super(x, y, diameter, diameter);
    }

    @Override
    public CircleImage clone() {
        return new CircleImage(this);
    }
}
