package org.bh.uifxhelpercore.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public class ButtonHelper {

    /**
     * Holder for all buttons. Key is custom user defined identifier and value is button.
     */
    Map<String, Button> buttons;



    public ButtonHelper() {
        buttons = new HashMap<>();
    }

    public void addButtons(ButtonType... buttonTypes) {
        for (ButtonType buttonType : buttonTypes) {
            addButtons(buttonType.getIdentifier());
        }
    }

    public void addButtons(String... buttonIdentifiers) {
        for (String identifier : buttonIdentifiers) {
            buttons.put(identifier, new Button());
        }
    }

    public void addActionListener(ButtonType buttonType, EventHandler eventHandler) {
        buttons.get(buttonType.getIdentifier()).addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addActionListener(String buttonId, EventHandler eventHandler) {
        buttons.get(buttonId).addEventHandler(ActionEvent.ACTION, eventHandler);
    }
}
