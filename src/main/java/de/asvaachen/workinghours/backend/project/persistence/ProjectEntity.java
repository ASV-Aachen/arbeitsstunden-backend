package de.asvaachen.workinghours.backend.project.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    private UUID id;

    private String name;

    private String description;

    private Integer firstSeason;

    private Integer lastSeason;

    @OneToMany(mappedBy = "id")
    private List<ProjectItemEntity> projectItems;

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

    public List<ProjectItemEntity> getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(List<ProjectItemEntity> projectItems) {
        this.projectItems = projectItems;
    }
}
