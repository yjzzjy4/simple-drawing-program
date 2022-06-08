package io.aaron.learning;

import io.aaron.learning.manage.Clipboard;
import io.aaron.learning.manage.CommandHolder;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.observer.AbstractObserver;
import io.aaron.learning.manage.observer.AbstractSubject;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBarController implements AbstractObserver {

    private ShapeHolder shapeHolder;

    private MainController parentController;

    @FXML
    public Menu fileMenu;

    @FXML
    public MenuItem newMenuItem;

    @FXML
    public MenuItem openMenuItem;

    @FXML
    public MenuItem saveMenuItem;

    @FXML
    public Menu editMenu;

    @FXML
    public MenuItem undoMenuItem;

    @FXML
    public MenuItem redoMenuItem;

    @FXML
    public MenuItem cutMenuItem;

    @FXML
    public MenuItem copyMenuItem;

    @FXML
    public MenuItem pasteMenuItem;

    @FXML
    public MenuItem deleteMenuItem;

    @FXML
    public MenuItem duplicateMenuItem;

    @FXML
    public MenuItem selectAllMenuItem;

    @FXML
    public MenuItem selectNoneMenuItem;

    @FXML
    public Menu arrangeMenu;

    @FXML
    public MenuItem toFrontMenuItem;

    @FXML
    public MenuItem toBackMenuItem;

    @FXML
    public MenuItem toForwardMenuItem;

    @FXML
    public MenuItem toBackwardMenuItem;

    @FXML
    public MenuItem groupMenuItem;

    @FXML
    public MenuItem ungroupMenuItem;

    public void setShapeHolder(ShapeHolder shapeHolder) {
        this.shapeHolder = shapeHolder;
        shapeHolder.registerObserver(this);
    }

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public void initialize() {
        CommandHolder.getSubject().registerObserver(this);
        Clipboard.getSubject().registerObserver(this);

        // edit menu;
        undoMenuItem.setOnAction(event -> CommandHolder.undo());

        redoMenuItem.setOnAction(event -> CommandHolder.redo());

        cutMenuItem.setOnAction(event -> parentController.cutSelectedShapes());

        copyMenuItem.setOnAction(event -> parentController.copySelectedShapes());

        pasteMenuItem.setOnAction(event -> parentController.pasteShapes());

        deleteMenuItem.setOnAction(event -> parentController.deleteSelectedShapes());

        duplicateMenuItem.setOnAction(event -> parentController.duplicateSelectedShapes());

        selectAllMenuItem.setOnAction(event -> parentController.selectAllShapes());

        selectNoneMenuItem.setOnAction(event -> parentController.unSelectAllShapes());

        // arrange menu;
        toFrontMenuItem.setOnAction(event -> parentController.bringSelectedShapesToFront());

        toBackMenuItem.setOnAction(event -> parentController.sendSelectedShapesToBack());

        toForwardMenuItem.setOnAction(event -> parentController.bringSelectedShapeForward());

        toBackwardMenuItem.setOnAction(event -> parentController.sendSelectedShapeBackward());

        groupMenuItem.setOnAction(event -> parentController.groupSelectedShapes());

        ungroupMenuItem.setOnAction(event -> parentController.unGroupSelectedShapes());
    }

    @Override
    public void response(AbstractSubject subject) {
        if(subject instanceof ShapeHolder) {
            int selectedCount = shapeHolder.getBounds().size();

            if(selectedCount == 0) {
                cutMenuItem.setDisable(true);
                copyMenuItem.setDisable(true);
                deleteMenuItem.setDisable(true);
                duplicateMenuItem.setDisable(true);
                selectNoneMenuItem.setDisable(true);
                toFrontMenuItem.setDisable(true);
                toForwardMenuItem.setDisable(true);
                toBackMenuItem.setDisable(true);
                toBackwardMenuItem.setDisable(true);
                groupMenuItem.setDisable(true);
                ungroupMenuItem.setDisable(true);
            }
            else {
                cutMenuItem.setDisable(false);
                copyMenuItem.setDisable(false);
                deleteMenuItem.setDisable(false);
                duplicateMenuItem.setDisable(false);
                selectNoneMenuItem.setDisable(false);
                toFrontMenuItem.setDisable(false);
                toForwardMenuItem.setDisable(selectedCount != 1);
                toBackMenuItem.setDisable(false);
                toBackwardMenuItem.setDisable(selectedCount != 1);
                groupMenuItem.setDisable(selectedCount <= 1);
                ungroupMenuItem.setDisable(false);
            }
        }
        else if(subject instanceof CommandHolder.CommandSubject) {
            undoMenuItem.setDisable(CommandHolder.UNDO_STACK.empty());
            redoMenuItem.setDisable(CommandHolder.REDO_STACK.empty());
        }
        else if(subject instanceof Clipboard.ClipboardSubject) {
            pasteMenuItem.setDisable(Clipboard.getCopies().isEmpty());
        }
    }
}
