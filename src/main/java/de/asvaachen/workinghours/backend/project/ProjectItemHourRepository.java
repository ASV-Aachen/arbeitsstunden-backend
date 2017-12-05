package de.asvaachen.workinghours.backend.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectItemHourRepository extends CrudRepository<ProjectItemHourEntity, UUID> {
    List<ProjectItemHourEntity> findAllByMemberIdAndProjectItemSeason(UUID memberId, Integer season);


    @Query("select distinct i.projectItem.season from ProjectItemHourEntity i where i.member.id = ?1")
    List<Integer> findSeasonsByMemberId(UUID memberId);

    @Query(value = "SELECT CAST(m.id as TEXT), m.first_name, m.last_name, r.status, r.reduction, sum(h.duration) as workedMinutes FROM " +
            "member m, project_item_hour h, project_item i, reduction r " +
            "WHERE m.id=h.member_id AND h.project_item_id=i.id AND i.season=2017 AND i.season=r.season AND r.member_id=m.id " +
            "GROUP BY m.id, r.status, r.reduction", nativeQuery = true)
    List<Object[]> memberList();


    @Query(value = "SELECT r.status, sum(h.duration) as workedMinutes FROM " +
            "member m, project_item_hour h, project_item i, reduction r " +
            "WHERE m.id=h.member_id AND h.project_item_id=i.id AND i.season=2017 AND i.season=r.season AND r.member_id=m.id " +
            "GROUP BY r.status", nativeQuery = true)
    List<Object[]> memberDistribution();

    @Query(value = "SELECT sum(h.duration) as workedMinutes FROM project_item_hour h, project_item i WHERE h.project_item_id=i.id AND i.season=2017", nativeQuery = true)
    Integer allMinutesForSeason();

    @Query(value = "SELECT CAST(h.member_id as TEXT), sum(h.duration) as worked_minutes FROM project_item_hour h, project_item i WHERE h.project_item_id=i.id AND i.season=2017 GROUP BY h.member_id ORDER BY worked_minutes DESC Limit 1;", nativeQuery = true)
    List<Object[]> getKingForSeason();

}