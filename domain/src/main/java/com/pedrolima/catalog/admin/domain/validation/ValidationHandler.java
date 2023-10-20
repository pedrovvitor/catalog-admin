package com.pedrolima.catalog.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        return getErrors() != null && !getErrors().isEmpty() ? getErrors().get(0) : null;
    }

    public interface Validation {
        void validate();
    }
}
