package io.aaron.learning.geom;

import io.aaron.learning.geom.operate.Bound;
import io.aaron.learning.geom.operate.base.Draggable;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractShape implements Draggable {
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private Double rotation;
    private Integer layer;
    private boolean filled;
    private Paint stroke;
    private Paint fill;
    private Paint border;
    private Bound bound;
    /**
     * TODO: to be implemented
     * <ul>
     *     <li>define a enum for borderType field</li>
     * </ul>
     */
    private Double borderWidth;
    private String borderType;

    {
        x = 0.0;
        y = 0.0;
        rotation = 0.0;
        filled = true;
        stroke = Color.web("#000");
        fill = Color.web("#fff");
    }

    protected AbstractShape(Double width, Double height) {
        this.width = width;
        this.height = height;
    }

    protected AbstractShape(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract boolean contains(double x, double y);

    public abstract Group provide();

}
