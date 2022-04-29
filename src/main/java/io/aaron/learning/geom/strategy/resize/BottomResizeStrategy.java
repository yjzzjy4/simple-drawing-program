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
                System.out.println("BOTTOM");
                Node container = bounds.getParent().getContainer();
                double height = bounds.getParent().getHeight();
                double offsetY = event.getY();
                double absLayoutY = Math.abs(point.getCanvas().getLayoutY());
                double offsetRadius = point.getHeight() / 2;

                // mirror flip;
                if (container.getTranslateY() < 0.0) {
                    // flip back;
                    if (offsetY > 0) {
                        container.translateYProperty().set(0.0);
                        height = offsetY;
                        point.getCanvas().translateYProperty().set(height - absLayoutY - offsetRadius);
                    } else {
                        container.translateYProperty().set(offsetY);
                        height = Math.abs(container.getTranslateY());
                        point.getCanvas().translateYProperty().set(height - absLayoutY - offsetRadius);
                    }
                } else {
                    double allOffsetY = height + offsetY;
                    // mirror flip;
                    if(allOffsetY < 0) {
                        container.translateYProperty().set(allOffsetY);
                        height = Math.abs(allOffsetY);
                        point.getCanvas().translateYProperty().set(height - absLayoutY - offsetRadius);
                    } else {
                        point.getCanvas().translateYProperty().set(point.getCanvas().getTranslateY() + offsetY);
                        height = point.getCanvas().getTranslateY() + Math.abs(point.getCanvas().getLayoutY() + offsetRadius);
                    }
                }
                resizeHeight(bounds, height);
            }
            event.consume();
        };
    }
}
