package org.yeffrey.cheesecake.features.auth.login;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.core.infra.PasswordEncoder;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

@Singleton
class LoginService {

    private final LoginRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(LoginRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean hasMatch(@NotBlank String email, @NotBlank String password) {
        return this.repository.findPassword(email)
                .map(encodedPassword -> this.passwordEncoder.matches(password, encodedPassword))
                .orElse(false);
    }
}
