package de.asvaachen.workinghours.backend.projects.model;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class CurrentSeasonsDto {

    Integer currentSeason;
    List<SeasonDto> seasons;

    public Integer getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Integer currentSeason) {
        this.currentSeason = currentSeason;
    }

    public List<SeasonDto> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonDto> seasons) {
        this.seasons = seasons;
    }
}
