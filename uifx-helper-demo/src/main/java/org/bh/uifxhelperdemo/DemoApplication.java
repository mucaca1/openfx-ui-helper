package org.bh.uifxhelperdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DemoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("demo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Demo");
        stage.setScene(scene);
        stage.show();

        ((DemoController) fxmlLoader.getController()).init();
    }

    public static void main(String[] args) {
        launch();
    }
}