package org.yeffrey.cheesecake.features.auth.session;

class UserSession {
    private final String email;

    public UserSession(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
