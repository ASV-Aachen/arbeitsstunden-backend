package de.asvaachen.workinghours.backend.members.model;

public class UpdateMemberDto {

    String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
