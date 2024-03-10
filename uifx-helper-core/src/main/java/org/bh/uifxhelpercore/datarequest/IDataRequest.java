package org.bh.uifxhelpercore.datarequest;

import java.util.List;

public interface IDataRequest<T> {

    List<T> getData(DataRequestPojo request);

    Long getCount(DataRequestPojo request);

}
