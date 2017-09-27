package de.asvaachen.workinghours.backend.project.model;

import java.util.List;

public class CurrentProjectsDto {

    String currentYear;
    List<WorkingHoursSeasonDto> seasons;
    List<ProjectDto> projects;

    public CurrentProjectsDto(String currentYear, List<WorkingHoursSeasonDto> seasons, List<ProjectDto> projects) {
        this.currentYear = currentYear;
        this.seasons = seasons;
        this.projects = projects;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public List<WorkingHoursSeasonDto> getSeasons() {
        return seasons;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }
}
