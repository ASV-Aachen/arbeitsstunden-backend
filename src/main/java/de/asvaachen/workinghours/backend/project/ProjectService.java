package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ActiveProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectEntityToProjectDtoConverter projectEntityToProjectDtoConverter;
    ProjectEntityToProjectOverviewDtoConverter projectEntityToProjectOverviewDtoConverter;

    public ProjectService(ProjectRepository projectRepository, ProjectEntityToProjectDtoConverter projectEntityToProjectDtoConverter, ProjectEntityToProjectOverviewDtoConverter projectEntityToProjectOverviewDtoConverter) {
        this.projectRepository = projectRepository;
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
        activeProjects.setSeasons(createWorkingHoursSeasonDto());

        return activeProjects;
    }

    private List<SeasonDto> createWorkingHoursSeasonDto() {
        List<SeasonDto> availableSeasons = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            int year = 2007 + i;
            int nextYear = year + 1;
            availableSeasons.add(new SeasonDto(year, String.format("%d/%d", year, nextYear)));
        }
        return availableSeasons;
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
