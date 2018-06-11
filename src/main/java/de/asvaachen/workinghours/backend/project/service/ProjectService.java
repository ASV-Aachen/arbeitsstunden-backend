package de.asvaachen.workinghours.backend.project.service;

import de.asvaachen.workinghours.backend.project.converter.ProjectEntityToProjectDtoConverter;
import de.asvaachen.workinghours.backend.project.converter.ProjectEntityToProjectOverviewDtoConverter;
import de.asvaachen.workinghours.backend.project.model.ProjectDetailsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDetailsItemDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDurationsForYearsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectItemEntityToProjectDetailsItemDto;
import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import de.asvaachen.workinghours.backend.project.model.ProjectsTakelSummaryDto;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.MemberRepository;
import de.asvaachen.workinghours.backend.project.persistence.ProjectEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemRepository;
import de.asvaachen.workinghours.backend.project.persistence.ProjectRepository;
import de.asvaachen.workinghours.backend.projects.model.CurrentSeasonsDto;
import de.asvaachen.workinghours.backend.projects.model.ProjectDetailItemDto;
import de.asvaachen.workinghours.backend.projects.model.ProjectsDetailDto;
import de.asvaachen.workinghours.backend.projects.model.ProjectsOverviewDto;
import de.asvaachen.workinghours.backend.season.SeasonService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectItemRepository projectItemRepository;
    private MemberRepository memberRepository;

    private SeasonService seasonService;

    private ProjectEntityToProjectDtoConverter projectEntityToProjectDtoConverter;
    private ProjectEntityToProjectOverviewDtoConverter projectEntityToProjectOverviewDtoConverter;
    private ProjectItemEntityToProjectDetailsItemDto projectItemEntityToProjectDetailsItemDto;

    public ProjectService(ProjectRepository projectRepository, ProjectItemRepository projectItemRepository, MemberRepository memberRepository, SeasonService seasonService, ProjectEntityToProjectDtoConverter projectEntityToProjectDtoConverter, ProjectEntityToProjectOverviewDtoConverter projectEntityToProjectOverviewDtoConverter, ProjectItemEntityToProjectDetailsItemDto projectItemEntityToProjectDetailsItemDto) {
        this.projectRepository = projectRepository;
        this.projectItemRepository = projectItemRepository;
        this.memberRepository = memberRepository;
        this.seasonService = seasonService;
        this.projectEntityToProjectDtoConverter = projectEntityToProjectDtoConverter;
        this.projectEntityToProjectOverviewDtoConverter = projectEntityToProjectOverviewDtoConverter;
        this.projectItemEntityToProjectDetailsItemDto = projectItemEntityToProjectDetailsItemDto;
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAllByOrderByNameAsc().stream()
                .map(projectEntityToProjectDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public CurrentSeasonsDto getSeasons() {
        Integer activeYear = seasonService.getActiveSeason();

        CurrentSeasonsDto currentSeasons = new CurrentSeasonsDto();

        currentSeasons.setCurrentSeason(activeYear);
        currentSeasons.setSeasons(seasonService.getAllSeasons());

        return currentSeasons;
    }

    public List<ProjectOverviewDto> getSeasons(Integer year) {
        if (year == -1) {
            year = seasonService.getActiveSeason();
        }

        Integer seasonForYear = year;
        return projectRepository.findAllByOrderByNameAsc().stream()
                .filter(projectEntity -> projectEntity.getFirstSeason() <= seasonForYear)
                .filter(projectEntity -> projectEntity.getLastSeason() == null || projectEntity.getLastSeason() >= seasonForYear)
                .map(projectEntity -> projectEntityToProjectOverviewDtoConverter.convert(projectEntity, seasonForYear))
                .collect(Collectors.toList());
    }

    public ProjectDto createProject(ProjectEntity projectEntity) {
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return projectEntityToProjectDtoConverter.convert(savedProjectEntity);
    }

    public List<ProjectDetailsItemDto> getProjectDetails(Integer season, UUID projectId) {

        List<ProjectItemEntity> projectItems = projectItemRepository.findAllByProjectIdAndSeason(projectId, season);

        return projectItems.stream().map(projectItemEntityToProjectDetailsItemDto::convert).collect(Collectors.toList());
    }

    public List<ProjectDurationsForYearsDto> getProjectForYears(UUID projectId) {
        return projectItemRepository.accumlateYears(projectId).stream().map(objects -> new ProjectDurationsForYearsDto(((Integer) objects[0]), ((BigInteger) objects[1]).intValue())).collect(Collectors.toList());
    }

    public ProjectDetailsDto getProjectSummary(Integer season, UUID projectId) {

        ProjectDetailsDto projectDetailsDto = new ProjectDetailsDto();
        projectDetailsDto.setDescription(projectRepository.findOne(projectId).getDescription());

        Integer minutesProject = (Optional.ofNullable((BigInteger) projectItemRepository.minutesForProjectAndSeason(projectId, season)[0])).map(BigInteger::intValue).orElse(0);
        Integer minutesAllProjects = (Optional.ofNullable((BigInteger) projectItemRepository.minutesForOtherProjectsAndSeason(season)[0])).map(BigInteger::intValue).orElse(0);

        projectDetailsDto.setPercentage((float) minutesProject / minutesAllProjects);


        List<Object[]> maxMember = projectItemRepository.minutesForSeasonAndProject(season, projectId);
        if (maxMember.size() > 0) {
            MemberEntity member = memberRepository.findOne(UUID.fromString((String) maxMember.get(0)[0]));
            projectDetailsDto.setMaxSeasonMember(member.getFirstName() + " " + member.getLastName());
            projectDetailsDto.setMaxSeasonMinutes(((BigInteger) maxMember.get(0)[1]).intValue());
        }

        List<Object[]> maxOverall = projectItemRepository.minutesForProject(projectId);
        if (maxOverall.size() > 0) {
            MemberEntity member = memberRepository.findOne(UUID.fromString((String) maxOverall.get(0)[0]));
            projectDetailsDto.setMaxOverallMember(member.getFirstName() + " " + member.getLastName());
            projectDetailsDto.setMaxOverallMinutes(((BigInteger) maxOverall.get(0)[1]).intValue());
        }

        return projectDetailsDto;
    }

    public ProjectsTakelSummaryDto getProjectsTakelSummary() {

        return new ProjectsTakelSummaryDto();
    }

    public List<ProjectsOverviewDto> getOverview() {
        List<Object[]> projects = projectItemRepository.minutes();
        List<ProjectsOverviewDto> projectOverDto = projects.stream().map(row -> {
            ProjectsOverviewDto dto = new ProjectsOverviewDto();

            dto.setSeason(((Integer) row[0]));
            dto.setDuration(((BigInteger) row[1]).intValue());
            return dto;
        }).collect(Collectors.toList());


        projectOverDto.sort(Comparator.comparingInt(ProjectsOverviewDto::getSeason));

        return projectOverDto;
    }

    public ProjectsDetailDto getDetail(Integer year) {
        if (year == -1) {
            year = seasonService.getActiveSeason();
        }
        Integer seasonForYear = year;

        ProjectsDetailDto projectsDetailDto = new ProjectsDetailDto();
        projectsDetailDto.setNumberProjects(projectRepository.findAllByOrderByNameAsc().stream()
                .filter(projectEntity -> projectEntity.getFirstSeason() <= seasonForYear)
                .filter(projectEntity -> projectEntity.getLastSeason() == null || projectEntity.getLastSeason() >= seasonForYear)
                .collect(Collectors.toList())
                .size());

        List<Object[]> projects = projectItemRepository.minutesForSeasonTop5(year);
        List<ProjectDetailItemDto> projectOverDto = projects.stream().map(row -> {
            ProjectDetailItemDto dto = new ProjectDetailItemDto();

            UUID projectId = UUID.fromString((String) row[0]);
            List<Object[]> members = projectItemRepository.accumlateYears(projectId, seasonForYear);
            Object[] maxMember = Collections.max(members, Comparator.comparing(o -> (BigInteger) o[1]));
            dto.setNumberMembers(members.size());

            MemberEntity member = memberRepository.findOne(UUID.fromString((String) maxMember[0]));
            dto.setMaxMember(member.getFirstName() + " " + member.getLastName());
            dto.setMaxMemberMinutes(((BigInteger) maxMember[1]).intValue());

            dto.setDuration(((BigInteger) row[1]).intValue());
            dto.setName(projectRepository.findOne(projectId).getName());
            return dto;
        }).collect(Collectors.toList());
        projectsDetailDto.setProjects(projectOverDto);

        return projectsDetailDto;
    }
}


























