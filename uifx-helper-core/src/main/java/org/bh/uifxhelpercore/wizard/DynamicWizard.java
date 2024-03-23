package org.bh.uifxhelpercore.wizard;

import javafx.stage.Stage;
import org.bh.uifxhelpercore.button.ButtonAdvancedBar;

import java.util.List;

public class DynamicWizard extends Stage {

    private ButtonAdvancedBar buttonBar;

    private List<WizardStage> stages;

    private int actualStageIndex;

    public DynamicWizard() {
        actualStageIndex = 0;
    }


}
