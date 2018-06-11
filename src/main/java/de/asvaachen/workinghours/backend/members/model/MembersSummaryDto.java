package de.asvaachen.workinghours.backend.members.model;

public class MembersSummaryDto {

    private Integer workedMinutesTotal;
    private Integer numMembersSailingAllowed;
    private Integer numMembersSailingNotAllowed;

    public Integer getWorkedMinutesTotal() {
        return workedMinutesTotal;
    }

    public void setWorkedMinutesTotal(Integer workedMinutesTotal) {
        this.workedMinutesTotal = workedMinutesTotal;
    }

    public Integer getNumMembersSailingAllowed() {
        return numMembersSailingAllowed;
    }

    public void setNumMembersSailingAllowed(Integer numMembersSailingAllowed) {
        this.numMembersSailingAllowed = numMembersSailingAllowed;
    }

    public Integer getNumMembersSailingNotAllowed() {
        return numMembersSailingNotAllowed;
    }

    public void setNumMembersSailingNotAllowed(Integer numMembersSailingNotAllowed) {
        this.numMembersSailingNotAllowed = numMembersSailingNotAllowed;
    }
}
