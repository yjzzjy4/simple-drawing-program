package io.aaron.learning.geom.operate.base;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author lishuang
 * @since 2022/04/21 12:22:06
 */
public interface MouseEventAccessible {

    void setMouseEventHandler(EventHandler<? super MouseEvent> value);

    /**
     * example:
     * <pre>
     * public void addMouseEventHandler() {
     *     getContainer().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
     *         System.out.println("hello");
     *     });
     * }
     * </pre>
     */
    default void addMouseEventHandler() {
    }
}
