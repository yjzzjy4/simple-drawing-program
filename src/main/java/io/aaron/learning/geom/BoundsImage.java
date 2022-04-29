package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.BoundsPoint;
import io.aaron.learning.geom.impl.RectangleImage;
import io.aaron.learning.geom.strategy.resize.BottomResizeStrategy;
import io.aaron.learning.geom.strategy.resize.LeftResizeStrategy;
import io.aaron.learning.geom.strategy.resize.RightResizeStrategy;
import io.aaron.learning.geom.strategy.resize.TopResizeStrategy;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class BoundsImage extends RectangleImage {
    private AbstractShape parent;
    private final Map<Handler, BoundsPoint> handlers = new HashMap<>();
    public final DoubleProperty widthProperty = new SimpleDoubleProperty();
    public final DoubleProperty heightProperty = new SimpleDoubleProperty();

    public enum Handler {
        /**
         * 8 handlers;
         */
        TOP(HorizontalResize.FORBID, VerticalResize.TOP),
        TOP_LEFT(HorizontalResize.LEFT, VerticalResize.TOP),
        TOP_RIGHT(HorizontalResize.RIGHT, VerticalResize.TOP),
        BOTTOM(HorizontalResize.FORBID, VerticalResize.BOTTOM),
        BOTTOM_LEFT(HorizontalResize.LEFT, VerticalResize.BOTTOM),
        BOTTOM_RIGHT(HorizontalResize.RIGHT, VerticalResize.BOTTOM),
        LEFT(HorizontalResize.LEFT, VerticalResize.FORBID),
        RIGHT(HorizontalResize.RIGHT, VerticalResize.FORBID);

        private final HorizontalResize horizontalResize;
        private final VerticalResize verticalResize;

        Handler(HorizontalResize horizontalResize, VerticalResize verticalResize) {
            this.horizontalResize = horizontalResize;
            this.verticalResize = verticalResize;
        }

        public enum HorizontalResize {
            /**
             * aha
             */
            LEFT, RIGHT, FORBID
        }

        public enum VerticalResize {
            /**
             * aha
             */
            TOP, BOTTOM, FORBID
        }
    }

    private BoundsImage() {
        super();
        init();
    }

    private void hideBoundsExcept(@NonNull BoundsPoint point) {
        parent.getContainer().getChildren().remove(getCanvas());
        parent.getContainer()
                .getChildren()
                .removeAll(handlers.values()
                        .stream()
                        .filter(p -> !p.equals(point))
                        .map(AbstractShape::getCanvas)
                        .collect(Collectors.toList()));
    }

    private void showBoundsBesides(@NonNull BoundsPoint point) {
        parent.getContainer().getChildren().add(draw());
        parent.getContainer()
                .getChildren()
                .addAll(handlers.values()
                        .stream()
                        .filter(p -> !p.equals(point))
                        .map(AbstractShape::getCanvas)
                        .collect(Collectors.toList()));
        placeHandlers();
    }

    /**
     * place all handlers in the container;
     */
    private void placeHandlers() {
        bindHandlerPosition(Handler.TOP, parent.getWidth() / 2, 0.0);
        bindHandlerPosition(Handler.TOP_RIGHT, parent.getWidth(), 0.0);
        bindHandlerPosition(Handler.RIGHT, parent.getWidth(), parent.getHeight() / 2);
        bindHandlerPosition(Handler.BOTTOM_RIGHT, parent.getWidth(), parent.getHeight());
        bindHandlerPosition(Handler.BOTTOM, parent.getWidth() / 2, parent.getHeight());
        bindHandlerPosition(Handler.BOTTOM_LEFT, 0.0, parent.getHeight());
        bindHandlerPosition(Handler.LEFT, 0.0, parent.getHeight() / 2);
        bindHandlerPosition(Handler.TOP_LEFT, 0.0, 0.0);
    }

    /**
     * bind handler to the corner position;
     *
     * @param handler handler point;
     * @param offsetX horizontal offset;
     * @param offsetY vertical offset;
     */
    private void bindHandlerPosition(Handler handler, double offsetX, double offsetY) {
        BoundsPoint point = handlers.get(handler);
        Canvas canvas = point.getCanvas();
        canvas.layoutXProperty().set(offsetX - point.getHeight() / 2);
        canvas.layoutYProperty().set(offsetY - point.getHeight() / 2);
        canvas.translateXProperty().set(0.0);
        canvas.translateYProperty().set(0.0);
        point.getCanvas().translateXProperty().set(0.0);
        point.getCanvas().translateYProperty().set(0.0);
    }

    /**
     * (logically) bind handler point event;
     */
    private void bindHandlerMouseEvent() {
        Arrays.stream(Handler.values()).forEach(handler -> {
            BoundsPoint point = handlers.get(handler);
            Cursor cursor = Util.getCursorSupplier(handler).get();
            if (handler.verticalResize == Handler.VerticalResize.TOP) {
                setHandlerMouseEvent(point, cursor, new TopResizeStrategy().handle(point, this));
            } else if (handler.verticalResize == Handler.VerticalResize.BOTTOM) {
                setHandlerMouseEvent(point, cursor, new BottomResizeStrategy().handle(point, this));
            }

            if (handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                setHandlerMouseEvent(point, cursor, new LeftResizeStrategy().handle(point, this));
            } else if (handler.horizontalResize == Handler.HorizontalResize.RIGHT) {
                setHandlerMouseEvent(point, cursor, new RightResizeStrategy().handle(point, this));
            }
        });
    }

    /**
     * (actually) bind handler point event;
     *
     * @param point       handler point;
     * @param cursor      mouse style when hovering;
     * @param dragHandler drag handler;
     */
    private void setHandlerMouseEvent(@NonNull BoundsPoint point, Cursor cursor,
                                      EventHandler<? super MouseEvent> dragHandler) {
        point.getCanvas().setOnMouseEntered(event -> point.getCanvas().setCursor(cursor));
        point.getCanvas().setOnMouseExited(event -> point.getCanvas().setCursor(Cursor.DEFAULT));
        point.getCanvas().setOnMousePressed(event -> hideBoundsExcept(point));
        point.getCanvas().setOnMouseReleased(event -> {
            showBoundsBesides(point);
            Node container = parent.getContainer();
            parent.setX(container.getLayoutX() + container.getTranslateX());
            parent.setY(container.getLayoutY() + container.getTranslateY());
            container.setLayoutX(parent.getX());
            container.setLayoutY(parent.getY());
            container.setTranslateX(0.0);
            container.setTranslateY(0.0);
        });
        point.getCanvas().addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
    }

    private void init() {
        // 8 handlers;
        handlers.put(Handler.TOP, new BoundsPoint(10.0));
        handlers.put(Handler.TOP_RIGHT, new BoundsPoint(10.0));
        handlers.put(Handler.RIGHT, new BoundsPoint(10.0));
        handlers.put(Handler.BOTTOM_RIGHT, new BoundsPoint(10.0));
        handlers.put(Handler.BOTTOM, new BoundsPoint(10.0));
        handlers.put(Handler.BOTTOM_LEFT, new BoundsPoint(10.0));
        handlers.put(Handler.LEFT, new BoundsPoint(10.0));
        handlers.put(Handler.TOP_LEFT, new BoundsPoint(10.0));
        bindHandlerMouseEvent();
        placeHandlers();
    }

    /**
     * show bounds with 8 handler points for the bound shape;
     */
    public void show() {
        parent.getContainer().getChildren().add(draw());
        List<Node> nodes = handlers.values().stream().map(BoundsPoint::getCanvas).collect(Collectors.toList());
        parent.getContainer().getChildren().addAll(nodes);
    }

    /**
     * hide bounds with 8 handler points for the bound shape;
     */
    public void hide() {
        parent.getContainer().getChildren().remove(this.getCanvas());
        List<Node> nodes = handlers.values().stream().map(BoundsPoint::getCanvas).collect(Collectors.toList());
        parent.getContainer().getChildren().removeAll(nodes);
    }

    public static BoundsImage fromShape(@NonNull AbstractShape shape) {
        BoundsImage bounds = BoundsImage.builder()
                .parent(shape)
                .x(shape.getX())
                .y(shape.getY())
                .width(shape.getWidth())
                .height(shape.getHeight())
                .strokeWidth(1.0)
                .square(false)
                .filled(false)
                .stroke(Color.web("#00b8f0"))
                .fill(Color.web("#00b8f0"))
                .build();
        bounds.init();
        return bounds;
    }

    private static class Util {
        private static Supplier<Cursor> getCursorSupplier(Handler handler) {
            return () -> {
                if (handler.verticalResize == Handler.VerticalResize.TOP) {
                    if (handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                        return Cursor.NW_RESIZE;
                    } else if (handler.horizontalResize == Handler.HorizontalResize.RIGHT) {
                        return Cursor.NE_RESIZE;
                    } else {
                        return Cursor.N_RESIZE;
                    }
                } else if (handler.verticalResize == Handler.VerticalResize.BOTTOM) {
                    if (handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                        return Cursor.SW_RESIZE;
                    } else if (handler.horizontalResize == Handler.HorizontalResize.RIGHT) {
                        return Cursor.SE_RESIZE;
                    } else {
                        return Cursor.S_RESIZE;
                    }
                } else {
                    if (handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                        return Cursor.W_RESIZE;
                    } else {
                        return Cursor.E_RESIZE;
                    }
                }
            };
        }
    }
}
