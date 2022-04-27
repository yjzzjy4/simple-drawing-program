package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.strategy.resize.base.VerticalResize;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author lishuang
 * @since 2022/04/27 11:49:29
 */
public class BottomResize implements VerticalResize {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsImage bounds) {
        return event -> {

        };
    }
}
