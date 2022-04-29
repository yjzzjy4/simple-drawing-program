package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.impl.BoundsPoint;
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
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, BoundsImage bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                System.out.println("TOP");
                Node container = bounds.getParent().getContainer();
                double height = bounds.getParent().getHeight();
                double offsetY = event.getY();
                double baseY = (bounds.getHandlers().get(BoundsImage.Handler.BOTTOM).getCanvas().getHeight() +
                        bounds.getHandlers().get(BoundsImage.Handler.BOTTOM).getCanvas().getLayoutY() * 2) / 2;

                // mirror flip;
                if (container.getTranslateY() == baseY) {
                    // flip back;
                    if (offsetY < 0) {
                        container.translateYProperty().set(container.getTranslateY() + offsetY);
                    }
                    height = offsetY;
                } else {
                    double allOffsetY = container.getTranslateY() + offsetY;
                    // mirror flip;
                    if (allOffsetY > baseY) {
                        height = allOffsetY - baseY;
                        container.translateYProperty().set(baseY);
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
