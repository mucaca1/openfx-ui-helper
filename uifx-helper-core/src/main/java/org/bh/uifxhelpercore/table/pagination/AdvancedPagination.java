package org.bh.uifxhelpercore.table.pagination;

import javafx.scene.control.Pagination;
import javafx.scene.control.Skin;
import org.bh.uifxhelpercore.table.pagination.skin.FirstLastIndexPaginationSkin;

/**
 * This pagination component add two next option for pagination.
 * Select first page and select last page
 */
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
