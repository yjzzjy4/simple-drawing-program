package io.aaron.learning.scene;

import io.aaron.learning.geom.AbstractShape;
import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CanvasWrapper extends Canvas {
    private List<AbstractShape> shapes;
}
