package org.bh.uifxhelpercore.button;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private final ObjectProperty<ButtonCallback> buttonCallback
            = new SimpleObjectProperty<>(this, "buttonCallback");


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

    public final ObjectProperty<ButtonCallback> buttonCallbackProperty() {
        return buttonCallback;
    }

    public final void setButtonCallbackProperty(ButtonCallback value) {
        this.buttonCallbackProperty().set(value);
    }

    /**
     * Add button into button bar.
     */
    public void addButtons(IButtonType... buttonTypes) {
        for (IButtonType buttonType : buttonTypes) {
            Button btn = addButton(buttonType.getIdentifier());
            btn.addEventHandler(ActionEvent.ACTION, ae -> {
                if (ae.isConsumed()) return;
                if (buttonCallback.get() != null) {
                    buttonCallback.get().call(buttonType);
                }
            });
        }
    }

    private Button addButton(String buttonIdentifier) {
        String btnLabel = buttonIdentifier;
        if (resourceBundleService != null) {
            String s = resourceBundleService.translate(buttonIdentifier);
            if (s != null) {
                btnLabel = s;
            }
        }
        Button btn = new Button(btnLabel);
        buttons.put(buttonIdentifier, btn);
        getButtons().add(btn);
        return btn;
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
