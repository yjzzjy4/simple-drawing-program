package io.aaron.learning.manage.command;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.command.base.AbstractCommand;
import javafx.scene.Node;

import java.util.List;

public class AddShapeCommand implements AbstractCommand {

    private final List<Node> container;
    private AbstractBoundDecorator bound;
    private final ShapeHolder shapeHolder;

    public AddShapeCommand(List<Node> container, AbstractBoundDecorator bound, ShapeHolder shapeHolder) {
        this.container = container;
        this.bound = bound;
        this.shapeHolder = shapeHolder;
    }

    @Override
    public void execute() {
        container.addAll(List.of(bound.getShape().getCanvas(), bound.getContainer()));
        shapeHolder.getShapes().add(bound.getShape());
        shapeHolder.getBounds().add(bound);
    }

    @Override
    public void unExecute() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        for(AbstractBoundDecorator b : bounds) {
            if(bound.getShape().equals(b.getShape())) {
                bound = b;
                break;
            }
        }
        container.removeAll(List.of(bound.getShape().getCanvas(), bound.getContainer()));
        shapeHolder.getShapes().remove(bound.getShape());
        shapeHolder.getBounds().remove(bound);
    }
}
