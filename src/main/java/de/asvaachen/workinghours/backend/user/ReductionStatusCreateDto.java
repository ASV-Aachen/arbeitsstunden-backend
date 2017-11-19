package de.asvaachen.workinghours.backend.user;

import java.util.Map;

public class ReductionStatusCreateDto {

    private String memberId;
    private Map<Integer, ReductionStatusDto> years;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Map<Integer, ReductionStatusDto> getYears() {
        return years;
    }

    public void setYears(Map<Integer, ReductionStatusDto> years) {
        this.years = years;
    }
}
