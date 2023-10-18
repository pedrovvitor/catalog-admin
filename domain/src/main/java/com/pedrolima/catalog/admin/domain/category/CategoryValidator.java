package com.pedrolima.catalog.admin.domain.category;

import com.pedrolima.catalog.admin.domain.validation.Error;
import com.pedrolima.catalog.admin.domain.validation.ValidationHandler;
import com.pedrolima.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(Category category, ValidationHandler aHandler) {
        super(aHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        var name = this.category.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > 255 || length < 3) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
