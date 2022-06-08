package io.aaron.learning.geom.decorator;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.ShapeStyleProperty;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.geom.shape.BoundPointImage;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
public class ShapeImageBoundDecorator extends AbstractBoundDecorator {

    private final Map<Handler, BoundPointImage> handlers = new HashMap<>();

    public ShapeImageBoundDecorator(AbstractShape shape) {
        super(shape);
        init();
    }

    @Override
    public AbstractBoundDecorator.Handler hitHandler(double x, double y) {
        for(Map.Entry<Handler, BoundPointImage> e : handlers.entrySet()) {
            BoundPointImage p = e.getValue();
            if(p.contains(x - p.getX(), y - p.getY())) {
                return e.getKey();
            }
        }
        return null;
    }

    @Override
    public Node draw() {
        setOpacity(1.0);
        GraphicsContext context = ((Canvas) super.draw()).getGraphicsContext2D();
        context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        return getCanvas();
    }

    @Override
    public boolean contains(double x, double y) {
        return getShape().contains(x, y);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        placeHandlers();
    }

    @Override
    public ShapeImageBoundDecorator clone() {
        AbstractShape shapeCopy = getShape().clone();
        return new ShapeImageBoundDecorator(shapeCopy);
    }

    @Override
    public void hideHandlers() {
        getContainer().getChildren().removeAll(handlers.values()
                                                       .stream()
                                                       .map(BoundPointImage::getCanvas)
                                                       .collect(Collectors.toList()));
    }

    @Override
    public void showHandlers() {
        getContainer().getChildren().addAll(handlers.values()
                                                       .stream()
                                                       .map(BoundPointImage::getCanvas)
                                                       .collect(Collectors.toList()));
    }

    @Override
    public void applyStyle(ShapeStyleProperty style) {
        getShape().applyStyle(style);
        draw();
    }

    private void hideBoundsExcept(@NonNull BoundPointImage point) {
        getContainer().getChildren().remove(getCanvas());
        getContainer().getChildren()
                      .removeAll(handlers.values()
                                         .stream()
                                         .filter(p -> !p.equals(point))
                                         .map(AbstractShape::getCanvas)
                                         .collect(Collectors.toList()));
    }

    private void showBoundsBesides(@NonNull BoundPointImage point) {
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
        BoundPointImage point = handlers.get(handler);
        point.setX(offsetX - getHandlerDiameter() / 2);
        point.setY(offsetY - getHandlerDiameter() / 2);
        Canvas canvas = point.getCanvas();
//        canvas.layoutXProperty().set(offsetX - point.getHeight() / 2);
//        canvas.layoutYProperty().set(offsetY - point.getWidth() / 2);
        canvas.translateXProperty().set(0.0);
        canvas.translateYProperty().set(0.0);
    }

    /**
     * (Actually) bind handler point event.
     *
     * @param point       handler point;
     * @param cursor      mouse style when hovering;
     * @param dragHandler drag handler.
     */
    private void setHandlerMouseEvent(@NonNull BoundPointImage point, Cursor cursor,
                                      EventHandler<? super MouseEvent> dragHandler) {
        point.getCanvas().setOnMouseEntered(event -> point.getCanvas().setCursor(cursor));
        point.getCanvas().setOnMouseExited(event -> point.getCanvas().setCursor(Cursor.DEFAULT));
        point.getCanvas().setOnMousePressed(event -> hideBoundsExcept(point));
        // resize finished;
        point.getCanvas().setOnMouseReleased(event -> {
            Node container = getContainer();
            setX(container.getLayoutX() + container.getTranslateX());
            setY(container.getLayoutY() + container.getTranslateY());
            container.setLayoutX(getX());
            container.setLayoutY(getY());
            container.translateXProperty().set(0);
            container.translateYProperty().set(0);
            showBoundsBesides(point);
            event.consume();
        });
        point.getCanvas().addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
    }

    private void init() {
        getContainer().layoutXProperty().bindBidirectional(getShape().xProperty());
        getContainer().layoutYProperty().bindBidirectional(getShape().yProperty());
        getCanvas().layoutXProperty().unbindBidirectional(xProperty());
        getCanvas().layoutYProperty().unbindBidirectional(yProperty());
        getCanvas().setLayoutX(0d);
        getCanvas().setLayoutY(0d);

        // 8 handlers;
        handlers.put(Handler.TOP, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.TOP_RIGHT, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.RIGHT, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.BOTTOM_RIGHT, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.BOTTOM, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.BOTTOM_LEFT, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.LEFT, new BoundPointImage(getHandlerDiameter()));
        handlers.put(Handler.TOP_LEFT, new BoundPointImage(getHandlerDiameter()));
        placeHandlers();
        show();
    }

    /**
     * Show bounds with 8 handler points for the bound shape.
     */
    @Override
    public void show() {
        getContainer().getChildren().add(draw());
        List<Node> nodes = handlers.values().stream().map(BoundPointImage::getCanvas).collect(Collectors.toList());
        getContainer().getChildren().addAll(nodes);
    }

    /**
     * Hide bounds with 8 handler points for the bound shape.
     */
    @Override
    public void hide() {
        getContainer().getChildren().remove(getCanvas());
        List<Node> nodes = handlers.values().stream().map(BoundPointImage::getCanvas).collect(Collectors.toList());
        getContainer().getChildren().removeAll(nodes);
    }

    public Map<Handler, BoundPointImage> getHandlers() {
        return handlers;
    }
}
