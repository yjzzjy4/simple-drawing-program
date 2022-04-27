package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.BoundsPoint;
import io.aaron.learning.geom.impl.RectangleImage;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    public final DoubleProperty widthProperty = new SimpleDoubleProperty();
    public final DoubleProperty heightProperty = new SimpleDoubleProperty();

    public enum Handler {
        TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT
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
        canvas.layoutXProperty().set(offsetX - POINT_RADIUS);
        canvas.layoutYProperty().set(offsetY - POINT_RADIUS);
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
                    if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                        Node container = parent.getContainer();
                        double height = parent.getHeight(), offsetY = event.getY();
                        if(handler == Handler.TOP) {
                            // direction reversed;
                            if(container.getTranslateY() == 55.0) {
                                double newHeight = height + offsetY;
                                if(newHeight <= 0) {
                                    height = Math.abs(newHeight);
                                    container.translateYProperty().set(container.getTranslateY() + height);
                                }
                                else {
                                    height = newHeight;
                                }
                                System.out.println(offsetY + ", " + height);
                            }
                            else {
                                double allOffsetY = container.getTranslateY() + offsetY;
                                if(allOffsetY >= 55.0) {
                                    height = allOffsetY - 55.0;
                                    container.translateYProperty().set(55.0);
                                }
                                else {
                                    height = height - offsetY;
                                    container.translateYProperty().set(allOffsetY);
                                }
                            }
                        }
                        else {
                            height = height + event.getY();
                        }
//                        height = Math.abs(height);
                        setHeight(height);
                        draw();
                        parent.setHeight(height);
                        parent.draw();
//                        placeHandlers();
                    }
                });
                break;
            }
            case LEFT:
            case RIGHT: {
                setHandlerMouseEvent(point, Cursor.H_RESIZE, event -> {
                    if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    }
                });
                break;
            }
            case TOP_LEFT:
            case BOTTOM_RIGHT: {
                setHandlerMouseEvent(point, Cursor.NW_RESIZE, event -> {
                    if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    }
                });
                break;
            }
            case TOP_RIGHT:
            case BOTTOM_LEFT: {
                setHandlerMouseEvent(point, Cursor.NE_RESIZE, event -> {
                    if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    }
                });
                break;
            }
        }

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
        point.getCanvas().setOnMouseEntered(e -> point.getCanvas().setCursor(cursor));
        point.getCanvas().setOnMouseExited(e -> point.getCanvas().setCursor(Cursor.DEFAULT));
        point.getCanvas().setOnMousePressed(event -> hideBoundsExcept(point));
        point.getCanvas().setOnMouseReleased(event -> showBoundsBesides(point));
        point.getCanvas().setOnMouseDragged(dragHandler);
    }

    private void init() {
        // 8 handlers;
        handlers.put(Handler.TOP, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.TOP);
        handlers.put(Handler.TOP_RIGHT, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.TOP_RIGHT);
        handlers.put(Handler.RIGHT, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.RIGHT);
        handlers.put(Handler.BOTTOM_RIGHT, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.BOTTOM_RIGHT);
        handlers.put(Handler.BOTTOM, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.BOTTOM);
        handlers.put(Handler.BOTTOM_LEFT, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.BOTTOM_LEFT);
        handlers.put(Handler.LEFT, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.LEFT);
        handlers.put(Handler.TOP_LEFT, new BoundsPoint(10.0));
        bindHandlerMouseEvent(Handler.TOP_LEFT);
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
                                        .square(false)
                                        .filled(false)
                                        .stroke(Color.web("#00b8f0"))
                                        .fill(Color.web("#00b8f0"))
                                        .build();
        bounds.init();
        return bounds;
    }
}
