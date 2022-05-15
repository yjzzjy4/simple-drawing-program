package io.aaron.learning.geom.strategy.resize.base;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:54:35
 */
public interface ResizeStrategy {
    EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds);

    /**
     * Get width of bounds before resizing.
     *
     * @return bounds width.
     */
    default double getOriginalWidth(ShapeImageBoundsDecorator bounds) {
        BoundsPoint point = bounds.getHandlers().get(ShapeImageBoundsDecorator.Handler.BOTTOM_RIGHT);
        return Math.abs(point.getCanvas().getLayoutX() + point.getWidth() / 2);
    }

    /**
     * Get height of bounds before resizing.
     *
     * @return bounds height.
     */
    default double getOriginalHeight(ShapeImageBoundsDecorator bounds) {
        BoundsPoint point = bounds.getHandlers().get(ShapeImageBoundsDecorator.Handler.BOTTOM_RIGHT);
        return Math.abs(point.getCanvas().getLayoutY() + point.getHeight() / 2);
    }

    default void resizeWidth(ShapeImageBoundsDecorator bounds, Double width) {
        resize(bounds, width, bounds.getShape().getHeight());
    }

    default void resizeHeight(ShapeImageBoundsDecorator bounds, Double height) {
        resize(bounds, bounds.getShape().getWidth(), height);
    }

    default void resize(ShapeImageBoundsDecorator bounds, Double width, Double height) {
        width = Math.abs(width);
        height = Math.abs(height);
        bounds.setWidth(width);
        bounds.setHeight(height);
        bounds.getShape().setWidth(width);
        bounds.getShape().setHeight(height);
        bounds.getShape().draw();
        bounds.draw();
    }
}
