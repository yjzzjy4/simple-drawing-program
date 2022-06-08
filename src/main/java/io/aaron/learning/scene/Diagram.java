package io.aaron.learning.scene;

import io.aaron.learning.geom.base.AbstractShape;

import java.util.List;

public class Diagram {

    private String name;
    private List<AbstractShape> shapes;

    public Diagram(String name, List<AbstractShape> shapes) {
        this.name = name;
        this.shapes = shapes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractShape> getShapes() {
        return shapes;
    }

    public void setShapes(List<AbstractShape> shapes) {
        this.shapes = shapes;
    }
}
