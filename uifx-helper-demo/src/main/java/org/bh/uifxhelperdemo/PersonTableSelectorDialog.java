package org.bh.uifxhelperdemo;

import javafx.collections.ObservableList;
import org.bh.uifxhelpercore.editor.entityselector.TableSelectorDialog;
import org.bh.uifxhelpercore.table.ViewType;

public class PersonTableSelectorDialog extends TableSelectorDialog<Person> {

    PersonTableSelectorDialog(ObservableList<Person> content) {
        super(Person.class, ViewType.Chooser);
        dataBrowser.setData(content);
    }
}
