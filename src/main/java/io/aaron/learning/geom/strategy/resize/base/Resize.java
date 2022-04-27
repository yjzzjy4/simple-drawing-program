package io.aaron.learning.geom.strategy.resize.base;

import io.aaron.learning.geom.BoundsImage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author lishuang
 * @since 2022/04/27 13:54:35
 */
public interface Resize {
    EventHandler<? super MouseEvent> handle(BoundsImage bounds);

    default void resize(BoundsImage bounds, Double height) {
        height = Math.abs(height);
        bounds.setHeight(height);
        bounds.draw();
        bounds.getParent().setHeight(height);
        bounds.getParent().draw();
    }
}
