package org.yeffrey.cheesecake.core.infra.errors;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CheesecakeJsonErrorItem {

    public final String path;
    public final String message;

    public CheesecakeJsonErrorItem(String path, String message) {
        this.path = path;
        this.message = message;
    }
}
