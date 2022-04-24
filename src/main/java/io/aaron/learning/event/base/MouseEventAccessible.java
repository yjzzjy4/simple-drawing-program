package io.aaron.learning.event.base;

import javafx.scene.Node;

public interface MouseEventAccessible {
    default void addMouseEventHandler(Node node) {}
}
