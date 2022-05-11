package io.aaron.learning.geom.base;

import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

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
    private Integer layer;
    private Boolean filled;
    private Boolean selected;
    private Paint strokePaint;
    private Paint fillPaint;
    private Double[] lineDashes;
    private Group container;
    private Canvas canvas;
    private Double lineWidth;

    {
        rotation = 0.0;
        filled = true;
        selected = false;
        strokePaint = Color.web("#000");
        fillPaint = Color.web("#fff");
        lineWidth = 1.0;
        lineDashes = null;
        container = new Group();
    }

    protected AbstractShape() {
        this(0.0, 0.0, 80.0, 80.0);
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
            init();
        }
    }

    private void init() {
        container.getChildren().add(draw());
    }

    public abstract Node draw();

    public AbstractShape getDefault() {
        return null;
    }

    public void applyChange() {
        draw();
    }

    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
