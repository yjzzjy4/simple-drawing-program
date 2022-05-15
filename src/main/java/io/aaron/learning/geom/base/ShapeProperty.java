package io.aaron.learning.geom.base;

import javafx.scene.paint.Paint;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@SuperBuilder
public class ShapeProperty {
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

    public static ShapeProperty extractFrom(AbstractShape shape) {
        return ShapeProperty.builder()
                            .x(shape.getX())
                            .y(shape.getY())
                            .width(shape.getWidth())
                            .height(shape.getHeight())
                            .rotation(shape.getRotation())
                            .zIndex(shape.getZIndex())
                            .filled(shape.getFilled())
                            .selected(shape.getSelected())
                            .strokePaint(shape.getStrokePaint())
                            .fillPaint(shape.getFillPaint())
                            .lineDashes(Objects.isNull(shape.getLineDashes()) ? null : shape.getLineDashes().clone())
                            .lineWidth(shape.getLineWidth())
                            .build();
    }
}
