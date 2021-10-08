package org.yeffrey.cheesecake.features.auth.logout;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.convert.value.MutableConvertibleValues;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.security.handlers.LogoutHandler;
import io.micronaut.security.session.SessionAuthenticationModeCondition;
import io.micronaut.session.Session;
import io.micronaut.session.http.HttpSessionFilter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Requires(condition = SessionAuthenticationModeCondition.class)
@Singleton
@Primary
public class SessionLogoutHandler implements LogoutHandler {
    @Override
    public MutableHttpResponse<?> logout(HttpRequest<?> request) {
        MutableConvertibleValues<Object> attrs = request.getAttributes();
        Optional<Session> existing = attrs.get(HttpSessionFilter.SESSION_ATTRIBUTE, Session.class);
        if (existing.isPresent()) {
            Session session = existing.get();
            session.remove(SecurityFilter.AUTHENTICATION);
        }

        return HttpResponse.noContent();
    }
}
