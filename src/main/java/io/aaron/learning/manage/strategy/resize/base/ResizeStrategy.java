package io.aaron.learning.manage.strategy.resize.base;

import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:54:35
 */
public interface ResizeStrategy {
    void handleResize(MouseEvent event);
}
