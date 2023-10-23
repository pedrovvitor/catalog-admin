package com.pedrolima.catalog.admin.application.category.retrieve.list;

import com.pedrolima.catalog.admin.domain.category.CategoryGateway;
import com.pedrolima.catalog.admin.domain.category.CategorySearchQuery;
import com.pedrolima.catalog.admin.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
