package org.bh.uifxhelpercore.button;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import org.bh.uifxhelpercore.locale.LocalizationHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * This class help hold data about all buttons in button bar.
 * Defines resource bundle for button labels and help update those labels after language change.
 */
public class ButtonAdvancedBar extends ButtonBar {

    /**
     * Holder for all buttons. Key is custom user defined identifier and value is button.
     */
    private Map<String, Button> buttons;


    /**
     * Localization resource bundle.
     */
    private ResourceBundleService resourceBundleService;

    public ButtonAdvancedBar() {
        this(null);
    }

    public ButtonAdvancedBar(ResourceBundleService resourceBundleService) {
        buttons = new HashMap<>();

        this.resourceBundleService = resourceBundleService;
        if (resourceBundleService == null) {
            this.resourceBundleService = LocalizationHelper.get().getDefaultButtonBundleService();
        }

        if (this.resourceBundleService != null) {
            this.resourceBundleService.addListener(this::translateAllButtons);
        }
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

    /**
     * Add button into button bar.
     */
    public void addButtons(IButtonType... buttonTypes) {
        for (IButtonType buttonType : buttonTypes) {
            addButtons(buttonType.getIdentifier());
        }
    }

    private void addButtons(String... buttonIdentifiers) {
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

    /**
     * Return button by identifier.
     * @return button
     */
    public Button getButton(IButtonType buttonType) {
        return buttons.get(buttonType.getIdentifier());
    }

    /**
     * Register event handler for button.
     * @param buttonType button type
     * @param eventHandler handler
     */
    public void addActionListener(IButtonType buttonType, EventHandler eventHandler) {
        addActionListener(buttonType.getIdentifier(), eventHandler);
    }

    private void addActionListener(String buttonId, EventHandler eventHandler) {
        buttons.get(buttonId).addEventHandler(ActionEvent.ACTION, eventHandler);
    }
}
