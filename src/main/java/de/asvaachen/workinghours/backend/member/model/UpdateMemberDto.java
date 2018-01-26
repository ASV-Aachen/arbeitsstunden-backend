package de.asvaachen.workinghours.backend.member.model;

public class UpdateMemberDto {

    String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
