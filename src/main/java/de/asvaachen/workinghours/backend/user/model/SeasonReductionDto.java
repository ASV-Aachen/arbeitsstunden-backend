package de.asvaachen.workinghours.backend.user.model;

public class SeasonReductionDto {

    private Integer year;
    private Integer reduction;

    private AsvStatus asvStatus;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getReduction() {
        return reduction;
    }

    public void setReduction(Integer reduction) {
        this.reduction = reduction;
    }

    public AsvStatus getAsvStatus() {
        return asvStatus;
    }

    public void setAsvStatus(AsvStatus asvStatus) {
        this.asvStatus = asvStatus;
    }
}
