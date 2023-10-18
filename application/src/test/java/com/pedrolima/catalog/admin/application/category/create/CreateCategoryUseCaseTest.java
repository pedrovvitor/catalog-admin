package com.pedrolima.catalog.admin.application.category.create;

import com.pedrolima.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateCategoryUseCaseTest {

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Movies";
        final var expectedDescription = "The most watched category";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = mock(CategoryGateway.class);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1))
                .create(argThat(aCategory ->
                                Objects.equals(expectedName, aCategory.getName())
                                        && Objects.equals(expectedDescription, aCategory.getDescription())
                                        && Objects.equals(expectedIsActive, aCategory.isActive())
                                        && Objects.nonNull(aCategory.getId())
                                        && Objects.nonNull(aCategory.getCreatedAt())
                                        && Objects.nonNull(aCategory.getUpdatedAt())
                                        && Objects.isNull(aCategory.getDeletedAt())

                        )
                );
    }
}
