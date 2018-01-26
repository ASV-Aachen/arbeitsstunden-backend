package de.asvaachen.workinghours.backend.member.model;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

public class OverviewSeasonDto {

    SeasonDto season;
    Integer minutes;

    public SeasonDto getSeason() {
        return season;
    }

    public void setSeason(SeasonDto season) {
        this.season = season;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}