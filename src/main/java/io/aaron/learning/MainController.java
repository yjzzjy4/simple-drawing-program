package io.aaron.learning;

import com.jfoenix.controls.JFXButton;
import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.shape.OvalImage;
import io.aaron.learning.geom.shape.RectangleImage;
import io.aaron.learning.manage.FactoryProvider;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.ShapePrototypeHolder;
import io.aaron.learning.manage.factory.base.AbstractShapeFactory;
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

    public static final AbstractShapeFactory shapeFactory = FactoryProvider.provideAbstractShapeFactory();

    public void initialize() {
        menubar.getMenus().addAll(new Menu("File"), new Menu("Edit"), new Menu("View"), new Menu("Arrange"));

        shortcuts.setPrefHeight(30);

        // 左侧边栏;
        shapePickerScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        shapePickerScroll.setMinViewportWidth(100);
        shapePicker.prefWidthProperty().bind(shapePickerScroll.prefViewportWidthProperty());
        ShapePrototypeHolder.PROTOTYPES.values().forEach(o -> {
            Canvas canvas = o.getCanvas();
            double scaleFactor = canvas.getWidth() > canvas.getHeight() ? 45.0 / canvas.getWidth() :
                    45.0 / canvas.getHeight();
            o.setWidth(scaleFactor * o.getWidth());
            o.setHeight(scaleFactor * o.getHeight());
            o.draw();
            Button btn = new JFXButton();
            btn.setMaxWidth(50.0);
            btn.setMaxHeight(50.0);
            btn.setGraphic(o.draw());
            btn.setOnAction(value -> {
                AbstractShape shape;
                if(o instanceof RectangleImage && ((RectangleImage) o).getSquare()) {
                    shape = shapeFactory.getBoundsShape(((RectangleImage) o).getSquareDefault(true));
                }
                else if(o instanceof OvalImage && ((OvalImage) o).getCircle()) {
                    shape = shapeFactory.getBoundsShape(((OvalImage) o).getCircleDefault(true));
                }
                else {
                    shape = shapeFactory.getBoundsShape(o.getDefault());
                }
                Double offsetX = shape.getWidth() / 2;
                Double offsetY = shape.getHeight() / 2;
                Double centerX = canvasScroll.widthProperty().divide(2).doubleValue();
                Double centerY = canvasScroll.heightProperty().divide(2).doubleValue();
                Node node = shape.getContainer();
                node.setLayoutX(centerX - offsetX);
                node.setLayoutY(centerY - offsetY);
                ShapeHolder.add(shape);
                this.canvas.getChildren().add(node);
                // todo: apply properties to the default shape
            });
            shapePicker.getChildren().add(btn);
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
        canvasScroll.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                ShapeHolder.getAllShapes().forEach(o -> {
                    o.setSelected(false);
                    ((ShapeImageBoundsDecorator) o).hide();
                });
            }
            event.consume();
        });
    }
}