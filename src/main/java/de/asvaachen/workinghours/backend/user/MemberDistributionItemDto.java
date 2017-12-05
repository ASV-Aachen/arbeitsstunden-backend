package de.asvaachen.workinghours.backend.user;

public class MemberDistributionItemDto {

    private String label;
    private Integer minutes;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
