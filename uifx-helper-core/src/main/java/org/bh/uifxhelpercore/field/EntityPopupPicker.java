package org.bh.uifxhelpercore.field;

import java.util.List;

public interface EntityPopupPicker<T, I> {

    boolean isCloseFlag();

    boolean isOkFlag();

    void initData();

    List<I> getSelectedDataObjects();
}
