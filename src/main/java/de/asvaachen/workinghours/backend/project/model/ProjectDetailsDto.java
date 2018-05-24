package de.asvaachen.workinghours.backend.project.model;

public class ProjectDetailsDto {

    private String description;
    private float percentage;

    private String maxSeasonMember;
    private Integer maxSeasonMinutes;

    private String maxOverallMember;
    private Integer maxOverallMinutes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getMaxSeasonMember() {
        return maxSeasonMember;
    }

    public void setMaxSeasonMember(String maxSeasonMember) {
        this.maxSeasonMember = maxSeasonMember;
    }

    public Integer getMaxSeasonMinutes() {
        return maxSeasonMinutes;
    }

    public void setMaxSeasonMinutes(Integer maxSeasonMinutes) {
        this.maxSeasonMinutes = maxSeasonMinutes;
    }

    public String getMaxOverallMember() {
        return maxOverallMember;
    }

    public void setMaxOverallMember(String maxOverallMember) {
        this.maxOverallMember = maxOverallMember;
    }

    public Integer getMaxOverallMinutes() {
        return maxOverallMinutes;
    }

    public void setMaxOverallMinutes(Integer maxOverallMinutes) {
        this.maxOverallMinutes = maxOverallMinutes;
    }
}
