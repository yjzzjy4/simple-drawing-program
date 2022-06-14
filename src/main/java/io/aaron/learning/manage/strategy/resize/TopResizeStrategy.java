package io.aaron.learning.manage.strategy.resize;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.DragHelper;
import io.aaron.learning.manage.strategy.resize.base.VerticalResizeStrategy;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * @author Aaron
 * @since 2022/04/27 11:29:45
 *
 */
public class TopResizeStrategy implements VerticalResizeStrategy {
    @Override
    public void handleResize(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double y = event.getY();
            double totalOffsetY = y - DragHelper.originalCY;
            DragHelper.setLatest(event.getX(), y);
            List<AbstractBoundDecorator> targets = DragHelper.targets;

            // mirror flip;
            if(totalOffsetY > DragHelper.originalHeight) {
                // calc scale factor;
                final double factor = (totalOffsetY - DragHelper.originalHeight) / DragHelper.exactTarget.getHeight();
                targets.forEach(t -> t.resize(t.getWidth(), t.getHeight() * factor));
            }
            else if(totalOffsetY < DragHelper.originalHeight) {
                // calc scale factor;
                final double factor = (DragHelper.originalHeight - totalOffsetY) / DragHelper.exactTarget.getHeight();
                targets.forEach(t -> {
                    t.move(0, t.getHeight() * (1 - factor));
                    t.resize(t.getWidth(), t.getHeight() * factor);
                });
            }
        }
        event.consume();
    }
}