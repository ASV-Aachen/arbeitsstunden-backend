package de.asvaachen.workinghours.backend.login;

import java.util.UUID;

public class LoginDto {

    private UUID token;

    private String role;

    private UUID memberId;

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

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }
}
