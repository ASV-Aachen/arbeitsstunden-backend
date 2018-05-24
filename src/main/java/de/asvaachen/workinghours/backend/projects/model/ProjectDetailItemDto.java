package de.asvaachen.workinghours.backend.projects.model;

public class ProjectDetailItemDto {

    private String name;
    private Integer duration;
    private Integer numberMembers;

    private String maxMember;
    private Integer maxMemberMinutes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNumberMembers() {
        return numberMembers;
    }

    public void setNumberMembers(Integer numberMembers) {
        this.numberMembers = numberMembers;
    }

    public String getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(String maxMember) {
        this.maxMember = maxMember;
    }

    public Integer getMaxMemberMinutes() {
        return maxMemberMinutes;
    }

    public void setMaxMemberMinutes(Integer maxMemberMinutes) {
        this.maxMemberMinutes = maxMemberMinutes;
    }
}
