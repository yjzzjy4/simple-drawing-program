package io.aaron.learning.geom.decorator;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.BottomResizeStrategy;
import io.aaron.learning.geom.strategy.resize.LeftResizeStrategy;
import io.aaron.learning.geom.strategy.resize.RightResizeStrategy;
import io.aaron.learning.geom.strategy.resize.TopResizeStrategy;
import io.aaron.learning.manage.ShapeHolder;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShapeImageBoundsDecorator extends AbstractShapeDecorator {
    private final Map<Handler, BoundsPoint> handlers = new HashMap<>();

    public enum Handler {
        /**
         * 8 handlers;
         */
        TOP(HorizontalResize.FORBID, VerticalResize.TOP), TOP_LEFT(HorizontalResize.LEFT,
                                                                   VerticalResize.TOP), TOP_RIGHT(
                HorizontalResize.RIGHT, VerticalResize.TOP), BOTTOM(HorizontalResize.FORBID,
                                                                    VerticalResize.BOTTOM), BOTTOM_LEFT(
                HorizontalResize.LEFT, VerticalResize.BOTTOM), BOTTOM_RIGHT(HorizontalResize.RIGHT,
                                                                            VerticalResize.BOTTOM), LEFT(
                HorizontalResize.LEFT, VerticalResize.FORBID), RIGHT(HorizontalResize.RIGHT, VerticalResize.FORBID);

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

    public ShapeImageBoundsDecorator(AbstractShape shape) {
        super(shape);
        init();
    }

    @Override
    public Node draw() {
        Canvas canvas = getCanvas();
        if(canvas == null) {
            setCanvas(new Canvas(getWidth() + 2 * getLineWidth(), getHeight() + 2 * getLineWidth()));
            canvas = getCanvas();
        }
        else {
            canvas.setHeight(getHeight() + 2 * getLineWidth());
            canvas.setWidth(getWidth() + 2 * getLineWidth());
        }
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(getFillPaint());
        context.setStroke(getStrokePaint());
        if(getFilled()) {
            context.fillRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        }
        context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        return canvas;
    }

    private void hideBoundsExcept(@NonNull BoundsPoint point) {
        getContainer().getChildren().remove(getCanvas());
        getContainer().getChildren()
                      .removeAll(handlers.values()
                                         .stream()
                                         .filter(p -> !p.equals(point))
                                         .map(AbstractShape::getCanvas)
                                         .collect(Collectors.toList()));
    }

    private void showBoundsBesides(@NonNull BoundsPoint point) {
        getContainer().getChildren().add(draw());
        getContainer().getChildren()
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
        bindHandlerPosition(Handler.TOP, getShape().getWidth() / 2, 0.0);
        bindHandlerPosition(Handler.TOP_RIGHT, getShape().getWidth(), 0.0);
        bindHandlerPosition(Handler.RIGHT, getShape().getWidth(), getShape().getHeight() / 2);
        bindHandlerPosition(Handler.BOTTOM_RIGHT, getShape().getWidth(), getShape().getHeight());
        bindHandlerPosition(Handler.BOTTOM, getShape().getWidth() / 2, getShape().getHeight());
        bindHandlerPosition(Handler.BOTTOM_LEFT, 0.0, getShape().getHeight());
        bindHandlerPosition(Handler.LEFT, 0.0, getShape().getHeight() / 2);
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
        canvas.layoutYProperty().set(offsetY - point.getWidth() / 2);
        canvas.translateXProperty().set(0.0);
        canvas.translateYProperty().set(0.0);
    }

    /**
     * (logically) bind handler point event;
     */
    private void bindHandlerMouseEvent() {
        Arrays.stream(Handler.values()).forEach(handler -> {
            BoundsPoint point = handlers.get(handler);
            Cursor cursor = Util.getCursorSupplier(handler).get();
            if(handler.verticalResize == Handler.VerticalResize.TOP) {
                setHandlerMouseEvent(point, cursor, new TopResizeStrategy().handle(point, this));
            }
            else if(handler.verticalResize == Handler.VerticalResize.BOTTOM) {
                setHandlerMouseEvent(point, cursor, new BottomResizeStrategy().handle(point, this));
            }

            if(handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                setHandlerMouseEvent(point, cursor, new LeftResizeStrategy().handle(point, this));
            }
            else if(handler.horizontalResize == Handler.HorizontalResize.RIGHT) {
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
            Node container = getContainer();
            setX(container.getLayoutX() + container.getTranslateX());
            setY(container.getLayoutY() + container.getTranslateY());
            container.setLayoutX(getX());
            container.setLayoutY(getY());
            container.translateXProperty().set(0);
            container.translateYProperty().set(0);
            showBoundsBesides(point);
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

        getContainer().getChildren().add(getShape().getContainer());

        // drag;
        getContainer().setOnMouseDragged(event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                getContainer().layoutXProperty().set(getContainer().getLayoutX() + event.getX() - getWidth() / 2);
                getContainer().layoutYProperty().set(getContainer().getLayoutY() + event.getY() - getHeight() / 2);
            }
        });

        // show bounds with handlers;
        getContainer().setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                // ctrl key can unselect a shape;
                if(event.isControlDown()) {
                    setSelected(!getSelected());
                    if(getSelected()) {
                        show();
                    }
                    else {
                        hide();
                    }
                    event.consume();
                    return;
                }
                ShapeHolder.getAllShapes().forEach(shape -> {
                    if(!this.equals(shape)) {
                        shape.setSelected(false);
                        ((ShapeImageBoundsDecorator) shape).hide();
                    }
                });
                if(!getSelected()) {
                    show();
                }
                setSelected(true);
            }
            event.consume();
        });
    }

    /**
     * show bounds with 8 handler points for the bound shape;
     */
    public void show() {
        getContainer().getChildren().add(draw());
        List<Node> nodes = handlers.values().stream().map(BoundsPoint::getCanvas).collect(Collectors.toList());
        getContainer().getChildren().addAll(nodes);
    }

    /**
     * hide bounds with 8 handler points for the bound shape;
     */
    public void hide() {
        getContainer().getChildren().remove(getCanvas());
        List<Node> nodes = handlers.values().stream().map(BoundsPoint::getCanvas).collect(Collectors.toList());
        getContainer().getChildren().removeAll(nodes);
    }

    private static class Util {
        private static Supplier<Cursor> getCursorSupplier(Handler handler) {
            return () -> {
                if(handler.verticalResize == Handler.VerticalResize.TOP) {
                    if(handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                        return Cursor.NW_RESIZE;
                    }
                    else if(handler.horizontalResize == Handler.HorizontalResize.RIGHT) {
                        return Cursor.NE_RESIZE;
                    }
                    else {
                        return Cursor.N_RESIZE;
                    }
                }
                else if(handler.verticalResize == Handler.VerticalResize.BOTTOM) {
                    if(handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                        return Cursor.SW_RESIZE;
                    }
                    else if(handler.horizontalResize == Handler.HorizontalResize.RIGHT) {
                        return Cursor.SE_RESIZE;
                    }
                    else {
                        return Cursor.S_RESIZE;
                    }
                }
                else {
                    if(handler.horizontalResize == Handler.HorizontalResize.LEFT) {
                        return Cursor.W_RESIZE;
                    }
                    else {
                        return Cursor.E_RESIZE;
                    }
                }
            };
        }
    }
}
