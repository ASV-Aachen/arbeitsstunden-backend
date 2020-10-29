package de.asvaachen.workinghours.backend.project.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "project_item_hour")
public class ProjectItemHourEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "projectItemId", nullable = false)
    private ProjectItemEntity projectItem;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private MemberEntity member;

    private Integer duration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProjectItemEntity getProjectItem() {
        return projectItem;
    }

    public void setProjectItem(ProjectItemEntity projectItem) {
        this.projectItem = projectItem;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
