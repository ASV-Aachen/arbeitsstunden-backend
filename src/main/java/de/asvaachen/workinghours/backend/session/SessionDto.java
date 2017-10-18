package de.asvaachen.workinghours.backend.session;

import java.util.UUID;

public class SessionDto {

    UUID token;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
