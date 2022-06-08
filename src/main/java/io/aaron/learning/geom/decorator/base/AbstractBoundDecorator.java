package io.aaron.learning.geom.decorator.base;

import io.aaron.learning.geom.base.AbstractShape;

public abstract class AbstractBoundDecorator extends AbstractShapeDecorator implements Selectable {

    public enum Handler {
        /**
         * 8 handlers;
         */
        TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT
    }

    private double handlerDiameter = 10.0;

    public AbstractBoundDecorator(AbstractShape shape) {
        super(shape);
    }

    @Override
    public void move(double offsetX, double offsetY) {
        getShape().move(offsetX, offsetY);
    }

    @Override
    public void resize(double width, double height) {
        getShape().resize(width, height);
        draw();
    }

    /**
     * Check whether the coordinate (x, y) hit any one of the handlers.
     *
     * @param x x
     * @param y y
     * @return the hit handler or null
     */
    public abstract Handler hitHandler(double x, double y);

    @Override
    public abstract AbstractBoundDecorator clone();

    public abstract void hideHandlers();

    public abstract void showHandlers();

    public double getHandlerDiameter() {
        return handlerDiameter;
    }

    public void setHandlerDiameter(double handlerDiameter) {
        this.handlerDiameter = handlerDiameter;
    }
}
