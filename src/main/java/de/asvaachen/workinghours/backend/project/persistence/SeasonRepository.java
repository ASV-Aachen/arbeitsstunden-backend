package de.asvaachen.workinghours.backend.project.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonRepository extends CrudRepository<SeasonEntity, Integer> {
    List<SeasonEntity> findAllByOrderByYearDesc();

    SeasonEntity findByYear(Integer season);

    SeasonEntity findTopByOrderByYearDesc();
}