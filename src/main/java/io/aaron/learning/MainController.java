package io.aaron.learning;

import io.aaron.learning.geom.AbstractShape;
import io.aaron.learning.geom.impl.OvalImage;
import io.aaron.learning.geom.impl.RectangleImage;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.ShapePrototypeHolder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    private BorderPane root;

    @FXML
    private MenuBar menubar;

    @FXML
    private HBox shortcuts;

    @FXML
    private ScrollPane shapePickerScroll;

    @FXML
    private FlowPane shapePicker;

    @FXML
    private ScrollPane propEditorScroll;

    @FXML
    private FlowPane propEditor;

    @FXML
    private ScrollPane canvasScroll;

    @FXML
    private Pane canvas;

    //    @FXML
    //    public ShapeHolder canvas;

    public void initialize() {
        menubar.getMenus().addAll(new Menu("File"), new Menu("Edit"), new Menu("View"), new Menu("Arrange"));

        shortcuts.setPrefHeight(30);

        // 左侧边栏;
        shapePickerScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        shapePickerScroll.setMinViewportWidth(100);
        shapePicker.prefWidthProperty().bind(shapePickerScroll.prefViewportWidthProperty());
        Button drawRect = new Button();
        drawRect.setGraphic(new FontIcon("di-java:32"));
        drawRect.setOnAction(value -> {
            RectangleImage rectangle = new RectangleImage();
            Double offsetX = rectangle.getWidth() / 2;
            Double offsetY = rectangle.getHeight() / 2;
            Double centerX = canvasScroll.widthProperty().divide(2).doubleValue();
            Double centerY = canvasScroll.heightProperty().divide(2).doubleValue();
            Node node = rectangle.getContainer();
            node.setLayoutX(centerX - offsetX);
            node.setLayoutY(centerY - offsetY);
            ShapeHolder.add(rectangle);
            canvas.getChildren().add(node);

        });
        Button drawCircle = new Button();
        drawCircle.setGraphic(new FontIcon("di-java:32"));
        drawCircle.setOnAction(value -> {
            OvalImage circle = new OvalImage();
            Double offsetX = circle.getWidth() / 2;
            Double offsetY = circle.getHeight() / 2;
            Double centerX = canvasScroll.widthProperty().divide(2).doubleValue();
            Double centerY = canvasScroll.heightProperty().divide(2).doubleValue();
            Node node = circle.getContainer();
            node.setLayoutX(centerX - offsetX);
            node.setLayoutY(centerY - offsetY);
            ShapeHolder.add(circle);
            canvas.getChildren().add(node);
        });
        shapePicker.getChildren().addAll(drawRect, drawCircle);
        ShapePrototypeHolder.PROTOTYPES.values().forEach(o -> {
            Canvas canvas = o.getCanvas();
            double scaleFactor = canvas.getWidth() > canvas.getHeight() ? 40.0 / canvas.getWidth() :
                    40.0 / canvas.getHeight();
            canvas.scaleXProperty().set(scaleFactor);
            canvas.scaleYProperty().set(scaleFactor);
        });
        shapePicker.getChildren()
                   .addAll(ShapePrototypeHolder.PROTOTYPES.values()
                                                          .stream()
                                                          .map(AbstractShape::getContainer)
                                                          .collect(Collectors.toList()));

        // 画板区域;
        canvas.prefWidthProperty().bind(canvasScroll.widthProperty());
        canvas.prefHeightProperty().bind(canvasScroll.heightProperty());
        canvasScroll.setMinViewportWidth(600);

        // 右侧边栏;
        propEditorScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        propEditorScroll.setMinViewportWidth(100);
        propEditor.prefWidthProperty().bind(propEditorScroll.prefViewportWidthProperty());

        // 删除图形;
        root.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.DELETE)) {
                List<AbstractShape> selected = ShapeHolder.getAllShapes()
                                                          .stream()
                                                          .filter(AbstractShape::getSelected)
                                                          .collect(Collectors.toList());
                canvas.getChildren().removeAll(selected.stream().map(AbstractShape::getContainer).collect(Collectors.toList()));
                ShapeHolder.getAllShapes().removeAll(selected);
            }
        });

        // 取消所有选中;
//        canvasScroll.setOnMouseClicked(event -> {
//            if(event.getButton() == MouseButton.PRIMARY) {
//                ShapeHolder.getAllShapes().forEach(o -> {
//                    o.setSelected(false);
//                    o.getBounds().hide();
//                });
//            }
//            event.consume();
//        });

    }
}