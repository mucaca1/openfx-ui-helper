package org.bh.uifxhelpercore;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Simple helper witch apply some features on form fx.
 */
public class FormsFxHelper {

    /**
     * Function find all labels in form and set for each label specific size.
     * @param pane Form panel
     * @param labelSize Size which will be applied on label component.
     */
    public static void searchAndSetControlsLabelWidth(Pane pane, double labelSize) {
        if (pane instanceof GridPane) {
            if (pane.getStyleClass().stream().anyMatch(s -> s.contains("simple-"))) {
                GridPane gp = (GridPane) pane;
                if (gp.getColumnConstraints().size() == 12) {
                    double rest = 100 - labelSize;
                    for (int i = 0; i < gp.getColumnConstraints().size(); i++) {
                        if (i < 3) {
                            gp.getColumnConstraints().get(i).setPercentWidth(labelSize / 2);
                        } else {
                            gp.getColumnConstraints().get(i).setPercentWidth(rest / 10);
                        }
                    }
                }
            }
        }

        for (Node child : pane.getChildren()) {
            if (child instanceof Pane) {
                Pane cpane = (Pane) child;
                searchAndSetControlsLabelWidth(cpane, labelSize);
            }

            else if (child instanceof TitledPane) {
                TitledPane tpane = (TitledPane) child;
                if (tpane.getContent() instanceof Pane) {
                    Pane cpane = (Pane) tpane.getContent();
                    searchAndSetControlsLabelWidth(cpane, labelSize);
                }
            }
        }
    }
}
