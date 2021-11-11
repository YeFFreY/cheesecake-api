package org.yeffrey.cheesecake.core.infra.errors;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
@Requires(classes = {DataAccessException.class, ExceptionHandler.class})
public class CheesecakeDataAccessExceptionHandler implements ExceptionHandler<DataAccessException, HttpResponse<Void>> {
    private static Logger logger = LoggerFactory.getLogger(CheesecakeDataAccessExceptionHandler.class);

    @Override
    public HttpResponse<Void> handle(HttpRequest request, DataAccessException exception) {
        logger.error(exception.getMessage());
        return HttpResponse.unprocessableEntity();
    }
}
