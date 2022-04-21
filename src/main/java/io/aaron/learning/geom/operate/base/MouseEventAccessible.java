package io.aaron.learning.geom.operate.base;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author lishuang
 * @since 2022/04/21 12:22:06
 */
public interface MouseEventAccessible {

    /**
     * example:
     * <pre>
     * public void addMouseEventHandler(Node node) {
     *     node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
     *         System.out.println("hello, world");
     *     });
     * }
     * </pre>
     *
     * @param node the node that mouse event handler will be registered in.
     */
    default void addMouseEventHandler(Node node) {
    }
}
