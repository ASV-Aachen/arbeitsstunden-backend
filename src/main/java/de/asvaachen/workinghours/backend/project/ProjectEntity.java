package de.asvaachen.workinghours.backend.project;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    private String name;

    private String description;

    private Integer firstSeason;

    private Integer lastSeason;

    @ManyToMany
    private Map<Integer, ProjectItemEntity> seasons;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFirstSeason() {
        return firstSeason;
    }

    public void setFirstSeason(Integer firstSeason) {
        this.firstSeason = firstSeason;
    }

    public Integer getLastSeason() {
        return lastSeason;
    }

    public void setLastSeason(Integer lastSeason) {
        this.lastSeason = lastSeason;
    }

    public Map<Integer, ProjectItemEntity> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<Integer, ProjectItemEntity> seasons) {
        this.seasons = seasons;
    }
}
