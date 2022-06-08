package io.aaron.learning.geom.base;

import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;

@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractShape implements Cloneable {

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private DoubleProperty height = new SimpleDoubleProperty();
    private DoubleProperty rotation = new SimpleDoubleProperty();
    private IntegerProperty verticalIndex = new SimpleIntegerProperty();
    private BooleanProperty filled = new SimpleBooleanProperty();
    private BooleanProperty lined = new SimpleBooleanProperty();
    private BooleanProperty selected = new SimpleBooleanProperty();
    private DoubleProperty lineWidth = new SimpleDoubleProperty();
    private DoubleProperty opacity = new SimpleDoubleProperty();
    private Paint strokePaint;
    private Paint fillPaint;
    private Double[] lineDashes;
    private Canvas canvas;

    {
        x.set(0d);
        y.set(0d);
        rotation.set(0d);
        filled.set(true);
        lined.set(true);
        selected.set(false);
        lineWidth.set(1d);
        opacity.set(1d);
        strokePaint = Color.web("#000");
        fillPaint = Color.web("#fff");
        lineDashes = new Double[]{};
        canvas = new Canvas();
        canvas.layoutXProperty().bindBidirectional(x);
        canvas.layoutYProperty().bindBidirectional(y);
    }

    protected AbstractShape() {}

    protected AbstractShape(AbstractShape shape) {
        x.set(shape.getX());
        y.set(shape.getY());
        width.set(shape.getWidth());
        height.set(shape.getHeight());
        rotation.set(shape.getRotation());
        verticalIndex.set(shape.getVerticalIndex());
        filled.set(shape.isFilled());
        lined.set(shape.isLined());
        selected.set(shape.isSelected());
        lineWidth.set(shape.getLineWidth());
        opacity.set(shape.getOpacity());
        strokePaint = shape.getStrokePaint();
        fillPaint = shape.getFillPaint();
        lineDashes = shape.getLineDashes().clone();
        draw();
    }

    protected AbstractShape(Double width, Double height) {
        this(0d, 0d, width, height);
    }

    protected AbstractShape(Double x, Double y, Double width, Double height) {
        this.x.set(x);
        this.y.set(y);
        this.width.set(width);
        this.height.set(height);
        if(!(this instanceof AbstractShapeDecorator)) {
            draw();
        }
    }

    public Node draw() {
        canvas.setWidth(width.get() + 2 * lineWidth.get());
        canvas.setHeight(height.get() + 2 * lineWidth.get());
        canvas.setOpacity(opacity.get());
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0d, 0d, canvas.getWidth(), canvas.getHeight());
        context.setLineDashes(Arrays.stream(lineDashes).mapToDouble(Double::doubleValue).toArray());
        context.setFill(fillPaint);
        context.setStroke(strokePaint);
        return canvas;
    }

    /**
     * Apply style property to shape, and redraw it.
     *
     * @param style shape style.
     */
    public void applyStyle(ShapeStyleProperty style) {
        filled.set(style.getFilled());
        lined.set(style.getLined());
        strokePaint = style.getStrokePaint();
        fillPaint = style.getFillPaint();
        lineDashes = style.getLineDashes().clone();
        lineWidth.set(style.getLineWidth());
        draw();
    }

    /**
     * Apply a pair of coordinate offset to shape;
     *
     * @param offsetX offsetX
     * @param offsetY offsetY
     */
    public void move(double offsetX, double offsetY) {
        x.set(getX() + offsetX);
        y.set(getY() + offsetY);
    }

    /**
     * Resize the shape to designate size;
     *
     * @param width width;
     * @param height height;
     */
    public void resize(double width, double height) {
        this.width.set(width);
        this.height.set(height);
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

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public double getRotation() {
        return rotation.get();
    }

    public DoubleProperty rotationProperty() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation.set(rotation);
    }

    public int getVerticalIndex() {
        return verticalIndex.get();
    }

    public IntegerProperty verticalIndexProperty() {
        return verticalIndex;
    }

    public void setVerticalIndex(int verticalIndex) {
        this.verticalIndex.set(verticalIndex);
    }

    public boolean isFilled() {
        return filled.get();
    }

    public BooleanProperty filledProperty() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled.set(filled);
    }

    public boolean isLined() {
        return lined.get();
    }

    public BooleanProperty linedProperty() {
        return lined;
    }

    public void setLined(boolean lined) {
        this.lined.set(lined);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public double getLineWidth() {
        return lineWidth.get();
    }

    public DoubleProperty lineWidthProperty() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth.set(lineWidth);
    }

    public double getOpacity() {
        return opacity.get();
    }

    public DoubleProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    public Paint getStrokePaint() {
        return strokePaint;
    }

    public void setStrokePaint(Paint strokePaint) {
        this.strokePaint = strokePaint;
    }

    public Paint getFillPaint() {
        return fillPaint;
    }

    public void setFillPaint(Paint fillPaint) {
        this.fillPaint = fillPaint;
    }

    public Double[] getLineDashes() {
        return lineDashes;
    }

    public void setLineDashes(Double[] lineDashes) {
        this.lineDashes = lineDashes;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
