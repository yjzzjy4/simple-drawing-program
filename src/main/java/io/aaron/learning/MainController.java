package io.aaron.learning;

import com.jfoenix.controls.JFXButton;
import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.AbstractShapeGroup;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.manage.*;
import io.aaron.learning.manage.command.base.AbstractCommand;
import io.aaron.learning.manage.factory.SimpleCommandFactory;
import io.aaron.learning.manage.factory.SimpleResizeStrategyFactory;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;
import io.aaron.learning.manage.strategy.resize.base.ResizeStrategy;
import io.aaron.learning.manage.strategy.resize.base.StrategyType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MainController {

    private Stage stage;
    
    private final ShapeHolder shapeHolder = new ShapeHolder();

    @FXML
    private BorderPane root;

    @FXML
    private MenuBar menubar;
    @FXML
    private MenuBarController menubarController;

    @FXML
    private HBox shortcuts;
    @FXML
    private ShortcutsController shortcutsController;

    @FXML
    private ScrollPane shapePickerScroll;

    @FXML
    private FlowPane shapePicker;

    @FXML
    private ScrollPane propEditorScroll;

    @FXML
    private FlowPane propEditor;
    @FXML
    private PropertyEditorController propEditorController;

    @FXML
    private ScrollPane canvasScroll;

    @FXML
    private Pane mainContainer;

    public static ResizeStrategy horizontalResizeStrategy;
    public static ResizeStrategy verticalResizeStrategy;

    public static final AbstractShapeFactory shapeFactory = FactoryProvider.provideShapeImageFactory();

    /**
     * Recalibrate the vertical index of the shapes and bounds held by ShapeHolder,
     * need to be sure that these lists are in sync with what's in the mainContainer,
     * otherwise unexpected behavior would be performed.
     */
    private void recalibrateVerticalIndex() {
        List<AbstractShape> shapes = shapeHolder.getShapes();
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        for(int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setVerticalIndex(i);
        }
        for(int i = 0; i < bounds.size(); i++) {
            bounds.get(i).setVerticalIndex(i + shapes.size());
        }
    }

    public void selectAllShapes() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        List<AbstractShape> shapes = shapeHolder.getShapes();
        // unselect all shapes;
        unSelectAllShapes();
        // shapes inside a group should be excluded;
        List<AbstractShape> exclusives = new ArrayList<>();
        // select all shapes;
        for(int i = shapes.size() - 1; i >= 0; i--) {
            AbstractShape s = shapes.get(i);
            if(exclusives.contains(s)) {
                continue;
            }
            AbstractBoundDecorator boundedShape = shapeFactory.getBoundDecorator(s);
            mainContainer.getChildren().add(boundedShape.getContainer());
            bounds.add(boundedShape);
            if(s instanceof AbstractShapeGroup) {
                exclusives.addAll(((AbstractShapeGroup) s).getChildren());
            }
        }
        shapeHolder.notifyObservers();
    }

    /**
     * Remove all bounds decorators to shapes,
     * equivalent to unselect all shapes.
     */
    public void unSelectAllShapes() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        mainContainer.getChildren()
                     .removeAll(bounds.stream().map(AbstractShapeDecorator::getContainer).collect(Collectors.toList()));
        bounds.clear();
        shapeHolder.notifyObservers();
    }

    /**
     * Delete selected shapes.
     *
     */
    public void deleteSelectedShapes() {
        recalibrateVerticalIndex();
        AbstractCommand cmd = SimpleCommandFactory.getDeleteSelectedCommand(mainContainer.getChildren(), shapeHolder.getBounds(), shapeHolder);
        cmd.execute();
        CommandHolder.pushToUndo(cmd);
        shapeHolder.notifyObservers();
    }

    /**
     * Copy selected shapes.
     *
     */
    public void copySelectedShapes() {
        Clipboard.copySelected(shapeHolder.getBounds());
    }

    /**
     * Cut selected shapes.
     *
     */
    public void cutSelectedShapes() {
        Clipboard.cutSelected(shapeHolder.getBounds());
        deleteSelectedShapes();
    }

    /**
     * Paste shapes.
     *
     */
    public void pasteShapes() {
        List<AbstractBoundDecorator> copies = Clipboard.getCopies();
        AbstractCommand cmd = SimpleCommandFactory.getDuplicateSelectedCommand(mainContainer.getChildren(), copies, shapeHolder, false);
        cmd.execute();
        CommandHolder.pushToUndo(cmd);
        copies.forEach(c -> c.move(10d, 10d));
    }

    /**
     * Duplicate selected shapes.
     *
     */
    public void duplicateSelectedShapes() {
        AbstractCommand cmd = SimpleCommandFactory.getDuplicateSelectedCommand(mainContainer.getChildren(), shapeHolder.getBounds(), shapeHolder, true);
        cmd.execute();
        CommandHolder.pushToUndo(cmd);
    }

    /**
     * Bring the selected shape forward (one shape only),
     * will do nothing when multiselect.
     *
     */
    public void bringSelectedShapeForward() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        List<AbstractShape> shapes = shapeHolder.getShapes();
        if(bounds.size() != 1) {
            return;
        }
        int index = shapes.indexOf(bounds.get(0).getShape());
        if(index != shapes.size() - 1) {
            AbstractShape next = shapes.get(index + 1).clone();
            mainContainer.getChildren().set(index, next.getCanvas());
            mainContainer.getChildren().set(index + 1, bounds.get(0).getShape().getCanvas());
            shapes.set(index, next);
            shapes.set(index + 1, bounds.get(0).getShape());
        }
    }

    /**
     * Send the selected shapes backward (one shape only),
     * will do nothing when multiselect.
     *
     */
    public void sendSelectedShapeBackward() {
        List<AbstractBoundDecorator> bounds = List.copyOf(shapeHolder.getBounds());
        List<AbstractShape> shapes = shapeHolder.getShapes();
        if(bounds.size() != 1) {
            return;
        }
        int index = shapes.indexOf(bounds.get(0).getShape());
        if(index != 0) {
            AbstractShape prev = shapes.get(index - 1).clone();
            mainContainer.getChildren().set(index, prev.getCanvas());
            mainContainer.getChildren().set(index - 1, bounds.get(0).getShape().getCanvas());
            shapes.set(index, prev);
            shapes.set(index - 1, bounds.get(0).getShape());
        }
    }

    /**
     * Bring the selected shapes to front.
     *
     */
    public void bringSelectedShapesToFront() {
        List<AbstractBoundDecorator> bounds = List.copyOf(shapeHolder.getBounds());
        deleteSelectedShapes();
        // clear side effects;
        CommandHolder.popFromUndo();
        mainContainer.getChildren().addAll(bounds.stream()
                                                 .map(b -> b.getShape().getCanvas())
                                                 .collect(Collectors.toList()));

        mainContainer.getChildren().addAll(bounds.stream()
                                             .map(AbstractBoundDecorator::getContainer)
                                             .collect(Collectors.toList()));

        shapeHolder.getShapes().addAll(bounds.stream()
                                             .map(AbstractBoundDecorator::getShape)
                                             .collect(Collectors.toList()));
        shapeHolder.getBounds().addAll(bounds);
    }

    /**
     * Send the selected shapes to back.
     *
     */
    public void sendSelectedShapesToBack() {
        List<AbstractBoundDecorator> bounds = List.copyOf(shapeHolder.getBounds());
        deleteSelectedShapes();
        // clear side effects;
        CommandHolder.popFromUndo();
        mainContainer.getChildren().addAll(0, bounds.stream()
                                                 .map(b -> b.getShape().getCanvas())
                                                 .collect(Collectors.toList()));

        mainContainer.getChildren().addAll(bounds.stream()
                                                 .map(AbstractBoundDecorator::getContainer)
                                                 .collect(Collectors.toList()));

        shapeHolder.getShapes().addAll(0, bounds.stream()
                                             .map(AbstractBoundDecorator::getShape)
                                             .collect(Collectors.toList()));
        shapeHolder.getBounds().addAll(bounds);
    }

    /**
     * Group selected shapes, shape count must be greater than 1;
     *
     */
    public void groupSelectedShapes() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        if(bounds.size() <= 1) {
            return;
        }
        AbstractCommand cmd = SimpleCommandFactory.getGroupSelectedCommand(mainContainer.getChildren(), bounds, shapeHolder, shapeFactory);
        cmd.execute();
        CommandHolder.pushToUndo(cmd);
    }

    /**
     * Ungroup selected shapes, ignore when some shape is not a group;
     *
     */
    public void unGroupSelectedShapes() {
        List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();
        if(bounds.size() < 1) {
            return;
        }
        recalibrateVerticalIndex();
        AbstractCommand cmd = SimpleCommandFactory.getUngroupSelectedCommand(mainContainer.getChildren(), bounds, shapeHolder, shapeFactory);
        cmd.execute();
        CommandHolder.pushToUndo(cmd);
    }

    /**
     * Cancel shapes duplicate, it involves drag status change,
     * status change: DUPLICATE -> MOVE.
     */
    private void cancelDuplicates() {
        if(DragHelper.dragType == DragHelper.DragType.DUPLICATE) {
            List<AbstractBoundDecorator> targets = DragHelper.targets;
            List<AbstractBoundDecorator> duplicates = DragHelper.duplicates;

            // reposition target;
            for(int i = 0; i < targets.size(); i++) {
                AbstractBoundDecorator target = targets.get(i);
                AbstractBoundDecorator duplicate = duplicates.get(i);
                target.move(duplicate.getX() - target.getX(), duplicate.getY() - target.getY());
            }

            mainContainer.getChildren()
                         .removeAll(duplicates.stream()
                                              .map(AbstractShapeDecorator::getContainer)
                                              .collect(Collectors.toList()));

            mainContainer.getChildren()
                         .addAll(targets.stream()
                                        .map(AbstractBoundDecorator::getContainer)
                                        .collect(Collectors.toList()));
            DragHelper.dragType = DragHelper.DragType.MOVE;
        }
    }

    public void initialize() {
        shortcutsController.setShapeHolder(shapeHolder);
        shortcutsController.setParentController(this);
        menubarController.setShapeHolder(shapeHolder);
        menubarController.setParentController(this);
        propEditorController.setShapeHolder(shapeHolder);

        /* indicate whether a bound is new added,
         * this is helpful in the mouse press -> release -> click event chain;
         */
        AtomicBoolean newBound = new AtomicBoolean(false);

        // left panel;
        shapePickerScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        shapePickerScroll.setMinViewportWidth(100);
        shapePicker.prefWidthProperty().bind(shapePickerScroll.prefViewportWidthProperty());
        // for all shapes;
        Arrays.asList(ShapeType.values()).forEach(type -> {
            if(type == ShapeType.GROUP) {
                return;
            }
            AbstractShape shape = ShapePrototypeHolder.getShape(type);
            Canvas canvas = shape.getCanvas();
            double scaleFactor = canvas.getWidth() > canvas.getHeight() ? 45.0 / canvas.getWidth() :
                    45.0 / canvas.getHeight();
            shape.setWidth(scaleFactor * shape.getWidth());
            shape.setHeight(scaleFactor * shape.getHeight());
            Button btn = new JFXButton();
            btn.setMaxWidth(50.0);
            btn.setMaxHeight(50.0);
            btn.setPadding(new Insets(0.0));
            btn.setGraphic(shape.draw());
            // button event;
            btn.setOnAction(value -> {
                AbstractShape _shape = ShapePrototypeHolder.getShape(type);
                // position the shape;
                double offsetX = _shape.getWidth() / 2;
                double offsetY = _shape.getHeight() / 2;
                double centerX = canvasScroll.widthProperty().divide(2).doubleValue();
                double centerY = canvasScroll.heightProperty().divide(2).doubleValue();
                _shape.setX(centerX - offsetX);
                _shape.setY(centerY - offsetY);
                _shape.applyStyle(ShapePreviewHolder.DEFAULT_STYLE);
                unSelectAllShapes();
                AbstractBoundDecorator b = shapeFactory.getBoundDecorator(_shape);
                AbstractCommand cmd = SimpleCommandFactory.getAddShapeCommand(mainContainer.getChildren(), b, shapeHolder);
                cmd.execute();
                CommandHolder.pushToUndo(cmd);
                shapeHolder.notifyObservers();
            });
            shapePicker.getChildren().add(btn);
        });

        // main container area;
        mainContainer.prefWidthProperty().bind(canvasScroll.widthProperty());
        mainContainer.prefHeightProperty().bind(canvasScroll.heightProperty());
        canvasScroll.setMinViewportWidth(600);

        // right panel;
        propEditorScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        propEditorScroll.setMinViewportWidth(100);
        propEditor.prefWidthProperty().bind(propEditorScroll.prefViewportWidthProperty());

        // group or ungroup shapes;
        canvasScroll.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.isControlDown() && event.getCode().equals(KeyCode.G)) {
                if(event.isShiftDown()) {
                    unGroupSelectedShapes();
                    return;
                }
                groupSelectedShapes();
            }
            event.consume();
        });

        // select or unselect shape(s), init drag event;
        canvasScroll.setOnMousePressed(event -> {
            // init drag type;
            DragHelper.dragType = DragHelper.DragType.NONE;

            if(event.getButton() == MouseButton.PRIMARY) {
                List<AbstractShape> shapes = shapeHolder.getShapes();
                List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();

                // check the selected shapes (bounds) first;
                for(int i = bounds.size() - 1; i >= 0; i--) {
                    AbstractBoundDecorator b = bounds.get(i);
                    AbstractBoundDecorator.Handler handler = b.hitHandler(event.getX() - b.getX(),
                                                                          event.getY() - b.getY());
                    if(Objects.nonNull(handler)) {
                        // init drag event;
                        DragHelper.initStates(event.getX(), event.getY(), b, bounds, DragHelper.DragType.RESIZE);
                        // set target handler;
                        DragHelper.setResizeHandler(handler);
                        newBound.set(false);
                        event.consume();
                        return;
                    }
                    else if(b.getShape().contains(event.getX() - b.getX(), event.getY() - b.getY())) {
                        DragHelper.initStates(event.getX(), event.getY(), b, bounds, DragHelper.DragType.MOVE);
                        newBound.set(false);
                        event.consume();
                        return;
                    }
                }

                if(!event.isControlDown()) {
                    unSelectAllShapes();
                }

                for(int i = shapes.size() - 1; i >= 0; i--) {
                    AbstractShape shape = shapes.get(i);
                    shape.setSelected(false);
                    // hit the target;
                    if(shape.contains(event.getX() - shape.getX(), event.getY() - shape.getY())) {
                        newBound.set(true);
                        shape.setSelected(true);
                        if(event.isControlDown()) {
                            int index = -1;
                            for(int j = 0; j < bounds.size(); j++) {
                                int currentIndex = shapes.indexOf(bounds.get(j).getShape());
                                if(currentIndex > i) {
                                    index = j;
                                    break;
                                }
                            }
                            index = index == -1 ? bounds.size() : index;
                            AbstractBoundDecorator b = shapeFactory.getBoundDecorator(shape);
                            mainContainer.getChildren().add(shapes.size() + index, b.getContainer());
                            bounds.add(index, b);
                            DragHelper.initStates(event.getX(), event.getY(), b, bounds, DragHelper.DragType.MOVE);
                        }
                        else {
                            // add decorator to main container;
                            AbstractBoundDecorator b = shapeFactory.getBoundDecorator(shape);
                            mainContainer.getChildren().add(b.getContainer());
                            bounds.add(b);
                            DragHelper.initStates(event.getX(), event.getY(), b, DragHelper.DragType.MOVE);
                        }
                        shapeHolder.notifyObservers();
                        event.consume();
                        break;
                    }
                }
            }
            event.consume();
        });

        // some supplements for mouse press event;
        canvasScroll.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                if(event.isStillSincePress()) {
                    AbstractBoundDecorator target = null;
                    List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();

                    // check the selected shapes (bounds);
                    for(int i = bounds.size() - 1; i >= 0; i--) {
                        AbstractBoundDecorator b = bounds.get(i);
                        if(b.getShape().contains(event.getX() - b.getX(), event.getY() - b.getY())) {
                            target = b;
                            break;
                        }
                    }

                    if(Objects.isNull(target)) {
                        DragHelper.dragType = DragHelper.DragType.NONE;
                        event.consume();
                        return;
                    }

                    if(event.isControlDown()) {
                        if(!newBound.get()) {
                            mainContainer.getChildren().remove(target.getContainer());
                            bounds.remove(target);
                        }
                    }
                    else {
                        mainContainer.getChildren().removeAll(bounds.stream()
                                                                 .map(AbstractBoundDecorator::getContainer)
                                                                 .collect(Collectors.toList()));
                        bounds.clear();
                        mainContainer.getChildren().add(target.getContainer());
                        bounds.add(target);
                    }
                    DragHelper.dragType = DragHelper.DragType.NONE;
                    event.consume();
                }
            }
        });

        // drag selected shape(s);
        mainContainer.setOnMouseDragged(event -> {
            double x = event.getX(), y = event.getY();
            double offsetX = x - DragHelper.lastCX, offsetY = y - DragHelper.lastCY;
            if(DragHelper.dragType != DragHelper.DragType.RESIZE) {
                DragHelper.setLatest(x, y);
            }

            if(DragHelper.dragType == DragHelper.DragType.MOVE) {
                List<AbstractBoundDecorator> targets = DragHelper.targets;
                if(event.isControlDown()) {
                    DragHelper.dragType = DragHelper.DragType.DUPLICATE;

                    // duplicate shapes and move copies;
                    mainContainer.getChildren()
                                 .removeAll(DragHelper.targets.stream()
                                                              .map(AbstractShapeDecorator::getContainer)
                                                              .collect(Collectors.toList()));

                    /*
                     * Note: targets aren't removed from ShapeHolder.BOUNDS,
                     * will remove them after mouse release (drag event over),
                     * or re-add them to mainContainer by proper order
                     * when cancel duplicating (ctrl key released before drag event over).
                     */

                    List<AbstractBoundDecorator> duplicates = DragHelper.targets.stream()
                                                                                .map(AbstractBoundDecorator::clone)
                                                                                .collect(Collectors.toList());

                    mainContainer.getChildren()
                                 .addAll(duplicates.stream()
                                                   .map(AbstractBoundDecorator::getContainer)
                                                   .collect(Collectors.toList()));

                    /*
                     * Note: duplicates aren't added to ShapeHolder.BOUNDS,
                     * same reason as targets above.
                     */

                    targets = DragHelper.duplicates = duplicates;
                }
                // move targets;
                targets.forEach(o -> o.move(offsetX, offsetY));
            }
            else if(DragHelper.dragType == DragHelper.DragType.DUPLICATE) {
                if(!event.isControlDown()) {
                    cancelDuplicates();
                    event.consume();
                    return;
                }
                // move targets;
                DragHelper.duplicates.forEach(o -> o.move(offsetX, offsetY));
            }
            else if(DragHelper.dragType == DragHelper.DragType.RESIZE) {
                // pick strategies;
                horizontalResizeStrategy = SimpleResizeStrategyFactory.getHorizontalStrategy(DragHelper.resizeHandler,
                                                                                             StrategyType.FREE);
                verticalResizeStrategy = SimpleResizeStrategyFactory.getVerticalStrategy(DragHelper.resizeHandler,
                                                                                         StrategyType.FREE);

                if(event.isShiftDown()) {
                    // equal proportional resize strategy;
                    horizontalResizeStrategy = SimpleResizeStrategyFactory.getHorizontalStrategy(
                            DragHelper.resizeHandler, StrategyType.EQUAL_PROPORTION);
                    verticalResizeStrategy = SimpleResizeStrategyFactory.getVerticalStrategy(DragHelper.resizeHandler,
                                                                                             StrategyType.EQUAL_PROPORTION);
                }
                if(Objects.nonNull(horizontalResizeStrategy)) {
                    horizontalResizeStrategy.handleResize(event);
                }
                if(Objects.nonNull(verticalResizeStrategy)) {
                    verticalResizeStrategy.handleResize(event);
                }
                event.consume();
                return;
            }
            event.consume();
        });

        // drag is over;
        canvasScroll.setOnMouseReleased(event -> {
            if(event.getX() == DragHelper.originalCX && event.getY() == DragHelper.originalCY) {
                event.consume();
                return;
            }
            if(DragHelper.dragType == DragHelper.DragType.MOVE) {
                AbstractCommand cmd = SimpleCommandFactory.getMoveSelectedCommand(DragHelper.exactTarget, DragHelper.targets,
                                                              DragHelper.originalX, DragHelper.originalY,
                                                              DragHelper.exactTarget.getX(),
                                                              DragHelper.exactTarget.getY());
                cmd.execute();
                CommandHolder.pushToUndo(cmd);
            }
            else if(DragHelper.dragType == DragHelper.DragType.DUPLICATE) {
                List<AbstractShape> shapes = shapeHolder.getShapes();
                List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();

                DragHelper.duplicates.forEach(d -> {
                    AbstractShape shape = d.getShape();
                    if(shape instanceof AbstractShapeGroup) {
                        mainContainer.getChildren()
                                     .addAll(shapes.size(), ((AbstractShapeGroup) shape).getChildren()
                                                                                        .stream()
                                                                                        .map(AbstractShape::getCanvas)
                                                                                        .collect(Collectors.toList()));
                        shapes.addAll(((AbstractShapeGroup) shape).getChildren());
                    }
                    mainContainer.getChildren().add(shapes.size(), shape.getCanvas());
                    shapes.add(shape);
                });

                /*
                 * Note: this is where the targets are removed from and
                 * the duplicates are added to ShapeHolder.BOUNDS.
                 */
                bounds.removeAll(DragHelper.targets);
                bounds.addAll(DragHelper.duplicates);

                // todo: Add new duplicate drag command here;
            }
            else if(DragHelper.dragType == DragHelper.DragType.RESIZE) {
                AbstractCommand cmd = SimpleCommandFactory.getResizeSelectedCommand(DragHelper.exactTarget, DragHelper.targets,
                                                                DragHelper.originalX, DragHelper.originalY,
                                                                DragHelper.originalWidth, DragHelper.originalHeight,
                                                                DragHelper.exactTarget.getX(),
                                                                DragHelper.exactTarget.getY(),
                                                                DragHelper.exactTarget.getWidth(),
                                                                DragHelper.exactTarget.getHeight());
                cmd.execute();
                CommandHolder.pushToUndo(cmd);
            }
            DragHelper.dragType = DragHelper.DragType.NONE;
        });

        canvasScroll.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() == KeyCode.CONTROL) {
                cancelDuplicates();
            }
        });
    }

    public void setStage(@NonNull Stage stage) {
        this.stage = stage;
        Scene scene = this.stage.getScene();
        // select or unselect all shapes;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.isControlDown()) {
                // Ctrl + A;
                if(event.getCode() == KeyCode.A) {
                    // Ctrl + Shift + A;
                    if(event.isShiftDown()) {
                        unSelectAllShapes();
                        return;
                    }
                    selectAllShapes();
                }
                else if(event.getCode() == KeyCode.C) {
                    copySelectedShapes();
                }
                else if(event.getCode() == KeyCode.D) {
                    duplicateSelectedShapes();
                }
                else if(event.getCode() == KeyCode.V) {
                    pasteShapes();
                }
                else if(event.getCode() == KeyCode.X) {
                    cutSelectedShapes();
                }
                else if(event.getCode() == KeyCode.Z) {
                    if(event.isShiftDown()) {
                        CommandHolder.redo();
                        return;
                    }
                    CommandHolder.undo();
                }
            }
            // delete selected shapes;
            if(event.getCode() == KeyCode.DELETE) {
                deleteSelectedShapes();
            }
        });
    }
}