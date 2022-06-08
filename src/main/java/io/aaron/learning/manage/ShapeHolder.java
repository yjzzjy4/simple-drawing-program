package io.aaron.learning.manage;

import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.observer.AbstractSubject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShapeHolder extends AbstractSubject {
    private final List<AbstractShape> shapes = new CopyOnWriteArrayList<>();
    private final List<AbstractBoundDecorator> bounds = new CopyOnWriteArrayList<>();

    public List<AbstractShape> getShapes() {
        return shapes;
    }

    public List<AbstractBoundDecorator> getBounds() {
        return bounds;
    }
}
