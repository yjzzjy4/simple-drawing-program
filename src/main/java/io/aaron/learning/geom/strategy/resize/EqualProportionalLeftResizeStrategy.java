package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.ResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EqualProportionalLeftResizeStrategy implements ResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Node container = bounds.getContainer();
                double width = bounds.getWidth();
                double offsetX = event.getX();
                double originalWidth = getOriginalWidth(bounds);
                double originalHeight = getOriginalHeight(bounds);
                double proportion = originalHeight / originalWidth;

                // mirror flip;
                if (container.getTranslateX() == originalWidth) {
                    // flip back;
                    if (offsetX < 0) {
                        container.translateXProperty().set(originalWidth + offsetX);
                        container.translateYProperty().set(0.0);
                    }
                    else {
                        container.translateYProperty().set(-offsetX * proportion);
                    }
                    width = Math.abs(offsetX);
                    point.getCanvas().translateYProperty().set(-originalHeight / 2 + width * proportion / 2);
                }
                else {
                    double allOffsetX = container.getTranslateX() + offsetX;
                    // mirror flip;
                    if (allOffsetX > originalWidth) {
                        width = allOffsetX - originalWidth;
                        container.translateXProperty().set(originalWidth);
                        container.translateYProperty().set(-width * proportion);
                        point.getCanvas().translateYProperty().set(-originalHeight / 2 + width * proportion / 2);
                    }
                    else {
                        width = width - offsetX;
                        container.translateXProperty().set(allOffsetX);
                        point.getCanvas().translateYProperty().set(point.getCanvas().getTranslateY() - offsetX * proportion / 2);
                    }
                }
                resize(bounds, width, width * proportion);
            }
            event.consume();
        };
    }
}
