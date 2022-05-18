package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.ResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EqualProportionalTopResizeStrategy implements ResizeStrategy {

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
                if (container.getTranslateY() == originalHeight) {
                    // flip back;
                    if (offsetY < 0) {
                        container.translateXProperty().set(0.0);
                        container.translateYProperty().set(originalHeight + offsetY);
                    }
                    else {
                        container.translateXProperty().set(-offsetY * proportion);
                    }
                    height = Math.abs(offsetY);
                    point.getCanvas().translateXProperty().set(-originalWidth / 2 + height * proportion / 2);
                }
                else {
                    double allOffsetY = container.getTranslateY() + offsetY;
                    // mirror flip;
                    if (allOffsetY > originalHeight) {
                        height = allOffsetY - originalHeight;
                        container.translateXProperty().set(-height * proportion);
                        container.translateYProperty().set(originalHeight);
                        point.getCanvas().translateXProperty().set(-originalWidth / 2 + height * proportion / 2);
                    }
                    else {
                        height = height - offsetY;
                        container.translateYProperty().set(allOffsetY);
                        point.getCanvas().translateXProperty().set(point.getCanvas().getTranslateX() - offsetY * proportion / 2);
                    }
                }
                resize(bounds, height * proportion, height);
            }
            event.consume();
        };
    }
}
