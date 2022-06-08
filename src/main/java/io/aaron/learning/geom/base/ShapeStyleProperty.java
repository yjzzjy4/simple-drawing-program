package io.aaron.learning.geom.base;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Objects;

public class ShapeStyleProperty {

    private Boolean filled;
    private Boolean lined;
    private Paint strokePaint;
    private Paint fillPaint;
    private Double[] lineDashes;
    private Double lineWidth;

    {
        filled = true;
        lined = true;
        strokePaint = Color.web("#000");
        fillPaint = Color.web("#fff");
        lineDashes = new Double[]{};
        lineWidth = 1d;
    }

    public Boolean getFilled() {
        return filled;
    }

    public void setFilled(Boolean filled) {
        this.filled = filled;
    }

    public Boolean getLined() {
        return lined;
    }

    public void setLined(Boolean lined) {
        this.lined = lined;
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

    public Double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public static ShapeStyleProperty extractFrom(AbstractShape shape) {
        return new ShapeStyleProperty() {{
            setFilled(shape.isFilled());
            setLined(shape.isLined());
            setStrokePaint(shape.getStrokePaint());
            setFillPaint(shape.getFillPaint());
            setLineDashes(shape.getLineDashes());
            setLineWidth(shape.getLineWidth());
        }};
    }

    public void updateFrom(ShapeStyleProperty another) {
        filled = Objects.isNull(another.getFilled()) || another.getFilled();
        filled = Objects.isNull(another.getLined()) || another.getLined();
        strokePaint = Objects.isNull(another.getStrokePaint()) ? Color.web("#000") : another.getStrokePaint();
        fillPaint = Objects.isNull(another.getFillPaint()) ? Color.web("#fff") : another.getFillPaint();
        lineDashes = Objects.isNull(another.getLineDashes()) ? new Double[]{} : another.getLineDashes();
        lineWidth = Objects.isNull(another.getLineWidth()) ? 1d : another.getLineWidth();
    }
}
