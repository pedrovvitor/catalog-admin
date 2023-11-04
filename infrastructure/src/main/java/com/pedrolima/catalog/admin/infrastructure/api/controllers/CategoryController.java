package com.pedrolima.catalog.admin.infrastructure.api.controllers;

import com.pedrolima.catalog.admin.domain.pagination.Pagination;
import com.pedrolima.catalog.admin.infrastructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {
    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return null;
    }
}
