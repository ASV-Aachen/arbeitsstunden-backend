package de.asvaachen.workinghours.backend.projects.model;

import de.asvaachen.workinghours.backend.project.model.ProjectDetailsItemDto;

import java.util.List;

public class ProjectsDetailDto {

    private Integer numberProjects;
    private List<ProjectDetailItemDto> projects;

    public Integer getNumberProjects() {
        return numberProjects;
    }

    public void setNumberProjects(Integer numberProjects) {
        this.numberProjects = numberProjects;
    }

    public List<ProjectDetailItemDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDetailItemDto> projects) {
        this.projects = projects;
    }
}
