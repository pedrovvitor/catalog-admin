package com.pedrolima.catalog.admin.domain.category;

import com.pedrolima.catalog.admin.domain.exceptions.DomainException;
import com.pedrolima.catalog.admin.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

  @Test
  public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;
    final var actualCategory = Category.newCategory(expectedName, expectedDescription,
        expectedIsActive);

    Assertions.assertNotNull(actualCategory);
    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertNotNull(actualCategory.getCreatedAt());
    Assertions.assertNotNull(actualCategory.getUpdatedAt());
    Assertions.assertNull(actualCategory.getDeletedAt());
  }

  @Test
  public void givenInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
    final String expectedName = null;
    final var expectedErrorCount = 1;
    final var expectedErrorNessage = "'name' should not be null";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;
    final var actualCategory =
        Category.newCategory(expectedName, expectedDescription, expectedIsActive);

    final var actualException =
        Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorNessage, actualException.getErrors().get(0).message());
    Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
  }

  @Test
  public void givenInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
    final String expectedName = "  ";
    final var expectedErrorCount = 1;
    final var expectedErrorNessage = "'name' should not be empty";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var actualCategory =
        Category.newCategory(expectedName, expectedDescription, expectedIsActive);

    final var actualException =
        Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorNessage, actualException.getErrors().get(0).message());
    Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
  }

  @Test
  public void givenInvalidNameLenghtLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
    final var expectedName = "Fi ";
    final var expectedErrorCount = 1;
    final var expectedErrorNessage = "'name' must be between 3 and 255 characters";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var actualCategory =
        Category.newCategory(expectedName, expectedDescription, expectedIsActive);

    final var actualException =
        Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorNessage, actualException.getErrors().get(0).message());
    Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
  }

  @Test
  public void givenInvalidNameLenghtMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
    final var expectedName = """
        A prática cotidiana prova que o aumento do diálogo entre os diferentes setores produtivos
        maximiza as possibilidades por conta dos níveis de motivação departamental. A certificação
        de metodologias que nos auxiliam a lidar com a constante divulgação das informações representa
        uma abertura para a melhoria do sistema de participação geral.
        """;
    final var expectedErrorCount = 1;
    final var expectedErrorNessage = "'name' must be between 3 and 255 characters";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var actualCategory =
        Category.newCategory(expectedName, expectedDescription, expectedIsActive);

    final var actualException =
        Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorNessage, actualException.getErrors().get(0).message());
    Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
  }

  @Test
  public void givenAValidEmptyDescription_whenCallNewCategory_thenShouldNotReceiveError() {
    final var expectedName = "Filmes";
    final var expectedDescription = "";
    final var expectedIsActive = true;

    final var actualCategory = Category.newCategory(expectedName, expectedDescription,
        expectedIsActive);

    Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertNotNull(actualCategory);
    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertNotNull(actualCategory.getCreatedAt());
    Assertions.assertNotNull(actualCategory.getUpdatedAt());
    Assertions.assertNull(actualCategory.getDeletedAt());
  }

  @Test
  public void givenAValidFalseIsValid_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = false;

    final var actualCategory = Category.newCategory(expectedName, expectedDescription,
        expectedIsActive);

    Assertions.assertNotNull(actualCategory);
    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertNotNull(actualCategory.getCreatedAt());
    Assertions.assertNotNull(actualCategory.getUpdatedAt());
    Assertions.assertNotNull(actualCategory.getDeletedAt());
  }

  @Test
  public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {
    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = false;

    final var aCategory =
        Category.newCategory(expectedName, expectedDescription,true);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

    final var updatedAt = aCategory.getUpdatedAt();

    Assertions.assertTrue(aCategory.isActive());
    Assertions.assertNull(aCategory.getDeletedAt());

    final var actualCategory = aCategory.deactivate();

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertNotNull(actualCategory.getCreatedAt());
    Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    Assertions.assertNotNull(actualCategory.getDeletedAt());
  }

  @Test
  public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {
    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var aCategory = Category.newCategory(expectedName, expectedDescription,
        false);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

    final var createdAt = aCategory.getCreatedAt();
    final var updatedAt = aCategory.getUpdatedAt();

    Assertions.assertFalse(aCategory.isActive());
    Assertions.assertNotNull(aCategory.getDeletedAt());

    final var actualCategory = aCategory.activate();

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
    Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    Assertions.assertNull(actualCategory.getDeletedAt());
  }

  @Test
  public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var aCategory =
        Category.newCategory("Film", "A categoria",false);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
    Assertions.assertFalse(aCategory.isActive());
    Assertions.assertNotNull(aCategory.getDeletedAt());

    final var createdAt = aCategory.getCreatedAt();
    final var updatedAt = aCategory.getUpdatedAt();

    final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));


    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
    Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    Assertions.assertNull(actualCategory.getDeletedAt());

  }

  @Test
  public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
    final var expectedName = "Filmes";
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = false;

    final var aCategory =
        Category.newCategory("Film", "A categoria",true);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
    Assertions.assertTrue(aCategory.isActive());
    Assertions.assertNull(aCategory.getDeletedAt());

    final var createdAt = aCategory.getCreatedAt();
    final var updatedAt = aCategory.getUpdatedAt();

    final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
    Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    Assertions.assertFalse(aCategory.isActive());
    Assertions.assertNotNull(aCategory.getDeletedAt());
  }

  @Test
  public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {
    final String expectedName = null;
    final var expectedDescription = "A categoria mais assistida";
    final var expectedIsActive = true;

    final var aCategory =
        Category.newCategory("Filme", "A categoria",expectedIsActive);

    Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

    final var createdAt = aCategory.getCreatedAt();
    final var updatedAt = aCategory.getUpdatedAt();

    final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

    Assertions.assertNotNull(actualCategory.getId());
    Assertions.assertEquals(expectedName, actualCategory.getName());
    Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
    Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
    Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    Assertions.assertTrue(aCategory.isActive());
    Assertions.assertNull(aCategory.getDeletedAt());
  }
}
