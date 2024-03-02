package org.bh.uifxhelpercore.pagination;

import javafx.scene.control.Pagination;
import javafx.scene.control.Skin;
import org.bh.uifxhelpercore.pagination.skin.FirstLastIndexPaginationSkin;

public class AdvancedPagination extends Pagination {

    public AdvancedPagination(int pageCount, int pageIndex) {
        super(pageCount, pageIndex);
    }

    public AdvancedPagination(int pageCount) {
        super(pageCount);
    }

    public AdvancedPagination() {
        super();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new FirstLastIndexPaginationSkin(this);
    }

}
