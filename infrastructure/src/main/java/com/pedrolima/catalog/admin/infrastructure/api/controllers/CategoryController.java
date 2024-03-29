package com.pedrolima.catalog.admin.infrastructure.api.controllers;

import com.pedrolima.catalog.admin.application.category.create.CreateCategoryCommand;
import com.pedrolima.catalog.admin.application.category.create.CreateCategoryOutput;
import com.pedrolima.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.pedrolima.catalog.admin.application.category.update.UpdateCategoryCommand;
import com.pedrolima.catalog.admin.application.category.update.UpdateCategoryOutput;
import com.pedrolima.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.pedrolima.catalog.admin.domain.category.CategorySearchQuery;
import com.pedrolima.catalog.admin.domain.pagination.Pagination;
import com.pedrolima.catalog.admin.domain.validation.handler.Notification;
import com.pedrolima.catalog.admin.infrastructure.api.CategoryAPI;
import com.pedrolima.catalog.admin.infrastructure.category.models.CategoryListResponse;
import com.pedrolima.catalog.admin.infrastructure.category.models.CategoryResponse;
import com.pedrolima.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
import com.pedrolima.catalog.admin.infrastructure.category.models.UpdateCategoryRequest;
import com.pedrolima.catalog.admin.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase, final GetCategoryByIdUseCase getCategoryByIdUseCase, final UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase, ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listCategoriesUseCase.execute(new CategorySearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(final String id) {
        return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }
    @Override
    public void deleteById(final String id) {
        deleteCategoryUseCase.execute(id);
    }
}
