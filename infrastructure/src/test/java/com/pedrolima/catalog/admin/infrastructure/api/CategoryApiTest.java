package com.pedrolima.catalog.admin.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrolima.catalog.admin.ControllerTest;
import com.pedrolima.catalog.admin.application.category.create.CreateCategoryOutput;
import com.pedrolima.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.pedrolima.catalog.admin.application.category.retrieve.get.CategoryOutput;
import com.pedrolima.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.pedrolima.catalog.admin.domain.category.Category;
import com.pedrolima.catalog.admin.domain.category.CategoryID;
import com.pedrolima.catalog.admin.domain.exceptions.DomainException;
import com.pedrolima.catalog.admin.domain.validation.Error;
import com.pedrolima.catalog.admin.domain.validation.handler.Notification;
import com.pedrolima.catalog.admin.infrastructure.category.models.CreateCategoryApiInput;
import io.vavr.API;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        // given
        final var expectedName = "Movies";
        final var expectedDescription = "The most watched category";
        final var expectedIsActive = true;

        final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(API.Right(CreateCategoryOutput.from(CategoryID.from("123").getValue())));

        // when
        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aInput));

        final var response = mvc.perform(request)
                .andDo(print());

        // then
        response.andExpectAll(
                status().isCreated(),
                header().string("Location", "/categories/123"),
                header().string("Content-type", MediaType.APPLICATION_JSON_VALUE),
                jsonPath("$.id", equalTo("123"))
        );

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "The most watched category";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(API.Left(Notification.create(new Error(expectedErrorMessage))));

        // when
        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aInput));

        final var response = mvc.perform(request)
                .andDo(print());
        // then
        response.andExpectAll(
                status().isUnprocessableEntity(),
                header().string("Location", Matchers.nullValue()),
                header().string("Content-type", MediaType.APPLICATION_JSON_VALUE),
                jsonPath("$.errors", hasSize(1)),
                jsonPath("$.errors[0].message", equalTo(expectedErrorMessage))
        );

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "The most watched category";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedErrorMessage)));

        // when
        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aInput));

        final var response = mvc.perform(request)
                .andDo(print());

        // then
        response.andExpectAll(
                status().isUnprocessableEntity(),
                header().string("Location", Matchers.nullValue()),
                header().string("Content-type", MediaType.APPLICATION_JSON_VALUE),
                jsonPath("$.message", equalTo(expectedErrorMessage)),
                jsonPath("$.errors", hasSize(1)),
                jsonPath("$.errors[0].message", equalTo(expectedErrorMessage))
        );

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        // given
        final var expectedName = "Movies";
        final var expectedDescription = "The most watched category";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryByIdUseCase.execute(any())).thenReturn(CategoryOutput.from(aCategory));

        // when
        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId);

        final var response = mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", equalTo(expectedId)))
                        .andExpect(jsonPath("$.name", equalTo(expectedName)))
                        .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                        .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                        .andExpect(jsonPath("$.created_at", equalTo(aCategory.getCreatedAt())))
                        .andExpect(jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt())))
                        .andExpect(jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt())));

        verify(getCategoryByIdUseCase, times(1)).execute(expectedId);
    }

    @Test
    public void givenAnInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        // given
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Category with ID %s was not found".formatted(expectedId.getValue());

        // when
        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId);

        final var response = mvc.perform(request)
                        .andDo(print());

        // then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }
}
