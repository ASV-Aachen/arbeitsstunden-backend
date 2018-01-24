package de.asvaachen.workinghours.backend.user.service;

import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionStatusEntity;
import de.asvaachen.workinghours.backend.project.service.SeasonService;
import de.asvaachen.workinghours.backend.user.model.AsvStatus;
import de.asvaachen.workinghours.backend.user.model.ReductionStatusDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class ReductionStatusService {

    private ReductionRepository reductionRepository;
    private MemberRepository memberRepository;
    private SeasonService seasonService;

    public ReductionStatusService(ReductionRepository reductionRepository, MemberRepository memberRepository, SeasonService seasonService) {
        this.reductionRepository = reductionRepository;
        this.memberRepository = memberRepository;
        this.seasonService = seasonService;
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