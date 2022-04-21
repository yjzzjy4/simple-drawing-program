package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.RectangleWrapper;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractShape {
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private Double rotation;
    private Integer layer;
    private Boolean filled;
    private Color strokeColor;
    private Color fillColor;
    private Bounds bounds;

    {
        rotation = 0.0;
        filled = true;
        strokeColor = Color.web("#000");
        fillColor = Color.web("#fff");
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

    public RectangleWrapper getBounds() {
        return null;
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public abstract void paint(Group group, double x, double y);
}
