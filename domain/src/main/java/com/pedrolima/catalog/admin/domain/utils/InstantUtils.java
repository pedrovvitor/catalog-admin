package com.pedrolima.catalog.admin.domain.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    public static Instant nowWith(ChronoUnit chronoUnit) {
        return Instant.now().truncatedTo(chronoUnit);
    }
}
