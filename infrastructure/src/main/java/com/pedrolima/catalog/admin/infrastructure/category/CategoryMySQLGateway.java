package com.pedrolima.catalog.admin.infrastructure.category;

import com.pedrolima.catalog.admin.domain.category.Category;
import com.pedrolima.catalog.admin.domain.category.CategoryGateway;
import com.pedrolima.catalog.admin.domain.category.CategoryID;
import com.pedrolima.catalog.admin.domain.category.CategorySearchQuery;
import com.pedrolima.catalog.admin.domain.pagination.Pagination;
import com.pedrolima.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return null;
    }

    @Override
    public void deleteById(final CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(final CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Category update(final Category aCategory) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery aQuery) {
        return null;
    }
}
