package io.aaron.learning.manage.command;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.command.base.AbstractCommand;

import java.util.List;

public class MoveSelectedCommand implements AbstractCommand {

    AbstractBoundDecorator exactTarget;
    List<AbstractBoundDecorator> targets;
    double originalX, originalY;
    double finalX, finalY;

    public MoveSelectedCommand(AbstractBoundDecorator exactTarget, List<AbstractBoundDecorator> targets, double originalX, double originalY,
                               double finalX, double finalY) {
        this.exactTarget = exactTarget;
        this.targets = List.copyOf(targets);
        this.originalX = originalX;
        this.originalY = originalY;
        this.finalX = finalX;
        this.finalY = finalY;
    }

    @Override
    public void execute() {
        if(exactTarget.getX() == originalX && exactTarget.getY() == originalY) {
            targets.forEach(o -> o.move(finalX - originalX, finalY - originalY));
        }
    }

    @Override
    public void unExecute() {
        if(exactTarget.getX() == finalX && exactTarget.getY() == finalY) {
            targets.forEach(o -> o.move(originalX - finalX, originalY - finalY));
        }
    }
}
