package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.HorizontalResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:55:42
 */
public class RightResizeStrategy implements HorizontalResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, ShapeImageBoundsDecorator bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Node container = bounds.getContainer();
                double width = bounds.getWidth();
                double offsetX = event.getX();
                double originWidth = Math.abs(point.getCanvas().getLayoutX() + point.getWidth() / 2);

                // mirror flip;
                if(container.getTranslateX() < 0.0) {
                    // flip back;
                    if(offsetX > 0) {
                        container.translateXProperty().set(0.0);
                        width = offsetX;
                        point.getCanvas().translateXProperty().set(width - originWidth);
                    }
                    else {
                        container.translateXProperty().set(offsetX);
                        width = Math.abs(container.getTranslateX());
                        point.getCanvas().translateXProperty().set(width - originWidth);
                    }
                }
                else {
                    double allOffsetX = width + offsetX;
                    // mirror flip;
                    if(allOffsetX < 0) {
                        container.translateXProperty().set(allOffsetX);
                        width = Math.abs(allOffsetX);
                        point.getCanvas().translateXProperty().set(width - originWidth);
                    }
                    else {
                        point.getCanvas().translateXProperty().set(point.getCanvas().getTranslateX() + offsetX);
                        width = point.getCanvas().getTranslateX() + originWidth;
                    }
                }
                resizeWidth(bounds, width);
            }
            event.consume();
        };
    }
}
