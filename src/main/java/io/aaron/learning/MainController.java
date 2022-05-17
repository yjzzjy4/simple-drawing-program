package io.aaron.learning;

import com.jfoenix.controls.JFXButton;
import io.aaron.learning.geom.base.AbstractShape;
import io.aaron.learning.geom.base.ShapeType;
import io.aaron.learning.geom.decorator.ShapeImageBoundsDecorator;
import io.aaron.learning.geom.decorator.base.AbstractShapeDecorator;
import io.aaron.learning.manage.FactoryProvider;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.ShapePreviewHolder;
import io.aaron.learning.manage.ShapePrototypeHolder;
import io.aaron.learning.manage.factory.base.AbstractShapeDecoratorFactory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

import java.util.Arrays;
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

    public static final AbstractShapeDecoratorFactory shapeFactory = FactoryProvider.provideAbstractShapeFactory();

    public void initialize() {
        menubar.getMenus().addAll(new Menu("File"), new Menu("Edit"), new Menu("View"), new Menu("Arrange"));

        shortcuts.setPrefHeight(30);

        // left panel.
        shapePickerScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        shapePickerScroll.setMinViewportWidth(100);
        shapePicker.prefWidthProperty().bind(shapePickerScroll.prefViewportWidthProperty());
        // for all shapes.
        Arrays.asList(ShapeType.values()).forEach(type -> {
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
            // button event.
            btn.setOnAction(value -> {
                AbstractShapeDecorator boundedShape = shapeFactory.getBoundsShapeDecorator(type);
                Double offsetX = boundedShape.getWidth() / 2;
                Double offsetY = boundedShape.getHeight() / 2;
                Double centerX = canvasScroll.widthProperty().divide(2).doubleValue();
                Double centerY = canvasScroll.heightProperty().divide(2).doubleValue();
                Node node = boundedShape.getContainer();
                node.setLayoutX(centerX - offsetX);
                node.setLayoutY(centerY - offsetY);
                ShapeHolder.add(boundedShape);
                boundedShape.applyStyle(ShapePreviewHolder.defaultStyle);
                this.canvas.getChildren().add(node);
            });
            shapePicker.getChildren().add(btn);
        });

        // canvas area.
        canvas.prefWidthProperty().bind(canvasScroll.widthProperty());
        canvas.prefHeightProperty().bind(canvasScroll.heightProperty());
        canvasScroll.setMinViewportWidth(600);

        // right panel.
        propEditorScroll.prefViewportWidthProperty().bind(root.widthProperty().multiply(0.15));
        propEditorScroll.setMinViewportWidth(100);
        propEditor.prefWidthProperty().bind(propEditorScroll.prefViewportWidthProperty());

        // delete shapes.
        root.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.DELETE)) {
                List<AbstractShapeDecorator> selected = ShapeHolder.getAllShapes()
                                                          .stream()
                                                          .filter(AbstractShape::getSelected)
                                                          .collect(Collectors.toList());
                canvas.getChildren().removeAll(selected.stream().map(AbstractShapeDecorator::getContainer).collect(Collectors.toList()));
                ShapeHolder.getAllShapes().removeAll(selected);
            }
        });

        // unselect all shapes.
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