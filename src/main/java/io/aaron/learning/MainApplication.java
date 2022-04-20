package io.aaron.learning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        VBox vBox = new VBox();
        MenuBar menuBar = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("View"), new Menu("Arrange"));
        FlowPane flowPane = new FlowPane();
        ScrollPane scroll = new ScrollPane(flowPane);
        scroll.setMinViewportWidth(100);
        flowPane.prefWidthProperty().bind(scroll.widthProperty());
        flowPane.setStyle("-fx-background-color: green");
        List<Button> list = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 100; i++) {
            Button btn = new Button();
            btn.setGraphic(new FontIcon("di-java:32"));
//            btn.setPrefSize(50, 50);
            list.add(btn);
        }
        flowPane.getChildren().addAll(list);
        Canvas canvas = new Canvas(10000, 1000);
        ScrollPane scrollPane = new ScrollPane(canvas);
        HBox hBox = new HBox(scroll, scrollPane, new FlowPane());
        scrollPane.prefWidthProperty().bind(hBox.widthProperty());
        hBox.setStyle("-fx-background-color: #000");
        hBox.prefHeightProperty().bind(vBox.heightProperty());
        vBox.setStyle("-fx-background-color: green");
        vBox.getChildren().addAll(menuBar, hBox);
        // fxml;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Simple Drawing Practice");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}