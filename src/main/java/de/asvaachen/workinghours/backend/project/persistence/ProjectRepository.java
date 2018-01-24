package de.asvaachen.workinghours.backend.project.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, UUID> {

    List<ProjectEntity> findAllByOrderByNameAsc();

    List<ProjectEntity> findAllByLastSeasonNullOrderByNameAsc();
}