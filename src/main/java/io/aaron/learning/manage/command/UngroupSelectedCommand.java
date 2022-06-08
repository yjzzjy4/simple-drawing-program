package io.aaron.learning.manage.command;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.command.base.AbstractCommand;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UngroupSelectedCommand implements AbstractCommand {

    private final List<Node> container;
    private final List<AbstractBoundDecorator> bounds;
    private final ShapeHolder shapeHolder;
    private final AbstractShapeFactory shapeFactory;

    public UngroupSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds, ShapeHolder shapeHolder,
                                  AbstractShapeFactory shapeFactory) {
        this.container = container;
        this.bounds = bounds.stream()
                            .filter(b -> b.getShape() instanceof AbstractShapeGroup)
                            .collect(Collectors.toList());
        this.shapeHolder = shapeHolder;
        this.shapeFactory = shapeFactory;
    }

    @Override
    public void execute() {
        // unselect all shapes;
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        List<AbstractShape> shapes = shapeHolder.getShapes();
        container.removeAll(bounds.stream().map(AbstractShapeDecorator::getContainer).collect(Collectors.toList()));
        container.removeAll(this.bounds.stream()
                                       .map(b -> b.getShape().getCanvas())
                                       .collect(Collectors.toList()));

        shapes.removeAll(this.bounds.stream()
                                    .map(AbstractShapeDecorator::getShape)
                                    .collect(Collectors.toList()));
        bounds.clear();

        // ungroup all groups;
        List<AbstractBoundDecorator> results = new ArrayList<>();
        for(AbstractBoundDecorator b : this.bounds) {
            for(AbstractShape s : ((AbstractShapeGroup) b.getShape()).getChildren()) {
                AbstractBoundDecorator bound = shapeFactory.getBoundDecorator(s);
                bound.setVerticalIndex(shapes.size() + s.getVerticalIndex());
                results.add(bound);
            }
        }
        System.out.println(results.size());
        results.sort(Comparator.comparingInt(AbstractShape::getVerticalIndex));
        container.addAll(results.stream()
                                .map(AbstractBoundDecorator::getContainer)
                                .collect(Collectors.toList()));
        bounds.addAll(results);
    }

    @Override
    public void unExecute() {
        // unselect all shapes;
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        container.removeAll(bounds.stream().map(AbstractShapeDecorator::getContainer).collect(Collectors.toList()));
        bounds.clear();

        List<AbstractShape> shapes = shapeHolder.getShapes();
        List<AbstractShape> groups = this.bounds.stream()
                                                .map(AbstractBoundDecorator::getShape)
                                                .collect(Collectors.toList());

        container.addAll(groups.stream()
                               .map(AbstractShape::getCanvas)
                               .collect(Collectors.toList()));

        container.addAll(this.bounds.stream()
                                    .map(AbstractBoundDecorator::getContainer)
                                    .collect(Collectors.toList()));
        shapes.addAll(groups);
        bounds.addAll(this.bounds);
    }
}
