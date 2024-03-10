package org.bh.uifxhelpercore.datarequest;

public class DataRequestPojo {

    private Integer index = null;
    private Integer paginationSize = null;
    private String textSearch = null;

    public Integer getPaginationSize() {
        return paginationSize;
    }

    public void setPaginationSize(Integer paginationSize) {
        this.paginationSize = paginationSize;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }
}
