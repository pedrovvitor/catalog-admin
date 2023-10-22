package com.pedrolima.catalog.admin.application.category.update;

import com.pedrolima.catalog.admin.application.UseCase;
import com.pedrolima.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
