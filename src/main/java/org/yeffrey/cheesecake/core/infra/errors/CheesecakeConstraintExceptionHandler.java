package org.yeffrey.cheesecake.core.infra.errors;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.Error;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import io.micronaut.validation.exceptions.ConstraintExceptionHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Produces
@Singleton
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
@Replaces(ConstraintExceptionHandler.class)
public class CheesecakeConstraintExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<CheesecakeJsonError>> {
    private final ErrorResponseProcessor<CheesecakeJsonError> responseProcessor;

    /**
     * Constructor.
     * @param responseProcessor Error Response Processor
     */
    @Inject
    public CheesecakeConstraintExceptionHandler(ErrorResponseProcessor<CheesecakeJsonError> responseProcessor) {
        this.responseProcessor = responseProcessor;
    }


    @Override
    public HttpResponse<CheesecakeJsonError> handle(HttpRequest request, ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        MutableHttpResponse<?> response = HttpResponse.badRequest();
        final ErrorContext.Builder contextBuilder = ErrorContext.builder(request).cause(exception);
        if (constraintViolations == null || constraintViolations.isEmpty()) {
            return responseProcessor.processResponse(contextBuilder.errorMessage(
                    exception.getMessage() == null ? HttpStatus.BAD_REQUEST.getReason() : exception.getMessage()
            ).build(), response);
        } else {
            return responseProcessor.processResponse(contextBuilder.errors(
                    exception.getConstraintViolations()
                            .stream()
                            .map(this::buildMessage)
                            .collect(Collectors.toList())
            ).build(), response);
        }
    }

    protected io.micronaut.http.server.exceptions.response.Error buildMessage(ConstraintViolation violation) {
        String fieldName = null;
        for (Path.Node node : violation.getPropertyPath()) {
            fieldName = node.getName();
        }
        String finalFieldName = fieldName;
        return new Error() {
            @Override
            public Optional<String> getPath() {
                return Optional.of(finalFieldName);
            }

            @Override
            public String getMessage() {
                return violation.getMessage();
            }
        };
    }
}
