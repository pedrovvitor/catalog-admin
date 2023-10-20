package com.pedrolima.catalog.admin.application.category.create;

import com.pedrolima.catalog.admin.application.UseCase;
import com.pedrolima.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
