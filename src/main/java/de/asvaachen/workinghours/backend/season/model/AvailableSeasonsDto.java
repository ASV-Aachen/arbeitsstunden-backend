package de.asvaachen.workinghours.backend.season.model;

import java.util.List;

public class AvailableSeasonsDto {

    Integer current;
    List<SeasonDto> seasons;

    public AvailableSeasonsDto(Integer current, List<SeasonDto> seasons) {
        this.current = current;
        this.seasons = seasons;
    }

    public Integer getCurrent() {
        return current;
    }

    public List<SeasonDto> getSeasons() {
        return seasons;
    }
}
