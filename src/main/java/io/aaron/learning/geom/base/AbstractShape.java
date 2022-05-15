package io.aaron.learning.geom.base;

import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractShape implements Cloneable {
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private Double rotation;
    private Integer zIndex;
    private Boolean filled;
    private Boolean selected;
    private Paint strokePaint;
    private Paint fillPaint;
    private Double[] lineDashes;
    private Double lineWidth;
    private Canvas canvas;

    {
        zIndex = 0;
        rotation = 0.0;
        filled = true;
        selected = false;
        strokePaint = Color.web("#000");
        fillPaint = Color.web("#fff");
        lineWidth = 1.0;
        lineDashes = null;
        canvas = new Canvas();
    }

    protected AbstractShape() {
        this(0.0, 0.0, 80.0, 80.0);
    }

    protected AbstractShape(AbstractShape shape) {
        x = shape.getX();
        y = shape.getY();
        width = shape.getWidth();
        height = shape.getHeight();
        rotation = shape.getRotation();
        zIndex = shape.getZIndex() + 1;
        filled = shape.getFilled();
        selected = shape.getSelected();
        strokePaint = shape.getStrokePaint();
        fillPaint = shape.getFillPaint();
        lineDashes = Objects.isNull(shape.getLineDashes()) ? null : shape.getLineDashes().clone();
        lineWidth = shape.getLineWidth();
        draw();
    }

    protected AbstractShape(Double width, Double height) {
        this(0.0, 0.0, width, height);
    }

    protected AbstractShape(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if(!(this instanceof AbstractShapeDecorator)) {
            draw();
        }
    }

    public Node draw() {
        if(canvas == null) {
            canvas = new Canvas(getWidth() + 2 * getLineWidth(), getHeight() + 2 * getLineWidth());
        }
        else {
            canvas.setHeight(getHeight() + 2 * getLineWidth());
            canvas.setWidth(getWidth() + 2 * getLineWidth());
        }
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(getFillPaint());
        context.setStroke(getStrokePaint());
        return canvas;
    }

    public void applyChange(ShapeProperty props) {
        x = props.getX();
        y = props.getY();
        width = props.getWidth();
        height = props.getHeight();
        rotation = props.getRotation();
        zIndex = props.getZIndex() + 1;
        filled = props.getFilled();
        selected = props.getSelected();
        strokePaint = props.getStrokePaint();
        fillPaint = props.getFillPaint();
        lineDashes = Objects.isNull(props.getLineDashes()) ? null : props.getLineDashes().clone();
        lineWidth = props.getLineWidth();
        draw();
    }

    /**
     * Check whether the coordinate (x, y) is within or on the shape,
     * the coordinate is relative, that is, we consider the top left coordinate of the shape being (0, 0).
     *
     * @param x x coordinate;
     * @param y y coordinate;
     * @return the result.
     */
    public abstract boolean contains(double x, double y);

    public abstract AbstractShape clone();
}
