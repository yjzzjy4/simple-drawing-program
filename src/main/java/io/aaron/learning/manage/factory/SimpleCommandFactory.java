package io.aaron.learning.manage.factory;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.command.*;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;
import javafx.scene.Node;

import java.util.List;

public class SimpleCommandFactory {
    public static AddShapeCommand getAddShapeCommand(List<Node> container, AbstractBoundDecorator bound, ShapeHolder shapeHolder) {
        return new AddShapeCommand(container, bound, shapeHolder);
    }

    public static DeleteSelectedCommand getDeleteSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds, ShapeHolder shapeHolder) {
        return new DeleteSelectedCommand(container, bounds, shapeHolder);
    }

    public static DuplicateSelectedCommand getDuplicateSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds,
                                                                       ShapeHolder shapeHolder, Boolean directDuplicate) {
        return new DuplicateSelectedCommand(container, bounds, shapeHolder, directDuplicate);
    }

    public static MoveSelectedCommand getMoveSelectedCommand(AbstractBoundDecorator exactTarget, List<AbstractBoundDecorator> targets, double originalX, double originalY,
                                                      double finalX, double finalY) {
        return new MoveSelectedCommand(exactTarget, targets, originalX, originalY, finalX, finalY);
    }

    public static ResizeSelectedCommand getResizeSelectedCommand(AbstractBoundDecorator exactTarget, List<AbstractBoundDecorator> targets,
                                                          double originalX, double originalY, double originalWidth, double originalHeight,
                                                          double finalX, double finalY, double finalWidth, double finalHeight) {
        return new ResizeSelectedCommand(exactTarget, targets, originalX, originalY, originalWidth, originalHeight, finalX, finalY, finalWidth, finalHeight);
    }

    public static GroupSelectedCommand getGroupSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds, ShapeHolder shapeHolder, AbstractShapeFactory shapeFactory) {
        return new GroupSelectedCommand(container, bounds, shapeHolder, shapeFactory);
    }

    public static UngroupSelectedCommand getUngroupSelectedCommand(List<Node> container, List<AbstractBoundDecorator> bounds, ShapeHolder shapeHolder, AbstractShapeFactory shapeFactory) {
        return new UngroupSelectedCommand(container, bounds, shapeHolder, shapeFactory);
    }
}
