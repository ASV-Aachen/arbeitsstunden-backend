package de.asvaachen.workinghours.backend.project.model;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class CurrentProjectsDto {

    String currentYear;
    List<SeasonDto> seasons;
    List<ProjectOverviewDto> projects;

    public CurrentProjectsDto(String currentYear, List<SeasonDto> seasons, List<ProjectOverviewDto> projects) {
        this.currentYear = currentYear;
        this.seasons = seasons;
        this.projects = projects;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public List<SeasonDto> getSeasons() {
        return seasons;
    }

    public List<ProjectOverviewDto> getProjects() {
        return projects;
    }
}
