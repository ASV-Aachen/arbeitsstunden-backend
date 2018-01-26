package de.asvaachen.workinghours.backend.season.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SeasonDto {

    @NotNull
    @Min(2000)
    @Max(3000)
    Integer year;

    String label;

    @Min(60)
    Integer obligatoryMinutes;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getObligatoryMinutes() {
        return obligatoryMinutes;
    }

    public void setObligatoryMinutes(Integer obligatoryMinutes) {
        this.obligatoryMinutes = obligatoryMinutes;
    }
}