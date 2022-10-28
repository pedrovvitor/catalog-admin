package com.pedrolima.catalog.admin.application.category.create;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pedrolima.catalog.admin.domain.category.CategoryGateway;
import com.pedrolima.catalog.admin.domain.exceptions.DomainException;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

  @InjectMocks
  private DefaultCreateCategoryUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;
  // Teste do caminho feliz
  // Teste passando uma propriedade inválida
  // Teste criando criando uma categoria inativa
  // teste simulando um erro genérico vindo do gateway

  @Test
  public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {

    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription,
        expectedIsActive);

    when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

    final var actualOutput = useCase.execute(aCommand);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertNotNull(actualOutput.id());

    verify(categoryGateway, times(1)).create(argThat(aCategory ->
        Objects.equals(expectedName, aCategory.getName())
            && Objects.equals(expectedDescription, aCategory.getDescription())
            && Objects.equals(expectedIsActive, aCategory.isActive())
            && Objects.nonNull(aCategory.getId())
            && Objects.nonNull(aCategory.getCreatedAt())
            && Objects.nonNull(aCategory.getUpdatedAt())
            && Objects.isNull(aCategory.getDeletedAt())
    ));
  }

  @Test
  public void givenAInvalidName_whenCallsCreateCategory_shouldReturnDomainException() {

    final String expectedName = null;
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;
    final var expectedErrorMessage = "'name' should not be null";
    final var expectedErroCount = 1;

    final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription,
        expectedIsActive);

    final var actualException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(aCommand));

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    Mockito.verify(categoryGateway, times(0)).create(any());
  }

  @Test
  public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {

    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = false;

    final var aCommand =
        CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

    when(categoryGateway.create(any()))
        .thenAnswer(returnsFirstArg());

    final var actualOutput = useCase.execute(aCommand);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertNotNull(actualOutput.id());

    verify(categoryGateway, times(1)).create(argThat(aCategory ->
        Objects.equals(expectedName, aCategory.getName())
            && Objects.equals(expectedDescription, aCategory.getDescription())
            && Objects.equals(expectedIsActive, aCategory.isActive())
            && Objects.nonNull(aCategory.getId())
            && Objects.nonNull(aCategory.getCreatedAt())
            && Objects.nonNull(aCategory.getUpdatedAt())
            && Objects.nonNull(aCategory.getDeletedAt())
    ));
  }

  @Test
  public void givenAValidCommand_whenGatewayThrowsRangomException_shouldReturnAException() {

    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;
    final var expectedErrorMessage = "Gateway error";

    final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription,
        expectedIsActive);

    when(categoryGateway.create(any()))
        .thenThrow(new IllegalStateException(expectedErrorMessage));

    final var actualException =
        Assertions.assertThrows(IllegalStateException.class,() -> useCase.execute(aCommand));

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    verify(categoryGateway, times(1)).create(argThat(aCategory ->
        Objects.equals(expectedName, aCategory.getName())
            && Objects.equals(expectedDescription, aCategory.getDescription())
            && Objects.equals(expectedIsActive, aCategory.isActive())
            && Objects.nonNull(aCategory.getId())
            && Objects.nonNull(aCategory.getCreatedAt())
            && Objects.nonNull(aCategory.getUpdatedAt())
            && Objects.isNull(aCategory.getDeletedAt())
    ));
  }
}
