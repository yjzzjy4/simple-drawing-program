package io.aaron.learning.geom.operate;

import io.aaron.learning.geom.AbstractShape;
import io.aaron.learning.geom.impl.Rectangle;
import io.aaron.learning.geom.operate.constant.BoundDotPosition;
import javafx.scene.paint.Color;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * @author lishuang
 * @since 2022/04/21 11:48:15
 */
@SuperBuilder
public class Bound extends Rectangle {
    private AbstractShape parent;
    private Map<BoundDotPosition, Dot> dotMap;

    private Bound() {
        super();
        init();
    }

    private void init() {
        // TODO: add 8 dots at the right place for zooming the parent shape...
    }

    public static Bound fromShape(AbstractShape shape) {
        BoundBuilder<?, ?> builder = Bound.builder()
                .parent(shape)
                .x(shape.getX())
                .y(shape.getY())
                .width(shape.getWidth())
                .height(shape.getHeight())
                .filled(false)
                .stroke(Color.LIGHTBLUE);
        if (shape instanceof Rectangle) {
            builder.isSquare(((Rectangle) shape).isSquare());
        }
        Bound bound = builder.build();
        bound.init();
        return bound;
    }

    /**
     * TODO: to be implemented
     */
    private static class Dot /*extends Circle*/ {

    }
}
