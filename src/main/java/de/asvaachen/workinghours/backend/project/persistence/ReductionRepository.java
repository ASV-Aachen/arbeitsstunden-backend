package de.asvaachen.workinghours.backend.project.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReductionRepository extends CrudRepository<ReductionStatusEntity, UUID> {

    List<ReductionStatusEntity> findAllByMember(MemberEntity memberEntity);

    List<ReductionStatusEntity> findAllBySeason(Integer season);

    ReductionStatusEntity findTopByMemberOrderBySeasonAsc(MemberEntity memberEntity);
}