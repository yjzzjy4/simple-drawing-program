package io.aaron.learning;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.ShapeHolder;
import io.aaron.learning.manage.ShapePreviewHolder;
import io.aaron.learning.manage.observer.AbstractObserver;
import io.aaron.learning.manage.observer.AbstractSubject;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;

public class PropertyEditorController implements AbstractObserver {

    private ShapeHolder shapeHolder;

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
        fillRadio.setSelected(ShapePreviewHolder.DEFAULT_STYLE.getFilled());
        lineRadio.setSelected(ShapePreviewHolder.DEFAULT_STYLE.getLined());
        fillColorPicker.setValue((Color) ShapePreviewHolder.DEFAULT_STYLE.getFillPaint());
        lineColorPicker.setValue((Color) ShapePreviewHolder.DEFAULT_STYLE.getStrokePaint());
    }

    public void initialize() {
        init();
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

            arrangeGridPane.setDisable(false);
            fillRadio.setSelected(top.getShape().isFilled());
            lineRadio.setSelected(top.getShape().isLined());
            fillColorPicker.setValue((Color) top.getShape().getFillPaint());
            lineColorPicker.setValue((Color) top.getShape().getStrokePaint());

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
