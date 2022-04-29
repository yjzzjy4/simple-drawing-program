package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.impl.BoundsPoint;
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
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, BoundsImage bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                System.out.println("RIGHT");
                Node container = bounds.getParent().getContainer();
                double width = bounds.getParent().getWidth();
                double offsetX = event.getX();
                double absLayoutX = Math.abs(point.getCanvas().getLayoutX());
                double offsetRadius = point.getWidth() / 2;

                // mirror flip;
                if(container.getTranslateX() < 0.0) {
                    // flip back;
                    if(offsetX > 0) {
                        container.translateXProperty().set(0.0);
                        width = offsetX;
                        point.getCanvas().translateXProperty().set(width - absLayoutX - offsetRadius);
                    }
                    else {
                        container.translateXProperty().set(offsetX);
                        width = Math.abs(container.getTranslateX());
                        point.getCanvas().translateXProperty().set(width - absLayoutX - offsetRadius);
                    }
                }
                else {
                    double allOffsetX = width + offsetX;
                    // mirror flip;
                    if(allOffsetX < 0) {
                        container.translateXProperty().set(allOffsetX);
                        width = Math.abs(allOffsetX);
                        point.getCanvas().translateXProperty().set(width - absLayoutX - offsetRadius);
                    }
                    else {
                        point.getCanvas().translateXProperty().set(point.getCanvas().getTranslateX() + offsetX);
                        width = point.getCanvas().getTranslateX() + Math.abs(point.getCanvas().getLayoutX() + offsetRadius);
                    }
                }
                resizeWidth(bounds, width);
            }
            event.consume();
        };
    }
}
