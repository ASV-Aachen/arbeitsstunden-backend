package de.asvaachen.workinghours.backend.project.model;

import java.util.List;

public class ProjectItemDto {

    String id;
    String projectId;

    Integer season;
    String title;
    String description;
    String date;

    List<ProjectItemHourDto> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ProjectItemHourDto> getItems() {
        return items;
    }

    public void setItems(List<ProjectItemHourDto> items) {
        this.items = items;
    }
}