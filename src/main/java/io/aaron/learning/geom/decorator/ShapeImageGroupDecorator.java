package io.aaron.learning.geom.decorator;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.geom.decorator.base.Selectable;
import io.aaron.learning.geom.shape.RectangleImage;
import io.aaron.learning.manage.ShapeHolder;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShapeImageGroupDecorator extends AbstractShapeDecorator implements Selectable {

    private ShapeImageBoundsDecorator bounds;
    private List<AbstractShapeDecorator> children;

    public ShapeImageGroupDecorator(@NonNull List<AbstractShapeDecorator> shapes) {
        this.children = shapes;
        // top left (x, y), bottom right (x, y);
        final double[] anchors = {Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
        shapes.forEach(s -> {
            double x = s.getX(), y = s.getY();
            anchors[0] = Math.min(x, anchors[0]);
            anchors[1] = Math.min(y, anchors[1]);
            anchors[2] = Math.max(x + s.getWidth(), anchors[2]);
            anchors[3] = Math.max(y + s.getHeight(), anchors[3]);
            System.out.println("x: " + x + ", " + ", y: " + y + ", width: " + s.getWidth() + ", height: " + s.getHeight());
        });
        AbstractShape rect = new RectangleImage(anchors[0], anchors[1], Math.abs(anchors[2] - anchors[0]), Math.abs(anchors[3] - anchors[1]));
        rect.setFilled(false);
        rect.setStrokePaint(Color.TRANSPARENT);
        bounds = new ShapeImageBoundsDecorator(rect);
        System.out.println(Arrays.toString(anchors));
        setX(anchors[0]);
        setY(anchors[1]);
        setWidth(Math.abs(anchors[2] - anchors[0]));
        setHeight(Math.abs(anchors[3] - anchors[1]));
        setContainer(bounds.getContainer());
        children.forEach(c -> {
            c.setX(c.getX() - getX());
            c.setY(c.getY() - getY());
            c.getContainer().layoutXProperty().set(c.getX());
            c.getContainer().layoutYProperty().set(c.getY());
            getContainer().getChildren().add(getContainer().getChildren().size() - 1, c.getContainer());
        });
        init();
        draw();
    }

    private void init() {
        Node container = getContainer();
        // clear event handlers;
        container.setOnMouseClicked(null);
        bounds.getHandlers().values().forEach(point -> {
            Canvas canvas = point.getCanvas();
            canvas.setOnMouseEntered(null);
            canvas.setOnMouseExited(null);
            canvas.setOnMousePressed(null);
//            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, canvas.getOnMouseDragged());
            canvas.setOnMouseReleased(null);
        });
        // clicked;
        container.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                if(!contains(event.getX(), event.getY())) {
                    return;
                }
                // ctrl key can unselect a shape;
                if(event.isControlDown()) {
                    setSelected(!getSelected());
                    if(getSelected()) {
                        bounds.show();
                    }
                    else {
                        bounds.hide();
                    }
                    event.consume();
                    return;
                }
                ShapeHolder.getAllShapes().forEach(shape -> {
                    if(!this.equals(shape)) {
                        shape.setSelected(false);
                        ((Selectable) shape).hide();
                    }
                });
                if(!getSelected()) {
                    bounds.show();
                }
                setSelected(true);
            }
            event.consume();
        });
    }

    @Override
    public Node draw() {
        GraphicsContext context = ((Canvas) super.draw()).getGraphicsContext2D();
        context.strokeRect(getLineWidth(), getLineWidth(), getWidth(), getHeight());
        for(AbstractShape shape: children) {
            shape.draw();
        }
        return getCanvas();
    }

    @Override
    public boolean contains(double x, double y) {
        for(AbstractShapeDecorator c : children) {
            if(c.contains(x - c.getX(), y - c.getY())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractShape clone() {
        return null;
    }

    @Override
    public void show() {
        bounds.show();
    }

    @Override
    public void hide() {
        bounds.hide();
    }
}
