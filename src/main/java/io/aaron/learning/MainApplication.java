package io.aaron.learning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // fxml;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simple Drawing Practice");
        stage.setScene(scene);
        stage.setMaximized(true);
        ((MainController) fxmlLoader.getController()).setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}