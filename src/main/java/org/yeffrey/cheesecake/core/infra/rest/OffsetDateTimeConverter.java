package org.yeffrey.cheesecake.core.infra.rest;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Singleton
public class OffsetDateTimeConverter implements TypeConverter<String, OffsetDateTime> {
    @Override
    public Optional<OffsetDateTime> convert(String input, Class<OffsetDateTime> targetType, ConversionContext context) {
        try {
            return Optional.of(OffsetDateTime.parse(input));

        } catch (DateTimeParseException ex) {
            context.reject(input, ex);
        }
        return Optional.empty();
    }
}
