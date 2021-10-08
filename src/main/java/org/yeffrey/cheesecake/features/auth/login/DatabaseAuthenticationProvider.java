package org.yeffrey.cheesecake.features.auth.login;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Objects;

@Singleton
class DatabaseAuthenticationProvider implements AuthenticationProvider {
    private final LoginService loginService;

    public DatabaseAuthenticationProvider(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            if (Objects.isNull(authenticationRequest)) {
                emitter.error(AuthenticationResponse.exception());
            } else {
                if (loginService.hasMatch((String) authenticationRequest.getIdentity(), (String) authenticationRequest.getSecret())) {
                    emitter.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity()));
                    emitter.complete();
                } else {
                    emitter.error(AuthenticationResponse.exception());
                }
            }

        }, FluxSink.OverflowStrategy.ERROR);
    }
}
