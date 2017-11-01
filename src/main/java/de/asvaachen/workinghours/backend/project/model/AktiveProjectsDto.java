package de.asvaachen.workinghours.backend.project.model;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class AktiveProjectsDto {

    Integer activeYear;
    List<SeasonDto> seasons;
    List<ProjectDto> projects;

    public Integer getActiveYear() {
        return activeYear;
    }

    public void setActiveYear(Integer activeYear) {
        this.activeYear = activeYear;
    }

    public List<SeasonDto> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonDto> seasons) {
        this.seasons = seasons;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }
}
