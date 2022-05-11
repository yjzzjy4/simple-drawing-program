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
