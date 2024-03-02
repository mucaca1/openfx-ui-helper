package org.bh.uifxhelpercore.editor;


import java.util.List;

public interface DataLoader<T> {

    List<T> getData();
    Long getDataSize();
}
