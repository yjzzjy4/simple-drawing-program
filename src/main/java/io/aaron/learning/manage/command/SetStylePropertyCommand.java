package io.aaron.learning.manage.command;

import io.aaron.learning.geom.base.ShapeStyleProperty;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.command.base.AbstractCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetStylePropertyCommand implements AbstractCommand {

    private final List<AbstractBoundDecorator> bounds;
    private final ShapeStyleProperty property;
    private final Map<AbstractBoundDecorator, ShapeStyleProperty> propertyMap = new HashMap<>();

    public SetStylePropertyCommand(List<AbstractBoundDecorator> bounds, ShapeStyleProperty property) {
        this.bounds = List.copyOf(bounds);
        this.property = property;
        bounds.forEach(b -> propertyMap.put(b, ShapeStyleProperty.extractFrom(b.getShape())));
    }

    @Override
    public void execute() {
        bounds.forEach(b -> b.getShape().applyStyle(property));
    }

    @Override
    public void unExecute() {
        bounds.forEach(b -> b.getShape().applyStyle(propertyMap.get(b)));
    }
}
