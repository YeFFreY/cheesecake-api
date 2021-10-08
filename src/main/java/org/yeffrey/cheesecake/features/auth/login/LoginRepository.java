package org.yeffrey.cheesecake.features.auth.login;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.Optional;

import static org.yeffrey.cheesecake.Tables.T_CREDENTIALS;
import static org.yeffrey.cheesecake.Tables.T_USER;

@Singleton
public class LoginRepository {

    private final DSLContext ctx;

    public LoginRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public Optional<String> findPassword(String email) {
        return ctx.select(T_CREDENTIALS.PASSWORD)
                .from(T_CREDENTIALS)
                .innerJoin(T_USER).on(T_CREDENTIALS.ID.eq(T_USER.ID))
                .where(T_USER.EMAIL.eq(email))
                .fetchOptional(T_CREDENTIALS.PASSWORD);
    }
}
