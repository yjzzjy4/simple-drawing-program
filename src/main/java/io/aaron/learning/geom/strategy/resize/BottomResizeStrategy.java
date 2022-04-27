package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.impl.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.VerticalResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 11:49:29
 */
public class BottomResizeStrategy implements VerticalResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, BoundsImage bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Node container = bounds.getParent().getContainer();
                double height = bounds.getParent().getHeight();
                double offsetY = event.getY();
                double baseY = (bounds.getHandlers().get(BoundsImage.Handler.TOP).getCanvas().getHeight() +
                        bounds.getHandlers().get(BoundsImage.Handler.TOP).getCanvas().getLayoutY() * 2) / 2;

                // mirror flip;
                if (point.getCanvas().getTranslateY() + point.getCanvas().getLayoutY() == 0.0) {
                    System.out.println("offsetY: " + offsetY);
                    // flip back;
                    if (offsetY > 0) {
                        container.translateYProperty().set(0.0);
                        point.getCanvas().translateYProperty().set(point.getCanvas().getTranslateY() + offsetY);
                    } else {
                        container.translateYProperty().set(offsetY);
                    }
                    height = offsetY;
                } else {
                    double allOffsetY = height + offsetY;
                    if(allOffsetY < 0) {
                        height = allOffsetY;
                        container.translateYProperty().set(height);
                        point.getCanvas().translateYProperty().set(0.0 - point.getCanvas().getLayoutY());
                    } else {
                        point.getCanvas().translateYProperty().set(point.getCanvas().getTranslateY() + offsetY);
                        height = point.getCanvas().getTranslateY() + Math.abs(point.getCanvas().getLayoutY() + point.getHeight() / 2);
                    }
                }
                resize(bounds, height);
            }
            event.consume();
        };
    }
}
