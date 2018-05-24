package de.asvaachen.workinghours.backend.project.model;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class ProjectsTakelSummaryDto {

    private List<SeasonDto> seasons;
    private Integer currentSeason;

    public List<SeasonDto> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonDto> seasons) {
        this.seasons = seasons;
    }

    public Integer getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Integer currentSeason) {
        this.currentSeason = currentSeason;
    }
}
