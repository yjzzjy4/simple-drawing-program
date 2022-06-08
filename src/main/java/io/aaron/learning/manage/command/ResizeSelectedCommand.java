package io.aaron.learning.manage.command;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.command.base.AbstractCommand;

import java.util.List;

public class ResizeSelectedCommand implements AbstractCommand {

    AbstractBoundDecorator exactTarget;
    List<AbstractBoundDecorator> targets;
    double originalX, originalY;
    double originalWidth, originalHeight;
    double finalX, finalY;
    double finalWidth, finalHeight;

    public ResizeSelectedCommand(AbstractBoundDecorator exactTarget, List<AbstractBoundDecorator> targets,
                                 double originalX, double originalY, double originalWidth, double originalHeight,
                                 double finalX, double finalY, double finalWidth, double finalHeight) {
        this.exactTarget = exactTarget;
        this.targets = List.copyOf(targets);
        this.originalX = originalX;
        this.originalY = originalY;
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.finalX = finalX;
        this.finalY = finalY;
        this.finalWidth = finalWidth;
        this.finalHeight = finalHeight;
    }

    @Override
    public void execute() {
        double x = exactTarget.getX(), y = exactTarget.getY(), width = exactTarget.getWidth(), height = exactTarget.getHeight();
        double offsetX = finalX - originalX, offsetY = finalY - originalY;
        if(x == originalX && y == originalY && width == originalWidth && height == originalHeight) {
            double widthFactor = finalWidth / width, heightFactor = finalHeight / height;
            targets.forEach(o -> {
                o.move(offsetX * o.getWidth() / width, offsetY * o.getHeight() / height);
                o.resize(o.getWidth() * widthFactor, o.getHeight() * heightFactor);
            });
        }
    }

    @Override
    public void unExecute() {
        double x = exactTarget.getX(), y = exactTarget.getY(), width = exactTarget.getWidth(), height = exactTarget.getHeight();
        double offsetX = originalX - finalX, offsetY = originalY - finalY;
        if(x == finalX && y == finalY && width == finalWidth && height == finalHeight) {
            double widthFactor = originalWidth / width, heightFactor = originalHeight / height;
            targets.forEach(o -> {
                o.move(offsetX * o.getWidth() / width, offsetY * o.getHeight() / height);
                o.resize(o.getWidth() * widthFactor, o.getHeight() * heightFactor);
            });
        }
    }
}
