package org.yeffrey.cheesecake.core.infra;

import jakarta.inject.Singleton;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Singleton
public class PasswordEncoder {
    private org.springframework.security.crypto.password.PasswordEncoder delegate = new Argon2PasswordEncoder();

    public String encode(String rawPassword) {
        return this.delegate.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return this.delegate.matches(rawPassword, encodedPassword);
    }
}
