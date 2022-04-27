package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.strategy.resize.base.HorizontalResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author lishuang
 * @since 2022/04/27 13:55:42
 */
public class RightResizeStrategy implements HorizontalResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsImage bounds) {
        return event -> {

        };
    }
}
