package io.aaron.learning.manage;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;

import java.util.List;

/**
 * This class is for tracing the targets, initial state of the drag shape(s);
 *
 */
public class DragHelper {
    /**
     * target shape original width, height;
     */
    public static double originalWidth, originalHeight;

    /**
     * target shape original x, y;
     */
    public static double originalX, originalY;

    /**
     * cursor original coordinate (x, y);
     */
    public static double originalCX, originalCY;

    /**
     * cursor last coordinate (x, y);
     */
    public static double lastCX, lastCY;

    public static DragType dragType;

    public static AbstractBoundDecorator.Handler resizeHandler;
    public static AbstractBoundDecorator exactTarget;
    public static List<AbstractBoundDecorator> targets;
    public static List<AbstractBoundDecorator> duplicates;

    public enum DragType {
        NONE, MOVE, RESIZE, DUPLICATE
    }

    public static void init(
            double originalX,
            double originalY,
            AbstractBoundDecorator exactTarget,
            DragType dragType
    ) {
        DragHelper.lastCX = DragHelper.originalCX = originalX;
        DragHelper.lastCY = DragHelper.originalCY = originalY;
        DragHelper.originalX = exactTarget.getX();
        DragHelper.originalY = exactTarget.getY();
        DragHelper.originalWidth = exactTarget.getWidth();
        DragHelper.originalHeight = exactTarget.getHeight();
        DragHelper.exactTarget = exactTarget;
        DragHelper.dragType = dragType;
        DragHelper.resizeHandler = null;
    }

    public static void initStates(
            double originalX,
            double originalY,
            AbstractBoundDecorator exactTarget,
            DragType dragType
    ) {
        init(originalX, originalY, exactTarget, dragType);
        DragHelper.targets = List.of(exactTarget);
    }

    public static void initStates(
            double originalX,
            double originalY,
            AbstractBoundDecorator exactTarget,
            List<AbstractBoundDecorator> targets,
            DragType dragType
    ) {
        init(originalX, originalY, exactTarget, dragType);
        DragHelper.targets = targets;
    }

    public static void setResizeHandler(AbstractBoundDecorator.Handler handler) {
        DragHelper.resizeHandler = handler;
    }

    public static void setLatest(double lastX, double lastY) {
        DragHelper.lastCX = lastX;
        DragHelper.lastCY = lastY;
    }
}
