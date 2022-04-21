package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import io.aaron.learning.geom.operate.Bound;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Rectangle extends AbstractShape {
    private boolean isSquare;
    private Group container;

    public Rectangle() {
        init(false);
    }

    public Rectangle(Boolean isSquare) {
        init(isSquare);
    }

    public Rectangle(Boolean isSquare, Double width, Double height) {
        super(width, height);
        init(isSquare);
    }

    private void init(Boolean square) {
        this.isSquare = square;
        if (square) {
            setWidth(80.0);
            setHeight(80.0);
        } else {
            setWidth(120.0);
            setHeight(60.0);
        }

        // invoke draw
        container = new Group(draw());

        // get bound
        if (!(this instanceof Bound)) {
            setBound(Bound.fromShape(this));
            container.getChildren().add(getBound().draw());
        }

        // event
        setOnMouseDragged(event -> {
            double xOffset = 0.0, yOffset = 0.0;
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                xOffset = event.getX();
                yOffset = event.getY();
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                container.translateXProperty().set(container.getTranslateX() + event.getX() - xOffset - getWidth() / 2);
                container.translateYProperty().set(container.getTranslateY() + event.getY() - yOffset - getHeight() / 2);
            }
        });
    }

    @Override
    public Group provide() {
        return container;
    }

    protected Canvas draw() {
        Canvas canvas = new Canvas();
        canvas.setHeight(getHeight() + 2);
        canvas.setWidth(getWidth() + 2);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(getFill());
        context.setStroke(getStroke());
        if (this instanceof Bound) {
            context.setLineWidth(3);
            context.setLineDashes(5);
        }
        if (isFilled()) {
            context.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        context.strokeRect(getX(), getY(), getWidth(), getHeight());
        return canvas;
    }

    @Override
    public void setOnMouseDragged(EventHandler<? super MouseEvent> handler) {
        container.setOnMouseDragged(handler);
    }

    @Override
    public Bound getBound() {
        return Bound.fromShape(this);
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    public void setContainer(Group container) {
        throw new UnsupportedOperationException("图形类一经创建不可替换！");
    }
}
