package io.aaron.learning.manage.command;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.command.base.AbstractCommand;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;
import javafx.scene.Node;

import java.util.List;
import java.util.stream.Collectors;

public class GroupSelectedCommand implements AbstractCommand {

    private final List<Node> container;
    private final List<AbstractBoundDecorator> bounds;
    private final ShapeHolder shapeHolder;
    private final AbstractShapeFactory shapeFactory;
    private AbstractBoundDecorator boundedGroup;

    public GroupSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds, ShapeHolder shapeHolder, AbstractShapeFactory shapeFactory) {
        this.container = container;
        this.bounds = List.copyOf(bounds);
        this.shapeHolder = shapeHolder;
        this.shapeFactory = shapeFactory;
    }

    @Override
    public void execute() {
        AbstractShapeGroup group = shapeFactory.getShapeGroup(bounds.stream()
                                                                    .map(AbstractShapeDecorator::getShape)
                                                                    .collect(Collectors.toList()));

        // unselect all shapes;
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        container.removeAll(bounds.stream().map(AbstractShapeDecorator::getContainer).collect(Collectors.toList()));
        bounds.clear();

        // select the group;
        boundedGroup = shapeFactory.getBoundDecorator(group);
        List<AbstractShape> shapes = shapeHolder.getShapes();
        container.addAll(List.of(group.getCanvas(), boundedGroup.getContainer()));
        shapes.add(group);
        bounds.add(boundedGroup);
    }

    @Override
    public void unExecute() {
        // unselect all shapes && remove group;
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        List<AbstractShape> shapes = shapeHolder.getShapes();
        container.removeAll(bounds.stream().map(AbstractShapeDecorator::getContainer).collect(Collectors.toList()));
        container.remove(boundedGroup.getShape().getCanvas());
        shapes.remove(boundedGroup.getShape());
        bounds.clear();

        // put bounds back;
        container.addAll(this.bounds.stream()
                                    .map(AbstractBoundDecorator::getContainer)
                                    .collect(Collectors.toList()));

        bounds.addAll(this.bounds);
    }
}
