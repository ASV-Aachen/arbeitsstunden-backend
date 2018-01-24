package de.asvaachen.workinghours.backend.project.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "season")
public class SeasonEntity {
    @Id
    @Column(name = "year")
    private Integer year;

    @Column(name = "obligatoryMinutes")
    private Integer obligatoryMinutes;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getObligatoryMinutes() {
        return obligatoryMinutes;
    }

    public void setObligatoryMinutes(Integer obligatoryMinutes) {
        this.obligatoryMinutes = obligatoryMinutes;
    }
}