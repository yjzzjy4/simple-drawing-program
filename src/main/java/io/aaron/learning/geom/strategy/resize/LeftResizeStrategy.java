package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.HorizontalResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:55:22
 */
public class LeftResizeStrategy implements HorizontalResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Node container = bounds.getContainer();
                double width = bounds.getWidth();
                double offsetX = event.getX();
                double originalWidth = getOriginalWidth(bounds);

                // mirror flip;
                if (container.getTranslateX() == originalWidth) {
                    // flip back;
                    if (offsetX < 0) {
                        container.translateXProperty().set(originalWidth + offsetX);
                    }
                    width = offsetX;
                }
                else {
                    double allOffsetX = container.getTranslateX() + offsetX;
                    // mirror flip;
                    if (allOffsetX > originalWidth) {
                        width = allOffsetX - originalWidth;
                        container.translateXProperty().set(originalWidth);
                    }
                    else {
                        width = width - offsetX;
                        container.translateXProperty().set(allOffsetX);
                    }
                }
                resizeWidth(bounds, width);
            }
            event.consume();
        };
    }
}
