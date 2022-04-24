package io.aaron.learning.geom;

import io.aaron.learning.geom.impl.BoundsPoint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.swing.event.HyperlinkEvent;

@Data
@SuperBuilder
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
    private Boolean selected;
    private Paint stroke;
    private Paint fill;
    private BoundsImage bounds;
    private Group container;
    private Double strokeWidth;
    private String strokeType;

    {
        rotation = 0.0;
        filled = true;
        selected = false;
        stroke = Color.web("#000");
        fill = Color.web("#fff");
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
    }

    protected final void encapsulateShape() {
        Node shape = draw();
        container.getChildren().addAll(shape);

        // get bounds;
        if(!(this instanceof BoundsImage) && !(this instanceof BoundsPoint)) {
            bounds = BoundsImage.fromShape(this);
        }

        // show bounds;
        container.setOnMouseEntered(event -> {
            bounds.show();
        });

        container.setOnMouseExited(event -> {
            bounds.hide();
        });

        // drag;
        container.setOnMouseDragged(event -> {
            if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                container.translateXProperty().set(container.getTranslateX() + event.getX() - getWidth() / 2);
                container.translateYProperty().set(container.getTranslateY() + event.getY() - getHeight() / 2);
            }
        });

        // show bounds with handlers;
        container.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                selected = !selected;
                if(selected) {
                    bounds.showWithHandlers();
                }
                else {
                    bounds.hideHandlers();
                }
            }
        });
    }

    public abstract Node draw();

    public boolean contains(double x, double y) {
        return false;
    }
}
