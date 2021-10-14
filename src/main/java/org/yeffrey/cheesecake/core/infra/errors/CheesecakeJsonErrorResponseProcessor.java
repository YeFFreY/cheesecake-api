package org.yeffrey.cheesecake.core.infra.errors;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Secondary;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.Error;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;

@Singleton
@Secondary
@Replaces(ErrorResponseProcessor.class)
public class CheesecakeJsonErrorResponseProcessor implements ErrorResponseProcessor<CheesecakeJsonError> {

    // Always serialize error as list

    @Override
    @NonNull
    public MutableHttpResponse<CheesecakeJsonError> processResponse(@NonNull ErrorContext errorContext, @NonNull MutableHttpResponse<?> response) {
        CheesecakeJsonError error;
        if (!errorContext.hasErrors()) {
            error = new CheesecakeJsonError();
        } else {
            error = new CheesecakeJsonError();
            for (Error jsonError : errorContext.getErrors()) {
                error.errors.add(new CheesecakeJsonErrorItem(jsonError.getPath().orElse(null), jsonError.getMessage()));
            }

        }

        return response.body(error).contentType(MediaType.APPLICATION_JSON_TYPE);
    }
}
