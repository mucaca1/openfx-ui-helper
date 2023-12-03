package org.bh.uifxhelpercore.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

import java.util.HashMap;
import java.util.Map;

public class ButtonAdvancedBar extends ButtonBar {

    /**
     * Holder for all buttons. Key is custom user defined identifier and value is button.
     */
    Map<String, Button> buttons;


    public ButtonAdvancedBar() {
        buttons = new HashMap<>();
    }

    public void addButtons(ButtonType... buttonTypes) {
        for (ButtonType buttonType : buttonTypes) {
            addButtons(buttonType.getIdentifier());
        }
    }

    public void addButtons(String... buttonIdentifiers) {
        for (String identifier : buttonIdentifiers) {
            Button btn = new Button();
            buttons.put(identifier, btn);
            getButtons().add(btn);
        }
    }

    public Button getButton(String buttonIdentifier) {
        return buttons.get(buttonIdentifier);
    }

    public void addActionListener(ButtonType buttonType, EventHandler eventHandler) {
        addActionListener(buttonType.getIdentifier(), eventHandler);
    }

    public void addActionListener(String buttonId, EventHandler eventHandler) {
        buttons.get(buttonId).addEventHandler(ActionEvent.ACTION, eventHandler);
    }
}
