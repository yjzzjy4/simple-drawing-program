package io.aaron.learning.manage.strategy.resize.base;

import io.aaron.learning.geom.decorator.ShapeImageBoundDecorator;
import io.aaron.learning.geom.shape.BoundPointImage;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:54:35
 */
public interface ResizeStrategy {
    void handleResize(MouseEvent event);

    /**
     * Get width of bounds before resizing.
     *
     * @return bounds width.
     */
    default double getOriginalWidth(ShapeImageBoundDecorator bounds) {
        BoundPointImage point = bounds.getHandlers().get(ShapeImageBoundDecorator.Handler.BOTTOM_RIGHT);
        return Math.abs(point.getCanvas().getLayoutX() + point.getWidth() / 2);
    }

    /**
     * Get height of bounds before resizing.
     *
     * @return bounds height.
     */
    default double getOriginalHeight(ShapeImageBoundDecorator bounds) {
        BoundPointImage point = bounds.getHandlers().get(ShapeImageBoundDecorator.Handler.BOTTOM_RIGHT);
        return Math.abs(point.getCanvas().getLayoutY() + point.getHeight() / 2);
    }

    default void resizeWidth(ShapeImageBoundDecorator bounds, Double width) {
        resize(bounds, width, bounds.getShape().getHeight());
    }

    default void resizeHeight(ShapeImageBoundDecorator bounds, Double height) {
        resize(bounds, bounds.getShape().getWidth(), height);
    }

    default void resize(ShapeImageBoundDecorator bounds, Double width, Double height) {
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
