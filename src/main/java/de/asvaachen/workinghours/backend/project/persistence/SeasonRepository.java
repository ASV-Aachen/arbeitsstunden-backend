package de.asvaachen.workinghours.backend.project.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends CrudRepository<SeasonEntity, Integer> {
    List<SeasonEntity> findAllByOrderByYearDesc();

    Optional<SeasonEntity> findByYear(Integer season);

    SeasonEntity findTopByOrderByYearDesc();
}