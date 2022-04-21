package io.aaron.learning.geom;

import io.aaron.learning.geom.operate.Bound;
import io.aaron.learning.geom.operate.base.MouseEventAccessible;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractShape implements MouseEventAccessible {
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
    private Group container;
    /**
     * TODO: to be implemented
     * <ul>
     *     <li>define a enum for borderType field</li>
     * </ul>
     */
    private Double borderWidth;
    private String borderType;

    public AbstractShape() {
        this(0.0, 0.0, 60.0, 60.0);
    }

    protected AbstractShape(Double width, Double height) {
        this(0.0, 0.0, width, height);
    }

    protected AbstractShape(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = 0.0;
        this.filled = true;
        this.stroke = Color.web("#000");
        this.fill = Color.web("#fff");
        init();
    }

    private void init() {

        // put image in container
        container = new Group();
        container.getChildren().add(draw());

        // get bound
        if (!(this instanceof Bound)) {
            bound = Bound.fromShape(this);
        }

        setMouseEventHandler(event -> {
            double xOffset = 0.0, yOffset = 0.0;

            // when clicking primary mouse button
            if (event.getButton() == MouseButton.PRIMARY) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    xOffset = event.getX();
                    yOffset = event.getY();
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    container.translateXProperty().set(container.getTranslateX() + event.getX() - xOffset - getWidth() / 2);
                    container.translateYProperty().set(container.getTranslateY() + event.getY() - yOffset - getHeight() / 2);
                }
            }

            // when clicking is not necessary
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                bound.show();
            } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
                bound.hide();
            }
        });

        addMouseEventHandler();
    }


    public Group provide() {
        return container;
    }

    protected abstract Canvas draw();

    public abstract boolean contains(double x, double y);

    @Override
    public void setMouseEventHandler(EventHandler<? super MouseEvent> handler) {
        getContainer().setOnMouseDragged(handler);
        getContainer().setOnMouseEntered(handler);
        getContainer().setOnMouseExited(handler);
    }
}
