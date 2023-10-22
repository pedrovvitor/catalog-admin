package com.pedrolima.catalog.admin.application.category.update;

import com.pedrolima.catalog.admin.domain.category.Category;
import com.pedrolima.catalog.admin.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
