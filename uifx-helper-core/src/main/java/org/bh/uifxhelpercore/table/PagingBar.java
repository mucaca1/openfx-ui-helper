package org.bh.uifxhelpercore.table;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PagingBar extends Pane {

    private int actualPageIndex;
    private int pageCount;
    TextField pageValue = new TextField();

    Button firstPageButton = new Button("|<-");
    Button prevButton = new Button("<-");
    Button nextButton = new Button("->");
    Button lastPageButton = new Button("->|");

    public PagingBar() {
        HBox horizontalBox = new HBox();
        horizontalBox.getChildren().addAll(firstPageButton, prevButton, pageValue, nextButton, lastPageButton);
        pageValue.setText("../..");
        pageValue.setEditable(false);
        getChildren().add(horizontalBox);

        actualPageIndex = 1;
        pageCount = 1;
    }

    private void nextPage() {
        if (actualPageIndex + 1 > pageCount) {
            return;
        }
        actualPageIndex++;
        pageValue.setText(actualPageIndex + "/" + pageCount);
    }

    private void prevPage() {
        if (actualPageIndex - 1 <= 0) {
            return;
        }
        actualPageIndex--;
        pageValue.setText(actualPageIndex + "/" + pageCount);
    }

    private void firstPage() {
        actualPageIndex = 1;
        pageValue.setText(actualPageIndex + "/" + pageCount);
    }

    private void lastPage() {
        actualPageIndex = pageCount;
        pageValue.setText(actualPageIndex + "/" + pageCount);
    }
}
