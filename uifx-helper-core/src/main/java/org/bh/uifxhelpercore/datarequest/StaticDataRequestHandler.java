package org.bh.uifxhelpercore.datarequest;

import java.util.List;

public class StaticDataRequestHandler<T> implements IDataRequest<T> {

    private final List<T> data;

    StaticDataRequestHandler(List<T> data) {
        this.data = data;
    }

    @Override
    public List<T> getData(DataRequestPojo request) {
        return data;
    }

    @Override
    public Long getCount(DataRequestPojo request) {
        return (long) data.size();
    }
}
