package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.*;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MembersService {

    ProjectItemHourRepository projectItemHourRepository;
    MemberRepository memberRepository;
    ReductionRepository reductionRepository;
    SeasonService seasonService;
    ProjectItemHourEntityToWorkinghourItemDtoConverter converter;
    ReductionStatusEntityToSeasonReductionDtoConverter reductionStatusConverter;

    UUID uuidRalf = UUID.fromString("bf461fe5-3e65-44ef-a092-b6bc26e45edc");
    Integer activeYear = 2017;

    public MembersService(ProjectItemHourRepository projectItemHourRepository, MemberRepository memberRepository, ReductionRepository reductionRepository, SeasonService seasonService, ProjectItemHourEntityToWorkinghourItemDtoConverter converter, ReductionStatusEntityToSeasonReductionDtoConverter reductionStatusConverter) {
        this.projectItemHourRepository = projectItemHourRepository;
        this.memberRepository = memberRepository;
        this.reductionRepository = reductionRepository;
        this.seasonService = seasonService;
        this.converter = converter;
        this.reductionStatusConverter = reductionStatusConverter;
    }

    public List<WorkinghourItemDto> getActiveMemberWorkinghours(Integer year) {

        return projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(uuidRalf, year).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public ActiveMemberWorkinghoursDto getActiveMemberWorkinghours() {
        ActiveMemberWorkinghoursDto activeMemberWorkinghoursDto = new ActiveMemberWorkinghoursDto();
        activeMemberWorkinghoursDto.setActiveYear(activeYear);

        List<Integer> seasons = projectItemHourRepository.findSeasonsByMemberId(uuidRalf);
        activeMemberWorkinghoursDto.setSeasons(seasonService.getSeasonsIn(seasons));

        activeMemberWorkinghoursDto.setWorkinghours(projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(uuidRalf, activeYear).stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
        return activeMemberWorkinghoursDto;
    }

    public List<OverviewSeasonDto> getMemberOverview() {
        List<OverviewSeasonDto> overviewItems = new ArrayList<>();

        List<Integer> seasons = projectItemHourRepository.findSeasonsByMemberId(uuidRalf);
        Collections.sort(seasons);

        for (Integer season : seasons) {
            List<ProjectItemHourEntity> projectItems = projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(uuidRalf, season);

            OverviewSeasonDto overviewSeasonDto = new OverviewSeasonDto();

            Integer sumMinutes = projectItems.stream().map(ProjectItemHourEntity::getDuration).mapToInt(Integer::intValue).sum();
            SeasonDto seasonDto = seasonService.getSeason(season);

            overviewSeasonDto.setSeason(seasonDto);
            overviewSeasonDto.setMinutes(sumMinutes);
            overviewItems.add(overviewSeasonDto);
        }
        return overviewItems;
    }

    public MemberDetailsDto getMemberDetails() {
        MemberDetailsDto memberDetailsDto = new MemberDetailsDto();

        MemberEntity memberEntity = memberRepository.findOne(uuidRalf);

        memberDetailsDto.setFirstName(memberEntity.getFirstName());
        memberDetailsDto.setLastName(memberEntity.getLastName());
        memberDetailsDto.setEmail(Optional.of(memberEntity.getUser()).map(UserEntity::getEmail).orElse(null));

        List<ReductionStatusEntity> reductions = reductionRepository.findAllByMember(memberEntity);

        memberDetailsDto.setSeasonReduction(reductions.stream().map(reductionStatusConverter::convert).collect(Collectors.toList()));

        return memberDetailsDto;
    }
}