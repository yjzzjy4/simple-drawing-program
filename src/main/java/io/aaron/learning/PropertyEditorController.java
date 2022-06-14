package io.aaron.learning;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import io.aaron.learning.geom.base.ShapeStyleProperty;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.ShapePreviewHolder;
import io.aaron.learning.manage.observer.AbstractObserver;
import io.aaron.learning.manage.observer.AbstractSubject;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PropertyEditorController implements AbstractObserver {

    private ShapeHolder shapeHolder;

    private ShapeStyleProperty styleProperty = ShapePreviewHolder.DEFAULT_STYLE;

    private final List<Double[]> lineDashes = new ArrayList<>();

    @FXML
    public JFXTabPane propertyPane;

    @FXML
    public GridPane styleGridPane;

    @FXML
    public JFXRadioButton fillRadio;

    @FXML
    public JFXColorPicker fillColorPicker;

    @FXML
    public JFXRadioButton lineRadio;

    @FXML
    public JFXColorPicker lineColorPicker;

    @FXML
    public JFXTextField opacityField;

    @FXML
    public GridPane arrangeGridPane;

    @FXML
    public JFXTextField topField;

    @FXML
    public JFXTextField leftField;

    @FXML
    public JFXTextField widthField;

    @FXML
    public JFXTextField heightField;

    public void setShapeHolder(ShapeHolder shapeHolder) {
        this.shapeHolder = shapeHolder;
        shapeHolder.registerObserver(this);
    }

    private void init() {
        arrangeGridPane.setDisable(true);
        styleProperty = ShapePreviewHolder.DEFAULT_STYLE;
        fillRadio.setSelected(styleProperty.getFilled());
        lineRadio.setSelected(styleProperty.getLined());
        fillColorPicker.setValue((Color) styleProperty.getFillPaint());
        lineColorPicker.setValue((Color) styleProperty.getStrokePaint());
    }

    public void initialize() {
        init();

        fillRadio.setOnAction(event -> {
            fillRadio.setSelected(!styleProperty.getFilled());
            styleProperty.setFilled(!styleProperty.getFilled());
            if(!styleProperty.getFilled()) {
                fillColorPicker.setDisable(true);
            }
            else {
                fillColorPicker.setDisable(false);
                // TODO: change the fill color of selected shapes;
            }
        });

        lineRadio.setOnAction(event -> {
            lineRadio.setSelected(!styleProperty.getLined());
            styleProperty.setLined(!styleProperty.getLined());
            if(!styleProperty.getLined()) {
                lineColorPicker.setDisable(true);
            }
            else {
                lineColorPicker.setDisable(false);
                // TODO: change the line color of selected shapes;
            }
        });
    }

    @Override
    public void response(AbstractSubject subject) {
        if(subject instanceof ShapeHolder) {
            int selectedCount = shapeHolder.getBounds().size();
            List<AbstractBoundDecorator> bounds = shapeHolder.getBounds();

            if(selectedCount == 0) {
                init();
                return;
            }

            AbstractBoundDecorator top = bounds.get(selectedCount - 1);

            styleProperty = ShapeStyleProperty.extractFrom(top.getShape());

            arrangeGridPane.setDisable(false);
            fillRadio.setSelected(styleProperty.getFilled());
            lineRadio.setSelected(styleProperty.getLined());
            fillColorPicker.setValue((Color) styleProperty.getFillPaint());
            lineColorPicker.setValue((Color) styleProperty.getStrokePaint());

            if(selectedCount == 1) {
                topField.setDisable(false);
                leftField.setDisable(false);
            }
            else {
                topField.setDisable(true);
                leftField.setDisable(true);
            }
        }
    }
}
