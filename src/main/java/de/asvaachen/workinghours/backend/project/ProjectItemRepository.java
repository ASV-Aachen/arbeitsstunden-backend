package de.asvaachen.workinghours.backend.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectItemRepository extends CrudRepository<ProjectItemEntity, UUID> {
    List<ProjectItemEntity> findAllByProjectIdAndSeason(UUID projectId, Integer season);

    @Query(value = "SELECT season, sum(duration) FROM project_item i, project_item_hour h WHERE project_id=?1 AND h.project_item_id=i.id  GROUP BY season", nativeQuery = true)
    List<Object[]> accumlateYears(UUID projectId);


    @Query(value = "SELECT sum(duration) FROM project_item i, project_item_hour h WHERE project_id=?1 AND i.season=?2 AND h.project_item_id=i.id", nativeQuery = true)
    Object[] minutesForProjectAndSeason(UUID projectId, Integer season);

    @Query(value = "SELECT sum(duration) FROM project_item i, project_item_hour h WHERE i.season=?1 AND h.project_item_id=i.id", nativeQuery = true)
    Object[] minutesForOtherProjectsAndSeason(Integer season);
}

