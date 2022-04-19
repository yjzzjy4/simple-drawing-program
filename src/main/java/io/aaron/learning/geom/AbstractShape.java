package io.aaron.learning.geom;

import lombok.Data;

@Data
public abstract class AbstractShape implements Shape {
    private Double x, y, width, height, rotation;
    private Integer layer;
    private Boolean filled;
    private String borderColor;
    private String filledColor;
    private com.sun.javafx.geom.Shape innerShape;
}
