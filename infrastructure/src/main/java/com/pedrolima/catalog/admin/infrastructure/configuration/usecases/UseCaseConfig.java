package com.pedrolima.catalog.admin.infrastructure.configuration.usecases;

import com.pedrolima.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.create.DefaultCreateCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.delete.DefaultDeleteCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.pedrolima.catalog.admin.application.category.update.DefaultUpdateCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.pedrolima.catalog.admin.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final CategoryGateway categoryGateway;

    public UseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }
}
