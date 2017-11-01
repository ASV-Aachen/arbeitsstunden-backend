package de.asvaachen.workinghours.backend.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectItemRepository extends CrudRepository<ProjectItemEntity, UUID> {

}