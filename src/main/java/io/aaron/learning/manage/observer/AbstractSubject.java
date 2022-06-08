package io.aaron.learning.manage.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubject {
    List<AbstractObserver> observers = new ArrayList<>();

    public void registerObserver(AbstractObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AbstractObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(o -> o.response(this));
    }
}
