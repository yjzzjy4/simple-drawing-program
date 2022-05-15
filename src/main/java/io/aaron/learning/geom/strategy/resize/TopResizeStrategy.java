package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.VerticalResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 11:29:45
 */
public class TopResizeStrategy implements VerticalResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Node container = bounds.getContainer();
                double height = bounds.getHeight();
                double offsetY = event.getY();
                double originalHeight = getOriginalHeight(bounds);

                // mirror flip;
                if (container.getTranslateY() == originalHeight) {
                    // flip back;
                    if (offsetY < 0) {
                        container.translateYProperty().set(originalHeight + offsetY);
                    }
                    height = offsetY;
                }
                else {
                    double allOffsetY = container.getTranslateY() + offsetY;
                    // mirror flip;
                    if (allOffsetY > originalHeight) {
                        height = allOffsetY - originalHeight;
                        container.translateYProperty().set(originalHeight);
                    }
                    else {
                        height = height - offsetY;
                        container.translateYProperty().set(allOffsetY);
                    }
                }
                resizeHeight(bounds, height);
            }
            event.consume();
        };
    }
}
