package de.asvaachen.workinghours.backend.member.service;

import de.asvaachen.workinghours.backend.project.converter.MemberEntityToMemberDtoConverter;
import de.asvaachen.workinghours.backend.project.model.MemberDto;
import de.asvaachen.workinghours.backend.project.persistence.*;
import de.asvaachen.workinghours.backend.project.service.SeasonService;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import de.asvaachen.workinghours.backend.member.converter.ProjectItemHourEntityToWorkinghourItemDtoConverter;
import de.asvaachen.workinghours.backend.member.converter.ReductionStatusEntityToSeasonReductionDtoConverter;
import de.asvaachen.workinghours.backend.member.model.*;
import de.asvaachen.workinghours.backend.member.persistence.UserEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MembersService {

    private ProjectItemHourRepository projectItemHourRepository;
    private MemberRepository memberRepository;
    private ReductionRepository reductionRepository;
    private SeasonService seasonService;
    private ProjectItemHourEntityToWorkinghourItemDtoConverter converter;
    private ReductionStatusEntityToSeasonReductionDtoConverter reductionStatusConverter;
    private MemberEntityToMemberDtoConverter memberEntityToMemberDtoConverter;

    public MembersService(ProjectItemHourRepository projectItemHourRepository, MemberRepository memberRepository, ReductionRepository reductionRepository, SeasonService seasonService, ProjectItemHourEntityToWorkinghourItemDtoConverter converter, ReductionStatusEntityToSeasonReductionDtoConverter reductionStatusConverter, MemberEntityToMemberDtoConverter memberEntityToMemberDtoConverter) {
        this.projectItemHourRepository = projectItemHourRepository;
        this.memberRepository = memberRepository;
        this.reductionRepository = reductionRepository;
        this.seasonService = seasonService;
        this.converter = converter;
        this.reductionStatusConverter = reductionStatusConverter;
        this.memberEntityToMemberDtoConverter = memberEntityToMemberDtoConverter;
    }

    public MemberWorkinghoursDto getActiveMemberWorkinghours(MemberEntity memberEntity, Integer year) {
        Integer obligatoryForSeason = seasonService.getObligatoryMinutes(year);

        MemberWorkinghoursDto memberWorkinghoursDto = new MemberWorkinghoursDto();

        List<ProjectItemHourEntity> allByMemberIdAndProjectItemSeason = projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(memberEntity.getId(), year);
        memberWorkinghoursDto.setWorkinghourItems(allByMemberIdAndProjectItemSeason.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
        memberWorkinghoursDto.setNeededMinutes(obligatoryForSeason);
        memberWorkinghoursDto.setWorkedMinutes(allByMemberIdAndProjectItemSeason.stream().mapToInt(ProjectItemHourEntity::getDuration).sum());

        return memberWorkinghoursDto;
    }

    public ActiveMemberWorkinghoursDto getActiveMemberWorkinghours(MemberEntity memberEntity) {
        Integer activeSeason = seasonService.getActiveSeason();

        Integer obligatoryForSeason = seasonService.getObligatoryMinutes(activeSeason);

        ActiveMemberWorkinghoursDto activeMemberWorkinghoursDto = new ActiveMemberWorkinghoursDto();
        activeMemberWorkinghoursDto.setActiveYear(activeSeason);

        Integer firstSeason = seasonService.getFirstSeason(memberEntity);
        List<Integer> allSeasons = seasonService.getAllSeasons().stream().map(seasonDto -> seasonDto.getYear()).filter(season -> season >= firstSeason).collect(Collectors.toList());

        activeMemberWorkinghoursDto.setSeasons(seasonService.getSeasonsIn(allSeasons));

        List<ProjectItemHourEntity> allByMemberIdAndProjectItemSeason = projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(memberEntity.getId(), seasonService.getActiveSeason());
        activeMemberWorkinghoursDto.setWorkinghours(allByMemberIdAndProjectItemSeason.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));

        activeMemberWorkinghoursDto.setNeededMinutes(obligatoryForSeason);
        activeMemberWorkinghoursDto.setWorkedMinutes(allByMemberIdAndProjectItemSeason.stream().mapToInt(ProjectItemHourEntity::getDuration).sum());

        return activeMemberWorkinghoursDto;
    }

    public List<OverviewSeasonDto> getMemberOverview(MemberEntity memberEntity) {

        Integer firstSeason = seasonService.getFirstSeason(memberEntity);
        List<Integer> allSeasons = seasonService.getAllSeasons().stream().map(seasonDto -> seasonDto.getYear()).filter(season -> season >= firstSeason).collect(Collectors.toList());

        List<OverviewSeasonDto> overviewItems = new ArrayList<>();
        for (Integer season : allSeasons) {

            List<ProjectItemHourEntity> projectItems = projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(memberEntity.getId(), season);

            OverviewSeasonDto overviewSeasonDto = new OverviewSeasonDto();

            Integer sumMinutes = projectItems.stream().map(ProjectItemHourEntity::getDuration).mapToInt(Integer::intValue).sum();
            SeasonDto seasonDto = seasonService.getSeason(season);

            overviewSeasonDto.setSeason(seasonDto);
            overviewSeasonDto.setMinutes(sumMinutes);
            overviewItems.add(overviewSeasonDto);
        }
        return overviewItems;
    }

    public MemberDetailsDto getMemberDetails(MemberEntity memberEntity) {
        MemberDetailsDto memberDetailsDto = new MemberDetailsDto();

        memberDetailsDto.setFirstName(memberEntity.getFirstName());
        memberDetailsDto.setLastName(memberEntity.getLastName());
        memberDetailsDto.setEmail(Optional.of(memberEntity.getUser()).map(UserEntity::getEmail).orElse(null));

        List<ReductionStatusEntity> reductions = reductionRepository.findAllByMember(memberEntity);

        memberDetailsDto.setSeasonReduction(reductions.stream().map(reductionStatusConverter::convert).collect(Collectors.toList()));

        return memberDetailsDto;
    }

    private Integer getObligatoryMinutes(Integer obligatoryMinutes, AsvStatus status) {

        if (status == AsvStatus.INACTIVE || status == AsvStatus.OLD_MAN) {
            return 0;
        } else {
            return obligatoryMinutes;
        }
    }

    public List<MemberListItemDto> getMemberList(Integer season) {

        Integer obligatoryForSeason = seasonService.getObligatoryMinutes(seasonService.getActiveSeason());

        List<Object[]> memberOverviewItems = projectItemHourRepository.memberList(season);

        return memberOverviewItems.stream().map(objects -> {


            UUID memberId = UUID.fromString((String) objects[0]);
            String firstName = (String) objects[1];
            String lastName = (String) objects[2];
            AsvStatus status = AsvStatus.valueOf((String) objects[3]);
            Integer reduction = (Integer) objects[4];
            Integer workedMinutes = ((BigInteger) objects[5]).intValue();

            MemberListItemDto memberListItemDto = new MemberListItemDto();
            memberListItemDto.setMemberId(memberId);
            memberListItemDto.setFirstName(firstName);
            memberListItemDto.setLastName(lastName);
            memberListItemDto.setStatus(status);
            memberListItemDto.setWorkedMinutes(workedMinutes);
            memberListItemDto.setNeededMinutes(getObligatoryMinutes(obligatoryForSeason, status) - reduction);
            memberListItemDto.setTodoMinutes(getObligatoryMinutes(obligatoryForSeason, status) - reduction - workedMinutes);
            return memberListItemDto;
        }).collect(Collectors.toList());
    }

    public List<MemberDistributionItemDto> getMemberDistribution(Integer season) {
        return projectItemHourRepository.memberDistribution(season).stream().map(objects -> {

            AsvStatus status = AsvStatus.valueOf((String) objects[0]);
            Integer minutes = ((BigInteger) objects[1]).intValue();

            MemberDistributionItemDto memberDistributionItemDto = new MemberDistributionItemDto();
            memberDistributionItemDto.setLabel(status.toString());
            memberDistributionItemDto.setMinutes(minutes);
            return memberDistributionItemDto;
        }).collect(Collectors.toList());
    }

    public MembersSummaryDto getMembersSummary(Integer season) {
        Integer obligatoryForSeason = seasonService.getObligatoryMinutes(season);

        MembersSummaryDto membersSummaryDto = new MembersSummaryDto();

        membersSummaryDto.setWorkedMinutesTotal(projectItemHourRepository.allMinutesForSeason(season));

        List<Object[]> objects = projectItemHourRepository.getKingForSeason(season);
        UUID memberId = UUID.fromString((String) objects.get(0)[0]);
        Integer workedMinutes = ((BigInteger) objects.get(0)[1]).intValue();
        membersSummaryDto.setKingMember(memberEntityToMemberDtoConverter.convert(memberRepository.findOne(memberId)));
        membersSummaryDto.setKingMinutes(workedMinutes);


        List<Object[]> members = projectItemHourRepository.memberList(season);
        Integer numSailingAllowed = 0;
        Integer numSailingNotAllowed = 0;

        for (Object[] obj : members) {

            AsvStatus status = AsvStatus.valueOf((String) obj[3]);
            Integer reduction = (Integer) obj[4];
            Integer workedMins = ((BigInteger) obj[5]).intValue();

            Integer neededMins = getObligatoryMinutes(obligatoryForSeason, status) - reduction - workedMins;

            if (neededMins <= 0) {
                numSailingAllowed++;
            } else {
                numSailingNotAllowed++;
            }
        }

        membersSummaryDto.setNumMembersSailingAllowed(numSailingAllowed);
        membersSummaryDto.setNumMembersSailingNotAllowed(numSailingNotAllowed);


        return membersSummaryDto;
    }

    public List<MemberDto> getAllMembers() {
        return memberRepository.findAllByOrderByLastNameAsc().stream().map(memberEntityToMemberDtoConverter::convert).collect(Collectors.toList());
    }
}