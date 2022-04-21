package io.aaron.learning.geom.impl;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.Node;
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

    public Rectangle() {
        this(false, 120.0, 60.0);
    }

    public Rectangle(Boolean isSquare) {
        this(isSquare, 120.0, 60.0);
    }

    public Rectangle(Boolean isSquare, Double width, Double height) {
        super(width, height);
        init(isSquare);
    }

    private void init(Boolean square) {
        this.isSquare = square;
        if (square) {
            setWidth(getHeight());
        }
    }

    @Override
    protected Canvas draw() {
        Canvas canvas = new Canvas();
        canvas.setHeight(getHeight() + 2);
        canvas.setWidth(getWidth() + 2);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(getFill());
        context.setStroke(getStroke());
        if (isFilled()) {
            context.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        context.strokeRect(getX(), getY(), getWidth(), getHeight());
        return canvas;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public void addMouseEventHandler(Node node) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("hello, world"));
    }
}
