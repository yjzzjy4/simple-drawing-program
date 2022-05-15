package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.ResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class EqualProportionalBottomLeftResizeStrategy implements ResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds) {
        return null;
    }
}
