package de.asvaachen.workinghours.backend.session;

import java.util.UUID;

public class SessionDto {

    private UUID token;

    private String role;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
