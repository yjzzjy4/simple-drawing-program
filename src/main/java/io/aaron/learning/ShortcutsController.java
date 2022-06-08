package io.aaron.learning;

import com.jfoenix.controls.JFXButton;
import io.aaron.learning.manage.CommandHolder;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.observer.AbstractObserver;
import io.aaron.learning.manage.observer.AbstractSubject;
import javafx.fxml.FXML;

public class ShortcutsController implements AbstractObserver {

    private ShapeHolder shapeHolder;

    private MainController parentController;

    @FXML
    public JFXButton saveButton;

    @FXML
    public JFXButton undoButton;

    @FXML
    public JFXButton redoButton;

    @FXML
    public JFXButton deleteButton;

    @FXML
    public JFXButton toFrontButton;

    @FXML
    public JFXButton toBackButton;

    @FXML
    public JFXButton backgroundColorButton;

    @FXML
    public JFXButton strokeColorButton;

    @FXML
    public JFXButton groupButton;

    @FXML
    public JFXButton ungroupButton;

    @Override
    public void response(AbstractSubject subject) {
        if(subject instanceof ShapeHolder) {
            int selectedCount = shapeHolder.getBounds().size();

            if(selectedCount == 0) {
                deleteButton.setDisable(true);
                toFrontButton.setDisable(true);
                toBackButton.setDisable(true);
                backgroundColorButton.setDisable(true);
                strokeColorButton.setDisable(true);
                groupButton.setDisable(true);
                ungroupButton.setDisable(true);
            }
            else {
                deleteButton.setDisable(false);
                toFrontButton.setDisable(false);
                toBackButton.setDisable(false);
                backgroundColorButton.setDisable(false);
                strokeColorButton.setDisable(false);
                groupButton.setDisable(selectedCount <= 1);
                ungroupButton.setDisable(false);
            }
        }
        else if(subject instanceof CommandHolder.CommandSubject) {
            undoButton.setDisable(CommandHolder.UNDO_STACK.empty());
            redoButton.setDisable(CommandHolder.REDO_STACK.empty());
        }
    }

    public void setShapeHolder(ShapeHolder shapeHolder) {
        this.shapeHolder = shapeHolder;
        shapeHolder.registerObserver(this);
    }

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public void initialize() {
        CommandHolder.getSubject().registerObserver(this);

        undoButton.setOnAction(event -> CommandHolder.undo());

        redoButton.setOnAction(event -> CommandHolder.redo());

        deleteButton.setOnAction(event -> parentController.deleteSelectedShapes());

        toFrontButton.setOnAction(event -> parentController.bringSelectedShapesToFront());

        toBackButton.setOnAction(event -> parentController.sendSelectedShapesToBack());

        groupButton.setOnAction(event -> parentController.groupSelectedShapes());

        ungroupButton.setOnAction(event -> parentController.unGroupSelectedShapes());
    }
}
