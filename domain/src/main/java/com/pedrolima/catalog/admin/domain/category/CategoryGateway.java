package com.pedrolima.catalog.admin.domain.category;

import com.pedrolima.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create (Category aCategory);

    void deleteById(CategoryID anId);

    Optional<Category> findById(CategoryID anId);

    Category update(Category aCategory);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
