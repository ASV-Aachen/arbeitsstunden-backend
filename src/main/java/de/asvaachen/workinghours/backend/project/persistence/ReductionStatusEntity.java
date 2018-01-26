package de.asvaachen.workinghours.backend.project.persistence;

import de.asvaachen.workinghours.backend.member.model.AsvStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reduction")
public class ReductionStatusEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    private Integer season;
    private Integer reduction;

    @Enumerated(EnumType.STRING)
    private AsvStatus status;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private MemberEntity member;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getReduction() {
        return reduction;
    }

    public void setReduction(Integer reduction) {
        this.reduction = reduction;
    }

    public AsvStatus getStatus() {
        return status;
    }

    public void setStatus(AsvStatus status) {
        this.status = status;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}