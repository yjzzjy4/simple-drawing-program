package io.aaron.learning.manage;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.observer.AbstractSubject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Clipboard {
    public static class ClipboardSubject extends AbstractSubject {}

    public static final List<AbstractBoundDecorator> COPIES = new CopyOnWriteArrayList<>();
    public static final ClipboardSubject SUBJECT = new ClipboardSubject();

    public static List<AbstractBoundDecorator> getCopies() {
        return COPIES;
    }

    public static ClipboardSubject getSubject() {
        return SUBJECT;
    }

    public static void copySelected(List<AbstractBoundDecorator> bounds) {
        COPIES.clear();
        COPIES.addAll(bounds.stream()
                            .map(AbstractBoundDecorator::clone)
                            .collect(Collectors.toList()));
        COPIES.forEach(c -> c.move(10d, 10d));
        SUBJECT.notifyObservers();
    }

    public static void cutSelected(List<AbstractBoundDecorator> bounds) {
        COPIES.clear();
        COPIES.addAll(bounds.stream()
                            .map(AbstractBoundDecorator::clone)
                            .collect(Collectors.toList()));
        SUBJECT.notifyObservers();
    }
}
