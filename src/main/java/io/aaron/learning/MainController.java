package io.aaron.learning;

import io.aaron.learning.geom.impl.Rectangle;
import io.aaron.learning.scene.CanvasWrapper;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

public class MainController {
    @FXML
    public BorderPane root;

    @FXML
    public MenuBar menubar;

    @FXML
    public HBox shortcuts;

    @FXML
    public ScrollPane shapePickerScroll;

    @FXML
    public FlowPane shapePicker;

    @FXML
    public ScrollPane propEditorScroll;

    @FXML
    public FlowPane propEditor;

    @FXML
    public ScrollPane canvasScroll;

    @FXML
    public CanvasWrapper canvas;

    public void initialize() {
        initMenu();

        // 左侧边栏;
        shapePickerScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        shapePickerScroll.setMinViewportWidth(100);
        shapePicker.prefWidthProperty().bind(shapePickerScroll.prefViewportWidthProperty());
        Button button = new Button("rectangle");
        button.setGraphic(new FontIcon("gmi-dashboard-customize:32"));
        button.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
                Rectangle rectangle = new Rectangle();
                propEditor.getChildren().add(rectangle.provide());
            }
        });
        shapePicker.getChildren().add(button);

        // 画板区域;
        canvasScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.65));
        canvasScroll.setMinViewportWidth(600);

        // 右侧边栏;
        propEditorScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.2));
        propEditorScroll.setMinViewportWidth(100);
        propEditor.prefWidthProperty().bind(canvasScroll.prefViewportWidthProperty());
        propEditor.setOrientation(Orientation.HORIZONTAL);
    }

    private void initMenu() {
        menubar.getMenus().addAll(new Menu("File"),
                new Menu("Edit") {{
                    getItems().add(new MenuItem("Clear") {{
                        setOnAction(event -> propEditor.getChildren().clear());
                    }});
                }},
                new Menu("View"),
                new Menu("Arrange"));

        shortcuts.setPrefHeight(30);
    }
}