package de.asvaachen.workinghours.backend.season.model;

import java.util.List;

public class AvailableSeasonsDto {

    Integer activeSeason;
    List<SeasonDto> seasons;

    public Integer getActiveSeason() {
        return activeSeason;
    }

    public void setActiveSeason(Integer activeSeason) {
        this.activeSeason = activeSeason;
    }

    public List<SeasonDto> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonDto> seasons) {
        this.seasons = seasons;
    }
}
