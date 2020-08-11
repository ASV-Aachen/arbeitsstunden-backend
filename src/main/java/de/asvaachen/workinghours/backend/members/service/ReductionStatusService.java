package de.asvaachen.workinghours.backend.members.service;

import de.asvaachen.workinghours.backend.members.model.AsvStatus;
import de.asvaachen.workinghours.backend.members.model.ReductionStatusDto;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionStatusEntity;
import de.asvaachen.workinghours.backend.season.SeasonService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class ReductionStatusService {

    private final ReductionRepository reductionRepository;
    private final MemberRepository memberRepository;
    private final SeasonService seasonService;

    public ReductionStatusService(ReductionRepository reductionRepository, MemberRepository memberRepository, SeasonService seasonService) {
        this.reductionRepository = reductionRepository;
        this.memberRepository = memberRepository;
        this.seasonService = seasonService;
    }

    public void create(String memberId, Map<Integer, ReductionStatusDto> years) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(UUID.fromString(memberId));

        years.keySet().forEach(
                year -> {
                    ReductionStatusDto reductionStatusDto = years.get(year);

                    ReductionStatusEntity reductionStatus = new ReductionStatusEntity();
                    reductionStatus.setMember(memberEntity.orElse(null));
                    reductionStatus.setReduction(reductionStatusDto.getReduction());
                    reductionStatus.setSeason(year);
                    reductionStatus.setStatus(reductionStatusDto.getStatus());

                    reductionRepository.save(reductionStatus);
                }
        );
    }

    public void createInitial(MemberEntity member, Integer firstSeason, AsvStatus asvStatus) {
        Integer currentSeason = seasonService.getMaxSeason();

        IntStream.range(firstSeason, currentSeason + 1).forEach(
                season -> {
                    ReductionStatusEntity reductionStatusEntity = new ReductionStatusEntity();
                    reductionStatusEntity.setMember(member);
                    reductionStatusEntity.setSeason(season);
                    reductionStatusEntity.setStatus(asvStatus);
                    reductionStatusEntity.setReduction(0);
                    reductionRepository.save(reductionStatusEntity);
                }
        );
    }
}