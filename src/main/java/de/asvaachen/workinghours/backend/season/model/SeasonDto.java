package de.asvaachen.workinghours.backend.season.model;

public class SeasonDto {

    Integer year;
    String label;

    public SeasonDto(Integer year, String label) {
        this.year = year;
        this.label = label;
    }

    public Integer getYear() {
        return year;
    }

    public String getLabel() {
        return label;
    }
}
