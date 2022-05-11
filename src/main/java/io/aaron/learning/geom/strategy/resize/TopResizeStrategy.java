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
                double originHeight = (bounds.getHandlers().get(ShapeImageBoundsDecorator.Handler.BOTTOM).getCanvas().getHeight() +
                        bounds.getHandlers().get(ShapeImageBoundsDecorator.Handler.BOTTOM).getCanvas().getLayoutY() * 2) / 2;

                // mirror flip;
                if (container.getTranslateY() == originHeight) {
                    // flip back;
                    if (offsetY < 0) {
                        container.translateYProperty().set(container.getTranslateY() + offsetY);
                    }
                    height = offsetY;
                } else {
                    double allOffsetY = container.getTranslateY() + offsetY;
                    // mirror flip;
                    if (allOffsetY > originHeight) {
                        height = allOffsetY - originHeight;
                        container.translateYProperty().set(originHeight);
                    } else {
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
