package io.aaron.learning.geom.operate.base;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author lishuang
 * @since 2022/04/21 12:22:06
 */
public interface Draggable {
    void setOnMouseDragged(EventHandler<? super MouseEvent> value);
}
