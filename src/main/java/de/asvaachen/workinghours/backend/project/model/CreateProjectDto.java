package de.asvaachen.workinghours.backend.project.model;

import javax.validation.constraints.NotNull;

public class CreateProjectDto {

    @NotNull
    String name;

    @NotNull
    String description;

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
}
