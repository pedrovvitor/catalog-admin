package com.pedrolima.catalog.admin.application.category.delete;

import com.pedrolima.catalog.admin.domain.category.CategoryGateway;
import com.pedrolima.catalog.admin.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(String anIn) {
        this.categoryGateway.deleteById(CategoryID.from(anIn));
    }
}
