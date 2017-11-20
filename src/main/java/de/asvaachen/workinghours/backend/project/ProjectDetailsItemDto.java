package de.asvaachen.workinghours.backend.project;

import java.util.List;
import java.util.UUID;

public class ProjectDetailsItemDto {

    private UUID id;
    private String title;
    private String description;
    private String date;

    private List<ItemDetailsMinutesDto> itemDetailsMinutes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<ItemDetailsMinutesDto> getItemDetailsMinutes() {
        return itemDetailsMinutes;
    }

    public void setItemDetailsMinutes(List<ItemDetailsMinutesDto> itemDetailsMinutes) {
        this.itemDetailsMinutes = itemDetailsMinutes;
    }
}
