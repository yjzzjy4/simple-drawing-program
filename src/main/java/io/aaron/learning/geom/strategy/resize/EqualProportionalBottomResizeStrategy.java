package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.ResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EqualProportionalBottomResizeStrategy implements ResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Node container = bounds.getContainer();
                double height = bounds.getHeight();
                double offsetY = event.getY();
                double originalWidth = getOriginalWidth(bounds);
                double originalHeight = getOriginalHeight(bounds);
                double proportion = originalWidth / originalHeight;

                // mirror flip;
                if (container.getTranslateY() < 0.0) {
                    // flip back;
                    if (offsetY > 0) {
                        container.translateXProperty().set(0.0);
                        container.translateYProperty().set(0.0);
                    }
                    else {
                        container.translateXProperty().set(offsetY * proportion);
                        container.translateYProperty().set(offsetY);
                    }
                    height = Math.abs(offsetY);
                    point.getCanvas().translateXProperty().set(-originalWidth / 2 + height * proportion / 2);
                    point.getCanvas().translateYProperty().set(height - originalHeight);
                }
                else {
                    double allOffsetY = height + offsetY;
                    // mirror flip;
                    if(allOffsetY < 0) {
                        container.translateXProperty().set(allOffsetY * proportion);
                        container.translateYProperty().set(allOffsetY);
                        height = Math.abs(allOffsetY);
                        point.getCanvas().translateXProperty().set(-originalWidth / 2 + height * proportion / 2);
                        point.getCanvas().translateYProperty().set(height - originalHeight);
                    }
                    else {
                        point.getCanvas().translateXProperty().set(point.getCanvas().getTranslateX() + offsetY * proportion / 2);
                        point.getCanvas().translateYProperty().set(point.getCanvas().getTranslateY() + offsetY);
                        height = point.getCanvas().getTranslateY() + originalHeight;
                    }
                }
                resize(bounds, height * proportion, height);
            }
            event.consume();
        };
    }
}
