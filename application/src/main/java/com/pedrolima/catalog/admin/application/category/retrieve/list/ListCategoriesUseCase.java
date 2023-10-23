package com.pedrolima.catalog.admin.application.category.retrieve.list;

import com.pedrolima.catalog.admin.application.UseCase;
import com.pedrolima.catalog.admin.domain.category.CategorySearchQuery;
import com.pedrolima.catalog.admin.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
