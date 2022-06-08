package io.aaron.learning.geom.base;

import java.util.List;

public abstract class AbstractShapeGroup extends AbstractShape {

    private List<AbstractShape> children;

    @Override
    public void move(double offsetX, double offsetY) {
        setX(getX() + offsetX);
        setY(getY() + offsetY);
        children.forEach(c -> c.move(offsetX, offsetY));
    }

    @Override
    public void resize(double width, double height) {
        for(AbstractShape c : getChildren()) {
            c.resize(width * c.getWidth() / getWidth() , height * c.getHeight() / getHeight());
        }
        super.resize(width, height);
    }

    @Override
    public abstract AbstractShapeGroup clone();

    public List<AbstractShape> getChildren() {
        return children;
    }

    public void setChildren(List<AbstractShape> children) {
        this.children = children;
    }
}
