package io.aaron.learning.geom.decorator;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.EqualProportional;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.geom.shape.BoundsPoint;
import io.aaron.learning.geom.strategy.resize.*;
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
         * 8 handlers.
         */
        TOP(HorizontalResize.FORBID, VerticalResize.TOP),
        TOP_RIGHT(HorizontalResize.RIGHT, VerticalResize.TOP),
        RIGHT(HorizontalResize.RIGHT, VerticalResize.FORBID),
        BOTTOM_RIGHT(HorizontalResize.RIGHT, VerticalResize.BOTTOM),
        BOTTOM(HorizontalResize.FORBID, VerticalResize.BOTTOM),
        BOTTOM_LEFT(HorizontalResize.LEFT, VerticalResize.BOTTOM),
        LEFT(HorizontalResize.LEFT, VerticalResize.FORBID),
        TOP_LEFT(HorizontalResize.LEFT, VerticalResize.TOP);

        private final HorizontalResize horizontalResize;
        private final VerticalResize verticalResize;

        Handler(HorizontalResize horizontalResize, VerticalResize verticalResize) {
            this.horizontalResize = horizontalResize;
            this.verticalResize = verticalResize;
        }

        public enum HorizontalResize {
            LEFT, RIGHT, FORBID
        }

        public enum VerticalResize {
            TOP, BOTTOM, FORBID
        }
    }

    public ShapeImageBoundsDecorator(AbstractShape shape) {
        super(shape);
        init();
    }

    @Override
    public Node draw() {
        GraphicsContext context = ((Canvas) super.draw()).getGraphicsContext2D();
        if(getFilled()) {
            context.fillRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        }
        context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        return getCanvas();
    }

    @Override
    public boolean contains(double x, double y) {
        return getShape().contains(x, y);
    }

    @Override
    public AbstractShape clone() {
        return null;
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
     * Place all handlers in the container.
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
     * Bind handler to the corner position.
     *
     * @param handler handler point;
     * @param offsetX horizontal offset;
     * @param offsetY vertical offset.
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
     * (Logically) bind handler point event.
     */
    private void bindHandlerMouseEvent() {
        Arrays.stream(Handler.values()).forEach(handler -> {
            BoundsPoint point = handlers.get(handler);
            Cursor cursor = Util.getCursorSupplier(handler).get();
            if(getShape() instanceof EqualProportional) {
                // equal proportional resize strategy set;
                switch(handler) {
                    case TOP:
                        setHandlerMouseEvent(point, cursor, new EqualProportionalTopResizeStrategy().handle(point, this));
                        break;
                    case TOP_RIGHT:
                        break;
                    case RIGHT:
                        setHandlerMouseEvent(point, cursor, (new EqualProportionalRightResizeStrategy().handle(point, this)));
                        break;
                    case BOTTOM_RIGHT:
                        break;
                    case BOTTOM:
                        setHandlerMouseEvent(point, cursor, new EqualProportionalBottomResizeStrategy().handle(point, this));
                        break;
                    case BOTTOM_LEFT:
                        break;
                    case LEFT:
                        setHandlerMouseEvent(point, cursor, new EqualProportionalLeftResizeStrategy().handle(point, this));
                        break;
                    case TOP_LEFT:
                        break;
                }
            }
            else {
                // normal resize strategy set;
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
            }
        });
    }

    /**
     * (Actually) bind handler point event.
     *
     * @param point       handler point;
     * @param cursor      mouse style when hovering;
     * @param dragHandler drag handler.
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
        handlers.put(Handler.TOP, new BoundsPoint());
        handlers.put(Handler.TOP_RIGHT, new BoundsPoint());
        handlers.put(Handler.RIGHT, new BoundsPoint());
        handlers.put(Handler.BOTTOM_RIGHT, new BoundsPoint());
        handlers.put(Handler.BOTTOM, new BoundsPoint());
        handlers.put(Handler.BOTTOM_LEFT, new BoundsPoint());
        handlers.put(Handler.LEFT, new BoundsPoint());
        handlers.put(Handler.TOP_LEFT, new BoundsPoint());
        bindHandlerMouseEvent();
        placeHandlers();

        // drag;
        getContainer().setOnMouseDragged(event -> {
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if(!getShape().contains(event.getX(), event.getY())) {
                    return;
                }
                getContainer().layoutXProperty().set(getContainer().getLayoutX() + event.getX() - getWidth() / 2);
                getContainer().layoutYProperty().set(getContainer().getLayoutY() + event.getY() - getHeight() / 2);
            }
        });

        // show bounds with handlers;
        getContainer().setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                if(!getShape().contains(event.getX(), event.getY())) {
                    return;
                }
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
     * Show bounds with 8 handler points for the bound shape.
     */
    public void show() {
        getContainer().getChildren().add(draw());
        List<Node> nodes = handlers.values().stream().map(BoundsPoint::getCanvas).collect(Collectors.toList());
        getContainer().getChildren().addAll(nodes);
    }

    /**
     * Hide bounds with 8 handler points for the bound shape.
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
