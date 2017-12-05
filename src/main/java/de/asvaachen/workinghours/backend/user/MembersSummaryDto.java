package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberDto;

public class MembersSummaryDto {

    private Integer workedMinutesTotal;
    private Integer numMembersSailingAllowed;
    private Integer numMembersSailingNotAllowed;
    private MemberDto kingMember;
    private Integer kingMinutes;

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

    public MemberDto getKingMember() {
        return kingMember;
    }

    public void setKingMember(MemberDto kingMember) {
        this.kingMember = kingMember;
    }

    public Integer getKingMinutes() {
        return kingMinutes;
    }

    public void setKingMinutes(Integer kingMinutes) {
        this.kingMinutes = kingMinutes;
    }
}
