package io.aaron.learning.manage.command;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.command.base.AbstractCommand;
import javafx.scene.Node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DuplicateSelectedCommand implements AbstractCommand {

    private final List<Node> container;
    private final List<AbstractBoundDecorator> bounds;
    private final ShapeHolder shapeHolder;
    private final Boolean directDuplicate;

    public DuplicateSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds,
                                    ShapeHolder shapeHolder, Boolean directDuplicate) {
        this.container = container;
        this.bounds = bounds.stream()
                            .map(AbstractBoundDecorator::clone)
                            .collect(Collectors.toList());
        this.shapeHolder = shapeHolder;
        this.directDuplicate = directDuplicate;
    }

    @Override
    public void execute() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        container.removeAll(bounds.stream()
                                  .map(AbstractBoundDecorator::getContainer)
                                  .collect(Collectors.toList()));
        bounds.clear();
        container.addAll(this.bounds.stream()
                                    .map(b -> {
                                        if(directDuplicate) {
                                            b.move(10d, 10d);
                                        }
                                        return b.getShape().getCanvas();
                                    })
                                    .collect(Collectors.toList()));

        container.addAll(this.bounds.stream()
                                    .map(AbstractBoundDecorator::getContainer)
                                    .collect(Collectors.toList()));

        shapeHolder.getShapes().addAll(this.bounds.stream()
                                                  .map(AbstractBoundDecorator::getShape)
                                                  .collect(Collectors.toList()));
        bounds.addAll(this.bounds);
    }

    @Override
    public void unExecute() {
        container.removeAll(bounds.stream()
                                  .flatMap(b -> Stream.of(b.getShape().getCanvas(), b.getContainer()))
                                  .collect(Collectors.toList()));

        shapeHolder.getShapes().removeAll(bounds.stream()
                                                  .map(AbstractBoundDecorator::getShape)
                                                  .collect(Collectors.toList()));
        shapeHolder.getBounds().removeAll(bounds);
    }
}
