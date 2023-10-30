package com.pedrolima.catalog.admin.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String property, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(property)), like(term).toUpperCase());
    }

    private static String like(final String term) {
        return "%" + term.toUpperCase() + "%";
    }
}
