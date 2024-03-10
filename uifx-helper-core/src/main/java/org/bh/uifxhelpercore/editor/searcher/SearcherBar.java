package org.bh.uifxhelpercore.editor.searcher;

import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.button.ButtonAdvancedBar;
import org.bh.uifxhelpercore.button.ButtonType;

public class SearcherBar extends BorderPane {

    private SimpleTextSearcher simpleTextSearcher = new SimpleTextSearcher();
    private ButtonAdvancedBar buttonAdvancedBar;

    public SearcherBar(boolean autoSearch) {
        BorderPane wrapper = new BorderPane();

        wrapper.setCenter(simpleTextSearcher);

        if (!autoSearch) {
            buttonAdvancedBar = new ButtonAdvancedBar();
            buttonAdvancedBar.addButtons(ButtonType.SEARCH);
            wrapper.setRight(buttonAdvancedBar);
        }

        setCenter(wrapper);
    }

    public SimpleTextSearcher getSimpleTextSearcher() {
        return simpleTextSearcher;
    }

    public ButtonAdvancedBar getButtonAdvancedBar() {
        return buttonAdvancedBar;
    }
}
