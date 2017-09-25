package de.asvaachen.workinghours.backend;

public class WorkingHoursSeasonDto {

    String year;
    String label;

    public WorkingHoursSeasonDto(String year, String label) {
        this.year = year;
        this.label = label;
    }

    public String getYear() {
        return year;
    }

    public String getLabel() {
        return label;
    }
}
