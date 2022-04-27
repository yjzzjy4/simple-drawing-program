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

    default void resize(BoundsImage bounds, Double height) {
        height = Math.abs(height);
        bounds.setHeight(height);
        bounds.draw();
        bounds.getParent().setHeight(height);
        bounds.getParent().draw();
    }
}
