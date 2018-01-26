package de.asvaachen.workinghours.backend.member.model;

import java.util.List;

public class MemberWorkinghoursDto {

    private Integer neededMinutes;
    private Integer workedMinutes;
    private List<WorkinghourItemDto> workinghourItems;

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

    public List<WorkinghourItemDto> getWorkinghourItems() {
        return workinghourItems;
    }

    public void setWorkinghourItems(List<WorkinghourItemDto> workinghourItems) {
        this.workinghourItems = workinghourItems;
    }
}
