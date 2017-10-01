package de.asvaachen.workinghours.backend.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, UUID> {

}
