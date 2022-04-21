package io.aaron.learning.geom;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Data;

@Data
public class Bounds {
    private Double x, y;
    private Double width, height;
    private Rectangle bounds;
    private Circle NORTH = new Circle(10.0, Color.web("#00b8f0"));
    private Circle EAST = new Circle(10.0, Color.web("#00b8f0"));
    private Circle SOUTH = new Circle(10.0, Color.web("#00b8f0"));
    private Circle WEST = new Circle(10.0, Color.web("#00b8f0"));
    private Circle NORTH_EAST = new Circle(10.0, Color.web("#00b8f0"));
    private Circle SOUTH_EAST = new Circle(10.0, Color.web("#00b8f0"));
    private Circle SOUTH_WEST = new Circle(10.0, Color.web("#00b8f0"));
    private Circle NORTH_WEST = new Circle(10.0, Color.web("#00b8f0"));

    public Bounds(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        bounds.setStroke(Color.web("#00b8f0"));
        bindBounds();
    }

    private void bindBounds() {
        NORTH_WEST.centerXProperty().bind(bounds.xProperty());
        NORTH_WEST.centerYProperty().bind(bounds.yProperty());
        NORTH.centerXProperty().bind(bounds.xProperty().add(width).divide(2));
        NORTH.centerYProperty().bind(bounds.yProperty());
        NORTH_EAST.centerXProperty().bind(bounds.xProperty().add(width));
        NORTH_EAST.centerYProperty().bind(bounds.yProperty());
        WEST.centerXProperty().bind(bounds.xProperty());
        WEST.centerYProperty().bind(bounds.yProperty().add(height).divide(2));
        EAST.centerXProperty().bind(bounds.xProperty().add(width));
        EAST.centerYProperty().bind(bounds.yProperty().add(height).divide(2));
        SOUTH_WEST.centerXProperty().bind(bounds.xProperty());
        SOUTH_WEST.centerYProperty().bind(bounds.yProperty().add(height));
        SOUTH.centerXProperty().bind(bounds.xProperty().add(width).divide(2));
        SOUTH.centerYProperty().bind(bounds.yProperty().add(height));
        SOUTH_EAST.centerXProperty().bind(bounds.xProperty().add(width));
        SOUTH_WEST.centerYProperty().bind(bounds.yProperty().add(height));
    }
}
