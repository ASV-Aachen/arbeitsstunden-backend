package de.asvaachen.workinghours.backend.member.model;

import java.util.UUID;

public class MemberListItemDto {

    private UUID memberId;
    private String firstName;
    private String lastName;
    private AsvStatus status;
    private Integer workedMinutes;
    private Integer neededMinutes;
    private Integer todoMinutes;

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

    public Integer getTodoMinutes() {
        return todoMinutes;
    }

    public void setTodoMinutes(Integer todoMinutes) {
        this.todoMinutes = todoMinutes;
    }
}
