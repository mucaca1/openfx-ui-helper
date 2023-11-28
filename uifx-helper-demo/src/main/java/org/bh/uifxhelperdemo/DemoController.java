package org.bh.uifxhelperdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DemoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}