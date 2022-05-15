package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.ResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EqualProportionalRightResizeStrategy implements ResizeStrategy {
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
                if (container.getTranslateX() < 0.0) {
                    // flip back;
                    if (offsetX > 0) {
                        container.translateXProperty().set(0.0);
                        container.translateYProperty().set(0.0);
                    }
                    else {
                        container.translateXProperty().set(offsetX);
                        container.translateYProperty().set(offsetX * proportion);
                    }
                    width = Math.abs(offsetX);
                    point.getCanvas().translateXProperty().set(width - originalWidth);
                    point.getCanvas().translateYProperty().set(originalHeight / -2 + width * proportion / 2);
                }
                else {
                    double allOffsetX = width + offsetX;
                    // mirror flip;
                    if(allOffsetX < 0) {
                        container.translateXProperty().set(allOffsetX);
                        container.translateYProperty().set(allOffsetX * proportion);
                        width = Math.abs(allOffsetX);
                        point.getCanvas().translateXProperty().set(width - originalWidth);
                        point.getCanvas().translateYProperty().set(originalHeight / -2 + width * proportion / 2);
                    }
                    else {
                        point.getCanvas().translateXProperty().set(point.getCanvas().getTranslateX() + offsetX);
                        point.getCanvas().translateYProperty().set(point.getCanvas().getTranslateY() + offsetX * proportion / 2);
                        width = point.getCanvas().getTranslateX() + originalWidth;
                    }
                }
                resize(bounds, width, width * proportion);
            }
            event.consume();
        };
    }
}
