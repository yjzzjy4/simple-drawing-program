package io.aaron.learning.manage;

import io.aaron.learning.manage.command.base.AbstractCommand;
import io.aaron.learning.manage.observer.AbstractSubject;

import java.util.Objects;
import java.util.Stack;

public class CommandHolder {

    public static class CommandSubject extends AbstractSubject {}

    public static final Stack<AbstractCommand> UNDO_STACK = new Stack<>();
    public static final Stack<AbstractCommand> REDO_STACK = new Stack<>();
    public static final CommandSubject SUBJECT = new CommandSubject();

    public static CommandSubject getSubject() {
        return SUBJECT;
    }

    public static void pushToUndo(AbstractCommand cmd) {
        UNDO_STACK.push(cmd);
        SUBJECT.notifyObservers();
    }

    public static AbstractCommand popFromUndo() {
        if(!UNDO_STACK.empty()) {
            SUBJECT.notifyObservers();
            return UNDO_STACK.pop();
        }
        return null;
    }

    public static void pushToRedo(AbstractCommand cmd) {
        REDO_STACK.push(cmd);
        SUBJECT.notifyObservers();
    }

    public static AbstractCommand popFromRedo() {
        if(!REDO_STACK.empty()) {
            SUBJECT.notifyObservers();
            return REDO_STACK.pop();
        }
        return null;
    }

    public static void undo() {
        AbstractCommand cmd = CommandHolder.popFromUndo();
        if(Objects.nonNull(cmd)) {
            cmd.unExecute();
            CommandHolder.pushToRedo(cmd);
        }
    }

    public static void redo() {
        AbstractCommand cmd = CommandHolder.popFromRedo();
        if(Objects.nonNull(cmd)) {
            cmd.execute();
            CommandHolder.pushToUndo(cmd);
        }
    }
}
