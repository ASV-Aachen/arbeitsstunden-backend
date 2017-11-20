package de.asvaachen.workinghours.backend.project;

import java.util.List;

public class ProjectDetailsDto {

    private String name;
    private String description;
    private List<ProjectDetailsItemDto> projectItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectDetailsItemDto> getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(List<ProjectDetailsItemDto> projectItems) {
        this.projectItems = projectItems;
    }
}