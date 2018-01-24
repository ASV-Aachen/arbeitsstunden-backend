package de.asvaachen.workinghours.backend.project.model;

public class ProjectSummaryDto {

    private String description;
    private float percentage;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
