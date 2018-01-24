package de.asvaachen.workinghours.backend.user.model;

import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class ActiveMemberWorkinghoursDto {

    private Integer activeYear;
    private List<SeasonDto> seasons;
    private List<WorkinghourItemDto> workinghours;

    private Integer neededMinutes;
    private Integer workedMinutes;

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

    public Integer getNeededMinutes() {
        return neededMinutes;
    }

    public void setNeededMinutes(Integer neededMinutes) {
        this.neededMinutes = neededMinutes;
    }

    public Integer getWorkedMinutes() {
        return workedMinutes;
    }

    public void setWorkedMinutes(Integer workedMinutes) {
        this.workedMinutes = workedMinutes;
    }
}