package org.yeffrey.cheesecake.features.auth.session;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.security.Principal;
import java.util.Objects;

@Controller("/auth/session")
class UserSessionController {
    @Get
    public HttpResponse<QueryResult<UserSession>> currentUserSession(@Nullable Principal principal) {
        if(Objects.isNull(principal)) {
            return HttpResponse.unauthorized();
        }
        return HttpResponse.ok(new QueryResult<>(new UserSession(principal.getName())));
    }
}
