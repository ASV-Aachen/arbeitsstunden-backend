package de.asvaachen.workinghours.backend.project.model;

import javax.validation.constraints.NotNull;

public class ProjectDto {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer firstSeason;

    private Integer lastSeason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getFirstSeason() {
        return firstSeason;
    }

    public void setFirstSeason(Integer firstSeason) {
        this.firstSeason = firstSeason;
    }

    public Integer getLastSeason() {
        return lastSeason;
    }

    public void setLastSeason(Integer lastSeason) {
        this.lastSeason = lastSeason;
    }
}
