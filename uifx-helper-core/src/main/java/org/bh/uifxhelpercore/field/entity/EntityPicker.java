package org.bh.uifxhelpercore.field.entity;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.bh.uifxhelpercore.field.entity.skin.EntityPickerSkin;


public class EntityPicker<V> extends Control {

    protected Skin<?> createDefaultSkin() {
        return new EntityPickerSkin(this);
    }
}
