package de.asvaachen.workinghours.backend.member.model;

public class ReductionStatusDto {

    private AsvStatus status;
    private Integer reduction;

    public AsvStatus getStatus() {
        return status;
    }

    public void setStatus(AsvStatus status) {
        this.status = status;
    }

    public Integer getReduction() {
        return reduction;
    }

    public void setReduction(Integer reduction) {
        this.reduction = reduction;
    }
}