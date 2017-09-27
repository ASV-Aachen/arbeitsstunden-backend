package de.asvaachen.workinghours.backend.project.model;

public class ProjectDto {

    String id;
    String name;
    Integer duration;

    public ProjectDto(String id, String name, Integer duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }
}
