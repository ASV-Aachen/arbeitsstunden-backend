package de.asvaachen.workinghours.backend.members.service;

import de.asvaachen.workinghours.backend.member.model.MemberDetailsDto;
import de.asvaachen.workinghours.backend.member.model.MemberOverviewDto;
import de.asvaachen.workinghours.backend.member.model.MemberWorkinghoursDto;
import de.asvaachen.workinghours.backend.members.converter.ProjectItemHourEntityToWorkinghourItemDtoConverter;
import de.asvaachen.workinghours.backend.members.converter.ReductionStatusEntityToSeasonReductionDtoConverter;
import de.asvaachen.workinghours.backend.members.model.AsvStatus;
import de.asvaachen.workinghours.backend.members.model.MemberDistributionItemDto;
import de.asvaachen.workinghours.backend.members.model.MemberListItemDto;
import de.asvaachen.workinghours.backend.members.model.MembersSummaryDto;
import de.asvaachen.workinghours.backend.members.model.SeasonReductionDto;
import de.asvaachen.workinghours.backend.members.persistence.UserEntity;
import de.asvaachen.workinghours.backend.project.converter.MemberEntityToMemberDtoConverter;
import de.asvaachen.workinghours.backend.project.model.MemberDto;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberRepository;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemHourEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemHourRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionStatusEntity;
import de.asvaachen.workinghours.backend.projects.model.CurrentSeasonsDto;
import de.asvaachen.workinghours.backend.season.SeasonService;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private ProjectItemHourRepository projectItemHourRepository;
    private MemberRepository memberRepository;
    private ReductionRepository reductionRepository;
    private SeasonService seasonService;
    private UserService userService;
    private ProjectItemHourEntityToWorkinghourItemDtoConverter converter;
    private ReductionStatusEntityToSeasonReductionDtoConverter reductionStatusConverter;
    private MemberEntityToMemberDtoConverter memberEntityToMemberDtoConverter;

    public MemberService(ProjectItemHourRepository projectItemHourRepository, MemberRepository memberRepository, ReductionRepository reductionRepository, SeasonService seasonService, UserService userService, ProjectItemHourEntityToWorkinghourItemDtoConverter converter, ReductionStatusEntityToSeasonReductionDtoConverter reductionStatusConverter, MemberEntityToMemberDtoConverter memberEntityToMemberDtoConverter) {
        this.projectItemHourRepository = projectItemHourRepository;
        this.memberRepository = memberRepository;
        this.reductionRepository = reductionRepository;
        this.seasonService = seasonService;
        this.userService = userService;
        this.converter = converter;
        this.reductionStatusConverter = reductionStatusConverter;
        this.memberEntityToMemberDtoConverter = memberEntityToMemberDtoConverter;
    }

    public MemberWorkinghoursDto getMemberWorkinghours(MemberEntity memberEntity, Integer selectedSeason) {
        Integer activeSeason = seasonService.getActiveSeason();
        if (selectedSeason == 0) {
            selectedSeason = activeSeason;
        }

        Integer obligatoryForSeason = seasonService.getObligatoryMinutes(selectedSeason);

        MemberWorkinghoursDto memberWorkinghoursDto = new MemberWorkinghoursDto();
        memberWorkinghoursDto.setActiveSeason(activeSeason);
        memberWorkinghoursDto.setSelectedSeason(selectedSeason);
        memberWorkinghoursDto.setNeededMinutes(obligatoryForSeason);

        Integer firstSeason = seasonService.getFirstSeason(memberEntity);
        List<Integer> allSeasons = seasonService.getAllSeasons().stream().map(SeasonDto::getYear).filter(season -> season >= firstSeason).collect(Collectors.toList());
        memberWorkinghoursDto.setSeasons(seasonService.getSeasonsIn(allSeasons));

        List<ProjectItemHourEntity> allByMemberIdAndProjectItemSeason = projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(memberEntity.getId(), selectedSeason);
        memberWorkinghoursDto.setWorkinghours(allByMemberIdAndProjectItemSeason.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
        memberWorkinghoursDto.setWorkedMinutes(allByMemberIdAndProjectItemSeason.stream().mapToInt(ProjectItemHourEntity::getDuration).sum());

        return memberWorkinghoursDto;
    }

    public List<MemberOverviewDto> getMemberOverview(MemberEntity memberEntity) {

        Integer firstSeason = seasonService.getFirstSeason(memberEntity);
        List<Integer> allSeasons = seasonService.getAllSeasons().stream().map(SeasonDto::getYear).filter(season -> season >= firstSeason).collect(Collectors.toList());

        List<MemberOverviewDto> overviewItems = new ArrayList<>();
        for (Integer season : allSeasons) {

            List<ProjectItemHourEntity> projectItems = projectItemHourRepository.findAllByMemberIdAndProjectItemSeason(memberEntity.getId(), season);

            MemberOverviewDto memberOverviewDto = new MemberOverviewDto();

            Integer sumMinutes = projectItems.stream().map(ProjectItemHourEntity::getDuration).mapToInt(Integer::intValue).sum();
            SeasonDto seasonDto = seasonService.getSeason(season);

            memberOverviewDto.setSeason(seasonDto);
            memberOverviewDto.setMinutes(sumMinutes);
            overviewItems.add(memberOverviewDto);
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
        if (season == -1) {
            season = seasonService.getActiveSeason();
        }

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
        if (season == -1) {
            season = seasonService.getActiveSeason();
        }

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
        if (season == -1) {
            season = seasonService.getActiveSeason();
        }

        Integer obligatoryForSeason = seasonService.getObligatoryMinutes(season);

        MembersSummaryDto membersSummaryDto = new MembersSummaryDto();

        membersSummaryDto.setWorkedMinutesTotal(projectItemHourRepository.allMinutesForSeason(season));

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

    public List<MemberDto> getAllTakelMembers() {
        return userService.getAllTakelMembers().stream().map(memberEntityToMemberDtoConverter::convert).collect(Collectors.toList());
    }

    public List<MemberDto> getAllMembers() {
        return memberRepository.findAllByOrderByLastNameAsc().stream().map(memberEntityToMemberDtoConverter::convert).collect(Collectors.toList());
    }

    public MemberEntity getMember(UUID memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public CurrentSeasonsDto getSeasons() {
        Integer activeYear = seasonService.getActiveSeason();

        CurrentSeasonsDto currentSeasons = new CurrentSeasonsDto();

        currentSeasons.setCurrentSeason(activeYear);
        currentSeasons.setSeasons(seasonService.getAllSeasons());

        return currentSeasons;
    }

    public List<SeasonReductionDto> getSeasonsAndReductions(MemberEntity member) {
        List<ReductionStatusEntity> reductions = reductionRepository.findAllByMember(member);
        return reductions.stream()
                .map(reductionStatusConverter::convert)
                .sorted(Comparator.comparingInt(SeasonReductionDto::getYear))
                .collect(Collectors.toList());
    }

    public void updateSeasonsAndReductions(MemberEntity member, List<SeasonReductionDto> updatedSeasonsAndReductions) {
        List<ReductionStatusEntity> reductions = reductionRepository.findAllByMember(member);

        Map<Integer, SeasonReductionDto> seasonToReductionDto = updatedSeasonsAndReductions.stream()
                .collect(Collectors.toMap(SeasonReductionDto::getYear, Function.identity()));

        List<ReductionStatusEntity> updatedReductionEntities = reductions.stream()
                .peek(reductionStatusEntity -> {
                    SeasonReductionDto updatedSeasonReduction = seasonToReductionDto.get(reductionStatusEntity.getSeason());
                    reductionStatusEntity.setReduction(updatedSeasonReduction.getReduction());
                    reductionStatusEntity.setStatus(updatedSeasonReduction.getAsvStatus());
                })
                .collect(Collectors.toList());

        reductionRepository.saveAll(updatedReductionEntities);
    }
}
