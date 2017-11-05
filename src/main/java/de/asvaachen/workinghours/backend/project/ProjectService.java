package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ActiveProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    SeasonService seasonService;

    SeasonEntityToSeasonDtoConverter seasonEntityToSeasonDtoConverter;
    ProjectEntityToProjectDtoConverter projectEntityToProjectDtoConverter;
    ProjectEntityToProjectOverviewDtoConverter projectEntityToProjectOverviewDtoConverter;

    public ProjectService(ProjectRepository projectRepository, SeasonService seasonService, SeasonEntityToSeasonDtoConverter seasonEntityToSeasonDtoConverter, ProjectEntityToProjectDtoConverter projectEntityToProjectDtoConverter, ProjectEntityToProjectOverviewDtoConverter projectEntityToProjectOverviewDtoConverter) {
        this.projectRepository = projectRepository;
        this.seasonService = seasonService;
        this.seasonEntityToSeasonDtoConverter = seasonEntityToSeasonDtoConverter;
        this.projectEntityToProjectDtoConverter = projectEntityToProjectDtoConverter;
        this.projectEntityToProjectOverviewDtoConverter = projectEntityToProjectOverviewDtoConverter;
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAllByOrderByNameAsc().stream()
                .map(projectEntityToProjectDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public ActiveProjectsDto getActiveProjects() {
        Integer activeYear = 2017;

        ActiveProjectsDto activeProjects = new ActiveProjectsDto();

        List<ProjectOverviewDto> projects = projectRepository.findAllByLastSeasonNullOrderByNameAsc().stream()
                .map(projectEntity -> projectEntityToProjectOverviewDtoConverter.convert(projectEntity, activeYear))
                .collect(Collectors.toList());

        activeProjects.setProjects(projects);
        activeProjects.setActiveYear(activeYear);
        activeProjects.setSeasons(seasonService.getAllSeasons());

        return activeProjects;
    }

    public List<ProjectOverviewDto> getActiveProjects(Integer year) {
        return projectRepository.findAllByOrderByNameAsc().stream()
                .filter(projectEntity -> projectEntity.getFirstSeason() <= year)
                .filter(projectEntity -> projectEntity.getLastSeason() == null || projectEntity.getLastSeason() >= year)
                .map(projectEntity -> projectEntityToProjectOverviewDtoConverter.convert(projectEntity, year))
                .collect(Collectors.toList());
    }

    public ProjectDto createProject(ProjectEntity projectEntity) {
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return projectEntityToProjectDtoConverter.convert(savedProjectEntity);
    }
}