package org.bh.uifxhelpercore.editor.searcher;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

/**
 * Simple component for text searching.
 */
public class SimpleTextSearcher extends TextField {

    public SimpleTextSearcher() {
        setPromptText("Type for filtering...");
    }

    public StringProperty getSearchTextProperty() {
        return textProperty();
    }

}
