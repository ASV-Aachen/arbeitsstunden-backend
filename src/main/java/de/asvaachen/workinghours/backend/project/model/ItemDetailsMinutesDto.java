package de.asvaachen.workinghours.backend.project.model;

public class ItemDetailsMinutesDto {

    private MemberDto member;
    private Integer duration;

    public MemberDto getMember() {
        return member;
    }

    public void setMember(MemberDto member) {
        this.member = member;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}