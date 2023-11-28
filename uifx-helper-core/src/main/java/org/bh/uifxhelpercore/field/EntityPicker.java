package org.bh.uifxhelpercore.field;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.bh.uifxhelpercore.field.skin.EntityPickerSkin;


public class EntityPicker<V> extends Control {

    protected Skin<?> createDefaultSkin() {
        return new EntityPickerSkin(this);
    }
}
