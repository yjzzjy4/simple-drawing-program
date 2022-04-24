package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.BoundsPoint;
import io.aaron.learning.geom.impl.OvalImage;
import io.aaron.learning.geom.impl.RectangleImage;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
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

    private void bindHandlerPosition(Handler handler, double offsetX, double offsetY) {
        BoundsPoint boundsPoint = handlers.get(handler);
        Canvas canvas = boundsPoint.getCanvas();
        canvas.layoutXProperty().bind(parent.getContainer().layoutXProperty()
                .subtract(parent.getContainer().layoutXProperty()
                        .subtract(offsetX)
                        .add(POINT_RADIUS)));
        canvas.layoutYProperty().bind(parent.getContainer().layoutYProperty()
                .subtract(parent.getContainer().layoutYProperty()
                        .subtract(offsetY)
                        .add(POINT_RADIUS)));
    }

    // TODO: bind anchors to bounds;
    private void init() {
        // eight handlers;
        handlers.put(Handler.TOP, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.TOP, parent.getWidth() / 2, 0.0);
        handlers.put(Handler.TOP_RIGHT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.TOP_RIGHT, parent.getWidth(), 0.0);
        handlers.put(Handler.RIGHT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.RIGHT, parent.getWidth(), parent.getHeight() / 2);
        handlers.put(Handler.BOTTOM_RIGHT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.BOTTOM_RIGHT, parent.getWidth(), parent.getHeight());
        handlers.put(Handler.BOTTOM, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.BOTTOM, parent.getWidth() / 2, parent.getHeight());
        handlers.put(Handler.BOTTOM_LEFT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.BOTTOM_LEFT, 0.0, parent.getHeight());
        handlers.put(Handler.LEFT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.LEFT, 0.0, parent.getHeight() / 2);
        handlers.put(Handler.TOP_LEFT, new BoundsPoint(10.0));
        bindHandlerPosition(Handler.TOP_LEFT, 0.0, 0.0);
    }

    public void show() {
        parent.getContainer().getChildren().add(draw());
    }

    public void hide() {
        parent.getContainer().getChildren().remove(getCanvas());
    }

    public void showWithHandlers() {
        List<Node> nodes = handlers.values().stream().map(OvalImage::getCanvas).collect(Collectors.toList());
        parent.getContainer().getChildren().addAll(nodes);
    }

    public void hideHandlers() {
        List<Node> nodes = handlers.values().stream().map(OvalImage::getCanvas).collect(Collectors.toList());
        parent.getContainer().getChildren().removeAll(nodes);
    }

    public static BoundsImage fromShape(AbstractShape shape) {
        BoundsImage bounds = BoundsImage.builder()
                .parent(shape)
                .x(shape.getX())
                .y(shape.getY())
                .width(shape.getWidth())
                .height(shape.getHeight())
                .square(false)
                .filled(false)
                .stroke(Color.LIGHTBLUE)
                .build();
        bounds.init();
        return bounds;
    }
}
