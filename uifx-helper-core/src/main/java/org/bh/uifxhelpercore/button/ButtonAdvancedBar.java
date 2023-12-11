package org.bh.uifxhelpercore.button;

import com.dlsc.formsfx.model.util.ResourceBundleService;
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
    private Map<String, Button> buttons;

    private ResourceBundleService resourceBundleService;


    public ButtonAdvancedBar() {
        buttons = new HashMap<>();
    }

    public void setResourceBundleService(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;

        this.resourceBundleService.addListener(this::translateAllButtons);
        translateAllButtons();
    }

    private void translateAllButtons() {
        for (Map.Entry<String, Button> btn : buttons.entrySet()) {
            String btnLabel = btn.getKey();
            if (resourceBundleService != null) {
                String s = resourceBundleService.translate(btn.getKey());
                if (s != null) {
                    btnLabel = s;
                }
            }
            btn.getValue().setText(btnLabel);
        }
    }

    public void addButtons(ButtonType... buttonTypes) {
        for (ButtonType buttonType : buttonTypes) {
            addButtons(buttonType.getIdentifier());
        }
    }

    public void addButtons(String... buttonIdentifiers) {
        for (String identifier : buttonIdentifiers) {
            String btnLabel = identifier;
            if (resourceBundleService != null) {
                String s = resourceBundleService.translate(identifier);
                if (s != null) {
                    btnLabel = s;
                }
            }
            Button btn = new Button(btnLabel);
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
