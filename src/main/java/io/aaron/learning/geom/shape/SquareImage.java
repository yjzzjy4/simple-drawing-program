package io.aaron.learning.geom.shape;

import io.aaron.learning.geom.base.EqualProportional;

public class SquareImage extends RectangleImage implements EqualProportional {

    public SquareImage() {
        super(80.0, 80.0);
    }

    public SquareImage(SquareImage square) {
        super(square);
    }

    public SquareImage(double sideLength) {
        super(sideLength, sideLength);
    }

    public SquareImage(double x, double y, double sideLength) {
        super(x, y, sideLength, sideLength);
    }

    @Override
    public SquareImage clone() {
        return new SquareImage(this);
    }
}
