package de.asvaachen.workinghours.backend.user;

import java.util.List;

public class MemberDetailsDto {

    private String firstName;
    private String lastName;
    private String email;

    private List<SeasonReductionDto> seasonReduction;

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

    public List<SeasonReductionDto> getSeasonReduction() {
        return seasonReduction;
    }

    public void setSeasonReduction(List<SeasonReductionDto> seasonReduction) {
        this.seasonReduction = seasonReduction;
    }
}
