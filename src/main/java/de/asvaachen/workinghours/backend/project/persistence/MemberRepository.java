package de.asvaachen.workinghours.backend.project.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, UUID> {

    List<MemberEntity> findAllByOrderByLastNameAsc();

}