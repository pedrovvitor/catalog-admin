package com.pedrolima.catalog.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

  ValidationHandler append(Error anError);

  ValidationHandler append(ValidationHandler aHandler);

  ValidationHandler validate(Validation aValitadion);

  List<Error> getErrors();

  default boolean hasErrors() {
    return getErrors() != null && !(getErrors().isEmpty());
  }
  interface Validation {
    void validate();
  }
}
