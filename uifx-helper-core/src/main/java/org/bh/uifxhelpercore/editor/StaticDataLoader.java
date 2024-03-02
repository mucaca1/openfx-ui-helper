package org.bh.uifxhelpercore.editor;

import java.util.ArrayList;
import java.util.List;

public class StaticDataLoader<T> implements DataLoader<T>{

    private List<T> data = new ArrayList<>();

    StaticDataLoader(List<T> data) {
        this.data = data;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public Long getDataSize() {
        return (long) data.size();
    }
}
