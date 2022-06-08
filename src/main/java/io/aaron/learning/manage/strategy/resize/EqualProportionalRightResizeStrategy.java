package io.aaron.learning.manage.strategy.resize;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.DragHelper;
import io.aaron.learning.manage.strategy.resize.base.HorizontalResizeStrategy;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * @author Aaron
 * @since 2022/04/27 13:55:42
 *
 */
public class EqualProportionalRightResizeStrategy implements HorizontalResizeStrategy {
    @Override
    public void handleResize(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double x = event.getX();
            double totalOffsetX = x - DragHelper.originalCX;
            DragHelper.setLatest(x, event.getY());
            List<AbstractBoundDecorator> targets = DragHelper.targets;

            // mirror flip;
            if(totalOffsetX < 0 && totalOffsetX < -DragHelper.originalWidth) {
                // calc scale factor;
                final double factor = -(DragHelper.originalWidth + totalOffsetX) / DragHelper.exactTarget.getWidth();
                targets.forEach(t -> {
                    t.move(t.getWidth() * (1 - factor), 0);
                    t.resize(t.getWidth() * factor, t.getHeight() * factor);
                });
            }
            else if(totalOffsetX > 0 || totalOffsetX < 0 && totalOffsetX > -DragHelper.originalWidth) {
                // calc scale factor;
                final double factor = (DragHelper.originalWidth + totalOffsetX) / DragHelper.exactTarget.getWidth();
                targets.forEach(t -> t.resize(t.getWidth() * factor, t.getHeight() * factor));
            }
        }
        event.consume();
    }
}
