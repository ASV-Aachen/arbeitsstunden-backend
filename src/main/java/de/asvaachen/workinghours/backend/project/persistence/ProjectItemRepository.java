package de.asvaachen.workinghours.backend.project.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectItemRepository extends CrudRepository<ProjectItemEntity, UUID> {
    List<ProjectItemEntity> findAllByProjectIdAndSeason(UUID projectId, Integer season);

    @Query(value = "SELECT CAST(member_id as TEXT), sum(duration) FROM project_item i, project_item_hour h WHERE project_id=?1 AND h.project_item_id=i.id AND season=?2  GROUP BY member_id", nativeQuery = true)
    List<Object[]> accumlateYears(UUID projectId, Integer season);

    @Query(value = "SELECT season, sum(duration) FROM project_item i, project_item_hour h WHERE project_id=?1 AND h.project_item_id=i.id  GROUP BY season", nativeQuery = true)
    List<Object[]> accumlateYears(UUID projectId);

    @Query(value = "SELECT sum(duration) FROM project_item i, project_item_hour h WHERE project_id=?1 AND i.season=?2 AND h.project_item_id=i.id", nativeQuery = true)
    Object[] minutesForProjectAndSeason(UUID projectId, Integer season);

    @Query(value = "SELECT sum(duration) FROM project_item i, project_item_hour h WHERE i.season=?1 AND h.project_item_id=i.id", nativeQuery = true)
    Object[] minutesForOtherProjectsAndSeason(Integer season);

    @Query(value = "SELECT CAST(i.project_id as TEXT), sum(duration) FROM project_item i, project_item_hour h WHERE i.season=?1 AND h.project_item_id=i.id GROUP BY project_id", nativeQuery = true)
    List<Object[]> minutesForSeason(Integer season);

    @Query(value = "SELECT i.season, sum(duration) FROM project_item i, project_item_hour h WHERE h.project_item_id=i.id GROUP BY season", nativeQuery = true)
    List<Object[]> minutes();

    @Query(value = "SELECT CAST(project_id  as TEXT), sum(duration) as duration_sum FROM project_item i, project_item_hour h WHERE i.season=?1 AND h.project_item_id=i.id GROUP BY project_id ORDER BY duration_sum DESC LIMIT 5", nativeQuery = true)
    List<Object[]> minutesForSeasonTop5(Integer season);

    @Query(value = "SELECT CAST(member_id as TEXT), sum(duration) as duration_sum FROM project_item i, project_item_hour h WHERE i.season=?1 AND h.project_item_id=i.id AND project_id=?2 GROUP BY member_id ORDER BY duration_sum DESC LIMIT 1", nativeQuery = true)
    List<Object[]> minutesForSeasonAndProject(Integer season, UUID project_id);

    @Query(value = "SELECT CAST(member_id as TEXT), sum(duration) as duration_sum FROM project_item i, project_item_hour h WHERE h.project_item_id=i.id AND project_id=?1 GROUP BY member_id ORDER BY duration_sum DESC LIMIT 1", nativeQuery = true)
    List<Object[]> minutesForProject(UUID project_id);

}

