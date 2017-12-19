package de.asvaachen.workinghours.backend.user.model;

import de.asvaachen.workinghours.backend.user.AsvStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateMemberDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    @NotNull
    @Min(2000)
    @Max(2100)
    private Integer firstSeason;

    private AsvStatus status;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFirstSeason() {
        return firstSeason;
    }

    public void setFirstSeason(Integer firstSeason) {
        this.firstSeason = firstSeason;
    }

    public AsvStatus getStatus() {
        return status;
    }

    public void setStatus(AsvStatus status) {
        this.status = status;
    }
}