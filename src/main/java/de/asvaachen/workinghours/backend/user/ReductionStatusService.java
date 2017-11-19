package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberEntity;
import de.asvaachen.workinghours.backend.project.MemberRepository;
import de.asvaachen.workinghours.backend.project.ReductionRepository;
import de.asvaachen.workinghours.backend.project.ReductionStatusEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ReductionStatusService {

    ReductionRepository reductionRepository;
    MemberRepository memberRepository;

    public ReductionStatusService(ReductionRepository reductionRepository, MemberRepository memberRepository) {
        this.reductionRepository = reductionRepository;
        this.memberRepository = memberRepository;
    }

    public void create(String memberId, Map<Integer, ReductionStatusDto> years) {

        MemberEntity memberEntity = memberRepository.findOne(UUID.fromString(memberId));

        years.keySet().forEach(
                year -> {
                    ReductionStatusDto reductionStatusDto = years.get(year);

                    ReductionStatusEntity reductionStatus = new ReductionStatusEntity();
                    reductionStatus.setMember(memberEntity);
                    reductionStatus.setReduction(reductionStatusDto.getReduction());
                    reductionStatus.setSeason(year);
                    reductionStatus.setStatus(reductionStatusDto.getStatus());

                    reductionRepository.save(reductionStatus);
                }
        );
    }
}