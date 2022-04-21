package io.aaron.learning;

import io.aaron.learning.geom.impl.Rectangle;
import io.aaron.learning.scene.CanvasWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
        menubar.getMenus().addAll(new Menu("File"), new Menu("Edit"), new Menu("View"), new Menu("Arrange"));

        shortcuts.setPrefHeight(30);

        // 左侧边栏;
        shapePickerScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        shapePickerScroll.setMinViewportWidth(100);
        shapePicker.prefWidthProperty().bind(shapePickerScroll.prefViewportWidthProperty());
        Button button = new Button();
        button.setGraphic(new FontIcon("di-java:32"));
        button.setOnAction(value -> {
            // wrong approach...
            Rectangle rectangle = new Rectangle();
            Double offsetX = rectangle.getWidth() / 2;
            Double offsetY = rectangle.getHeight() / 2;
            Double centerX = canvasScroll.widthProperty().divide(2).doubleValue();
            Double centerY = canvasScroll.heightProperty().divide(2).doubleValue();
            rectangle.paint(canvas, centerX - offsetX, centerY - offsetY);
            rectangle.setX(0.0);
            rectangle.setY(0.0);
            // Should get it done this way...
            javafx.scene.shape.Rectangle rec = new javafx.scene.shape.Rectangle(120, 60);
            rec.setStroke(Color.web("#000"));
            rec.setFill(Color.web("#fff"));
            propEditor.getChildren().addAll(rec);
        });
        shapePicker.getChildren().add(button);

        // 画板区域;
        canvasScroll.setMinViewportWidth(600);

        // 右侧边栏;
        propEditorScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        propEditorScroll.setMinViewportWidth(100);
        propEditor.prefWidthProperty().bind(propEditorScroll.prefViewportWidthProperty());
    }
}