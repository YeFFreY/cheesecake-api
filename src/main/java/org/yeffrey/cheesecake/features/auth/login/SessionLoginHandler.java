package org.yeffrey.cheesecake.features.auth.login;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.security.session.SessionAuthenticationModeCondition;
import io.micronaut.session.Session;
import io.micronaut.session.SessionStore;
import io.micronaut.session.http.SessionForRequest;
import jakarta.inject.Singleton;


@Requires(condition = SessionAuthenticationModeCondition.class)
@Singleton
@Primary
public class SessionLoginHandler implements LoginHandler {

    protected final SessionStore<Session> sessionStore;

    public SessionLoginHandler(SessionStore<Session> sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public MutableHttpResponse<?> loginSuccess(Authentication authentication, HttpRequest<?> request) {
        Session session = SessionForRequest.find(request).orElseGet(() -> SessionForRequest.create(sessionStore, request));
        session.put(SecurityFilter.AUTHENTICATION, authentication);

        return HttpResponse.ok();
    }

    @Override
    public MutableHttpResponse<?> loginRefresh(Authentication authentication, String refreshToken, HttpRequest<?> request) {
        throw new UnsupportedOperationException("No refresh support");
    }

    @Override
    public MutableHttpResponse<?> loginFailed(AuthenticationResponse authenticationResponse, HttpRequest<?> request) {
        return HttpResponse.unauthorized();
    }
}
