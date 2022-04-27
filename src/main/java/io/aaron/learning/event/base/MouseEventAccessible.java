package io.aaron.learning.event.base;

import javafx.event.EventType;
import javafx.scene.Node;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public interface MouseEventAccessible {
    default void addMouseEventHandler(Node node, EventHandler<? super MouseEvent> value) {
    }
}
