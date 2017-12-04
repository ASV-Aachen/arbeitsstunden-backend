package de.asvaachen.workinghours.backend.user;

import java.util.UUID;

public class MemberListItemDto {

    private UUID memberId;
    private String firstName;
    private String lastName;
    private AsvStatus status;
    private Integer workedMinutes;
    private Integer neededMinutes;

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AsvStatus getStatus() {
        return status;
    }

    public void setStatus(AsvStatus status) {
        this.status = status;
    }

    public Integer getWorkedMinutes() {
        return workedMinutes;
    }

    public void setWorkedMinutes(Integer workedMinutes) {
        this.workedMinutes = workedMinutes;
    }

    public Integer getNeededMinutes() {
        return neededMinutes;
    }

    public void setNeededMinutes(Integer neededMinutes) {
        this.neededMinutes = neededMinutes;
    }
}
