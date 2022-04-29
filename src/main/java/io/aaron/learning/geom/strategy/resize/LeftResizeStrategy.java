package io.aaron.learning.geom.strategy.resize;

import io.aaron.learning.geom.BoundsImage;
import io.aaron.learning.geom.impl.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.base.HorizontalResizeStrategy;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Aaron
 * @since 2022/04/27 13:55:22
 */
public class LeftResizeStrategy implements HorizontalResizeStrategy {
    @Override
    public EventHandler<? super MouseEvent> handle(BoundsPoint point, BoundsImage bounds) {
        return event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                System.out.println("LEFT");
                Node container = bounds.getParent().getContainer();
                double width = bounds.getParent().getWidth();
                double offsetX = event.getX();
                double baseX = (bounds.getHandlers().get(BoundsImage.Handler.RIGHT).getCanvas().getWidth() +
                        bounds.getHandlers().get(BoundsImage.Handler.RIGHT).getCanvas().getLayoutX() * 2) / 2;

                // mirror flip;
                if (container.getTranslateX() == baseX) {
                    // flip back;
                    if (offsetX < 0) {
                        container.translateXProperty().set(container.getTranslateX() + offsetX);
                    }
                    width = offsetX;
                } else {
                    double allOffsetX = container.getTranslateX() + offsetX;
                    // mirror flip;
                    if (allOffsetX > baseX) {
                        width = allOffsetX - baseX;
                        container.translateXProperty().set(baseX);
                    } else {
                        width = width - offsetX;
                        container.translateXProperty().set(allOffsetX);
                    }
                }
                resizeWidth(bounds, width);
            }
            event.consume();
        };
    }
}
