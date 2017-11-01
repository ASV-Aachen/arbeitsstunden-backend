package de.asvaachen.workinghours.backend.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectItemHourRepository extends CrudRepository<ProjectItemHourEntity, UUID> {
    List<ProjectItemHourEntity> findAllByMemberIdAndProjectItemSeason(UUID memberId, Integer season);
}