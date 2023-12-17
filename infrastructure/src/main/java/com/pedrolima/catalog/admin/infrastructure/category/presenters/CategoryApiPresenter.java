package com.pedrolima.catalog.admin.infrastructure.category.presenters;

import com.pedrolima.catalog.admin.application.category.retrieve.get.CategoryOutput;
import com.pedrolima.catalog.admin.application.category.retrieve.list.CategoryListOutput;
import com.pedrolima.catalog.admin.infrastructure.category.models.CategoryListResponse;
import com.pedrolima.catalog.admin.infrastructure.category.models.CategoryResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
