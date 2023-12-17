package com.pedrolima.catalog.admin.domain.exceptions;

import com.pedrolima.catalog.admin.domain.AggregateRoot;
import com.pedrolima.catalog.admin.domain.Identifier;
import com.pedrolima.catalog.admin.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> errors) {
        super(aMessage, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier anId
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                anId.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }
}
