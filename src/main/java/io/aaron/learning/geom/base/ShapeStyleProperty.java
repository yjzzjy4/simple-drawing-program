package io.aaron.learning.geom.base;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ShapeStyleProperty {
    private Boolean filled;
    private Boolean selected;
    private Paint strokePaint;
    private Paint fillPaint;
    private Double[] lineDashes;
    private Double lineWidth;

    {
        filled = true;
        selected = false;
        strokePaint = Color.web("#000");
        fillPaint = Color.web("#fff");
        lineDashes = new Double[]{};
        lineWidth = 1.0;
    }

    public static ShapeStyleProperty extractFrom(AbstractShape shape) {
        return ShapeStyleProperty.builder()
                            .filled(shape.getFilled())
                            .selected(shape.getSelected())
                            .strokePaint(shape.getStrokePaint())
                            .fillPaint(shape.getFillPaint())
                            .lineDashes(shape.getLineDashes().clone())
                            .lineWidth(shape.getLineWidth())
                            .build();
    }
}
