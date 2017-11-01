package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class ActiveMemberWorkinghoursDto {

    private Integer activeYear;
    private List<SeasonDto> seasons;
    private List<WorkinghourItemDto> workinghours;

    public Integer getActiveYear() {
        return activeYear;
    }

    public void setActiveYear(Integer activeYear) {
        this.activeYear = activeYear;
    }

    public List<SeasonDto> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonDto> seasons) {
        this.seasons = seasons;
    }

    public List<WorkinghourItemDto> getWorkinghours() {
        return workinghours;
    }

    public void setWorkinghours(List<WorkinghourItemDto> workinghours) {
        this.workinghours = workinghours;
    }
}