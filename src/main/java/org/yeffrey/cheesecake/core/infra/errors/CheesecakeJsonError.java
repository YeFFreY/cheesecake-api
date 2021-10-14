package org.yeffrey.cheesecake.core.infra.errors;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;

import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Introspected
public class CheesecakeJsonError {

    public final List<CheesecakeJsonErrorItem> errors = new ArrayList<>();

}
