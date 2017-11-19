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
    SeasonService seasonService;
    ProjectItemHourEntityToWorkinghourItemDtoConverter converter;

    UUID uuidRalf = UUID.fromString("e3d82ffe-9bdd-4381-94e8-6d329e868eab");
    Integer activeYear = 2017;

    public MembersService(ProjectItemHourRepository projectItemHourRepository, MemberRepository memberRepository, SeasonService seasonService, ProjectItemHourEntityToWorkinghourItemDtoConverter converter) {
        this.projectItemHourRepository = projectItemHourRepository;
        this.memberRepository = memberRepository;
        this.seasonService = seasonService;
        this.converter = converter;
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

        return memberDetailsDto;
    }
}