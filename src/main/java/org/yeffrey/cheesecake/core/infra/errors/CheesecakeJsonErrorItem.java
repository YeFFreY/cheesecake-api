package org.yeffrey.cheesecake.core.infra.errors;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CheesecakeJsonErrorItem(String path, String message) {
}
