package org.yeffrey.cheesecake.features.auth.login;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yeffrey.cheesecake.shared.UserIdHelper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Singleton
class DatabaseAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(DatabaseAuthenticationProvider.class);

    private final LoginService loginService;
    private final UserIdHelper userIdHelper;

    public DatabaseAuthenticationProvider(LoginService loginService, UserIdHelper userIdHelper) {
        this.loginService = loginService;
        this.userIdHelper = userIdHelper;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            if (Objects.isNull(authenticationRequest)) {
                emitter.error(AuthenticationResponse.exception());
            } else {
                if (loginService.hasMatch((String) authenticationRequest.getIdentity(), (String) authenticationRequest.getSecret())) {
                    UUID userId = userIdHelper.get((String)authenticationRequest.getIdentity()).orElseThrow(AuthenticationException::new);
                    emitter.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity(), Collections.singletonMap("id", userId)));
                    emitter.complete();
                } else {
                    logger.error(MessageFormat.format("Invalid login from {0}", authenticationRequest.getIdentity()));
                    emitter.error(AuthenticationResponse.exception());
                }
            }

        }, FluxSink.OverflowStrategy.ERROR);
    }
}
