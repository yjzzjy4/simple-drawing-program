package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.BoundsPoint;
import io.aaron.learning.geom.impl.RectangleImage;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuperBuilder
public class BoundsImage extends RectangleImage {
    private AbstractShape parent;
    private final Map<Handler, BoundsPoint> handlers = new HashMap<>();
    private final Double POINT_RADIUS = 5.0;

    public enum Handler {
        TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT
    }

    private BoundsImage() {
        super();
        init();
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
        canvas.layoutXProperty()
              .bind(parent.getContainer()
                          .layoutXProperty()
                          .subtract(parent.getContainer().layoutXProperty().subtract(offsetX).add(POINT_RADIUS)));
        canvas.layoutYProperty()
              .bind(parent.getContainer()
                          .layoutYProperty()
                          .subtract(parent.getContainer().layoutYProperty().subtract(offsetY).add(POINT_RADIUS)));
    }

    /**
     * (logically) bind handler point event;
     *
     * @param handler handler point;
     */
    private void bindHandlerMouseEvent(Handler handler) {
        BoundsPoint point = handlers.get(handler);
        switch(handler) {
            case TOP:
            case BOTTOM: {
                setHandlerMouseEvent(point, Cursor.V_RESIZE, event -> {
                    if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                        if(handler == Handler.TOP) {
                            parent.setHeight(getHeight() - event.getY());
                        }
                        else {
                            parent.setHeight(getHeight() + event.getY());
                        }
                        parent.draw();
                    }
                });
                break;
            }
            case LEFT:
            case RIGHT: {
                setHandlerMouseEvent(point, Cursor.H_RESIZE, event -> {
                    if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    }
                });
                break;
            }
            case TOP_LEFT:
            case BOTTOM_RIGHT: {
                setHandlerMouseEvent(point, Cursor.NW_RESIZE, event -> {
                    if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    }
                });
                break;
            }
            case TOP_RIGHT:
            case BOTTOM_LEFT: {
                setHandlerMouseEvent(point, Cursor.NE_RESIZE, event -> {
                    if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    }
                });
                break;
            }
        }

    }

    /**
     * (actually) bind handler point event;
     *
     * @param point handler point;
     * @param cursor mouse style when hovering;
     * @param dragHandler drag handler;
     */
    private void setHandlerMouseEvent(@NonNull BoundsPoint point, Cursor cursor, EventHandler<? super MouseEvent> dragHandler) {
        point.getCanvas().setOnMouseEntered(e -> point.getCanvas().setCursor(cursor));
        point.getCanvas().setOnMouseExited(e -> point.getCanvas().setCursor(Cursor.DEFAULT));
        point.getCanvas().setOnMouseDragged(dragHandler);
    }

    private void init() {
        // eight handlers;
        handlers.put(Handler.TOP, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.TOP, parent.getWidth() / 2, 0.0);
        bindHandlerMouseEvent(Handler.TOP);
        handlers.put(Handler.TOP_RIGHT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.TOP_RIGHT, parent.getWidth(), 0.0);
        bindHandlerMouseEvent(Handler.TOP_RIGHT);
        handlers.put(Handler.RIGHT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.RIGHT, parent.getWidth(), parent.getHeight() / 2);
        bindHandlerMouseEvent(Handler.RIGHT);
        handlers.put(Handler.BOTTOM_RIGHT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.BOTTOM_RIGHT, parent.getWidth(), parent.getHeight());
        bindHandlerMouseEvent(Handler.BOTTOM_RIGHT);
        handlers.put(Handler.BOTTOM, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.BOTTOM, parent.getWidth() / 2, parent.getHeight());
        bindHandlerMouseEvent(Handler.BOTTOM);
        handlers.put(Handler.BOTTOM_LEFT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.BOTTOM_LEFT, 0.0, parent.getHeight());
        bindHandlerMouseEvent(Handler.BOTTOM_LEFT);
        handlers.put(Handler.LEFT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.LEFT, 0.0, parent.getHeight() / 2);
        bindHandlerMouseEvent(Handler.LEFT);
        handlers.put(Handler.TOP_LEFT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.TOP_LEFT, 0.0, 0.0);
        bindHandlerMouseEvent(Handler.TOP_LEFT);
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
                                        .square(false)
                                        .filled(false)
                                        .stroke(Color.web("#00b8f0"))
                                        .fill(Color.web("#00b8f0"))
                                        .build();
        bounds.init();
        return bounds;
    }
}
