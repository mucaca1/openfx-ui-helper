package org.bh.uifxhelperdemo;

import javafx.collections.ObservableList;
import org.bh.uifxhelpercore.editor.entityselector.SelectorDialog;
import org.bh.uifxhelpercore.table.ViewType;

public class PersonSelectorDialog extends SelectorDialog<Person> {

    PersonSelectorDialog(ObservableList<Person> content) {
        table.initialize(Person.class, ViewType.Chooser);
        table.setItems(content);
    }
}
