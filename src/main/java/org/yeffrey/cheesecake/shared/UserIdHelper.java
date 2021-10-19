package org.yeffrey.cheesecake.shared;

import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class UserIdHelper {
    private final DSLContext ctx;
    private final SecurityService securityService;

    public UserIdHelper(DSLContext ctx, SecurityService securityService) {
        this.ctx = ctx;
        this.securityService = securityService;
    }

    @Transactional
    public Optional<UUID> get(String username) {
        return ctx.select(T_USER.ID)
                .from(T_USER)
                .where(T_USER.EMAIL.eq(username))
                .fetchOptional(T_USER.ID);
    }

    public Optional<UUID> getCurrent() {
        return securityService.getAuthentication()
                .map(Authentication::getAttributes).stream()
                .map(attr -> (UUID) attr.get("id"))
                .findFirst();
    }

    public UUID getCurrentOrThrow() {
        return this.getCurrent().orElseThrow(AuthenticationException::new);
    }
}
