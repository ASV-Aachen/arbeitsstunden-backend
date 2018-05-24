package de.asvaachen.workinghours.backend.projects.model;

public class ProjectsOverviewDto {

    private Integer season;

    private Integer duration;

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
