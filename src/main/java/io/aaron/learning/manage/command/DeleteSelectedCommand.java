package io.aaron.learning.manage.command;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.command.base.AbstractCommand;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteSelectedCommand implements AbstractCommand {

    private final List<Node> container;
    private final List<AbstractBoundDecorator> bounds;
    private final ShapeHolder shapeHolder;

    public DeleteSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds, ShapeHolder shapeHolder) {
        this.container = container;
        this.bounds = List.copyOf(bounds);
        this.shapeHolder = shapeHolder;
    }

    @Override
    public void execute() {
        for(AbstractBoundDecorator b : bounds) {
            if(b.getShape() instanceof AbstractShapeGroup) {
                container.removeAll(((AbstractShapeGroup) b.getShape()).getChildren()
                                                            .stream()
                                                            .map(AbstractShape::getCanvas)
                                                            .collect(Collectors.toList()));
                shapeHolder.getShapes().removeAll(((AbstractShapeGroup) b.getShape()).getChildren());
            }
            container.removeAll(Arrays.asList(b.getShape().getCanvas(), b.getContainer()));
            shapeHolder.getShapes().remove(b.getShape());
        }
        shapeHolder.getBounds().clear();
    }

    @Override
    public void unExecute() {
        Comparator<AbstractShape> cmp = Comparator.comparingInt(AbstractShape::getVerticalIndex);
        List<AbstractShape> shapes = shapeHolder.getShapes();
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        for(AbstractBoundDecorator b : this.bounds) {
            if(b.getShape() instanceof AbstractShapeGroup) {
                shapeHolder.getShapes().addAll(((AbstractShapeGroup) b.getShape()).getChildren());
            }
            shapeHolder.getShapes().add(b.getShape());
        }
        bounds.clear();
        bounds.addAll(this.bounds);
        shapes.sort(cmp);
        bounds.sort(cmp);
        container.clear();
        container.addAll(shapes.stream()
                               .map(AbstractShape::getCanvas)
                               .collect(Collectors.toList()));
        container.addAll(bounds.stream()
                               .map(AbstractBoundDecorator::getContainer)
                               .collect(Collectors.toList()));
    }
}
