package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.AktiveProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectEntityToProjectDtoConverter converter;

    public ProjectService(ProjectRepository projectRepository, ProjectEntityToProjectDtoConverter converter) {
        this.projectRepository = projectRepository;
        this.converter = converter;
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAllByOrderByNameAsc().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public AktiveProjectsDto getActiveProjects() {
        AktiveProjectsDto activeProjects = new AktiveProjectsDto();

        List<ProjectDto> projects = projectRepository.findAllByLastSeasonNullOrderByNameAsc().stream()
                .map(converter::convert)
                .collect(Collectors.toList());

        activeProjects.setProjects(projects);
        activeProjects.setActiveYear(2017);
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

    public List<ProjectDto> getActiveProjects(Integer year) {
        return projectRepository.findAllByOrderByNameAsc().stream()
                .filter(projectEntity -> projectEntity.getFirstSeason() <= year)
                .filter(projectEntity -> projectEntity.getLastSeason() == null || projectEntity.getLastSeason() >= year)
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public ProjectDto createProject(ProjectEntity projectEntity) {
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return converter.convert(savedProjectEntity);
    }
}
