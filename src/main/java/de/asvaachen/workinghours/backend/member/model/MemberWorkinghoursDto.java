package de.asvaachen.workinghours.backend.member.model;

import de.asvaachen.workinghours.backend.members.model.WorkinghourItemDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;

import java.util.List;

public class MemberWorkinghoursDto {

    private Integer activeSeason;
    private Integer selectedSeason;
    private Integer neededMinutes;
    private Integer workedMinutes;
    private List<SeasonDto> seasons;
    private List<WorkinghourItemDto> workinghours;

    public Integer getActiveSeason() {
        return activeSeason;
    }

    public void setActiveSeason(Integer activeSeason) {
        this.activeSeason = activeSeason;
    }

    public Integer getSelectedSeason() {
        return selectedSeason;
    }

    public void setSelectedSeason(Integer selectedSeason) {
        this.selectedSeason = selectedSeason;
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