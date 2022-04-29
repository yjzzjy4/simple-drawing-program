package io.aaron.learning.geom.strategy.resize.base;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.impl.BoundsPoint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:54:35
 */
public interface ResizeStrategy {
    EventHandler<? super MouseEvent> handle(BoundsPoint point, BoundsImage bounds);

    default void resizeWidth(BoundsImage bounds, Double width) {
        resize(bounds, width, bounds.getParent().getHeight());
    }

    default void resizeHeight(BoundsImage bounds, Double height) {
        resize(bounds, bounds.getParent().getWidth(), height);
    }

    default void resize(BoundsImage bounds, Double width, Double height) {
        width = Math.abs(width);
        height = Math.abs(height);
        bounds.setWidth(width);
        bounds.setHeight(height);
        bounds.getParent().setWidth(width);
        bounds.getParent().setHeight(height);
        bounds.getParent().draw();
        bounds.draw();
    }
}
