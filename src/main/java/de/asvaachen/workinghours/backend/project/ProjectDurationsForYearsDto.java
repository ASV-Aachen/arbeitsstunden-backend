package de.asvaachen.workinghours.backend.project;

public class ProjectDurationsForYearsDto {

    Integer season;
    Integer duration;

    public ProjectDurationsForYearsDto(Integer season, Integer duration) {
        this.season = season;
        this.duration = duration;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getDuration() {
        return duration;
    }
}