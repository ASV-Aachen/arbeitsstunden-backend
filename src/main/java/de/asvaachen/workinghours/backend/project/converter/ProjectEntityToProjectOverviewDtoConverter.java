package de.asvaachen.workinghours.backend.project.converter;

import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import de.asvaachen.workinghours.backend.project.persistence.ProjectEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemHourEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectEntityToProjectOverviewDtoConverter {

    ProjectItemRepository projectItemRepository;

    public ProjectEntityToProjectOverviewDtoConverter(ProjectItemRepository projectItemRepository) {
        this.projectItemRepository = projectItemRepository;
    }

    public ProjectOverviewDto convert(ProjectEntity source, Integer season) {
        ProjectOverviewDto projectOverviewDto = new ProjectOverviewDto();

        projectOverviewDto.setId(source.getId().toString());
        projectOverviewDto.setName(source.getName());
        projectOverviewDto.setDuration(accumulateHours(source.getId(), season));

        return projectOverviewDto;
    }

    private Integer accumulateHours(UUID projectId, Integer year) {
        return projectItemRepository.findAllByProjectIdAndSeason(projectId, year).stream().mapToInt(
                projectItemEntity -> projectItemEntity.getHours().stream()
                        .mapToInt(ProjectItemHourEntity::getDuration)
                        .sum()
        ).sum();
    }
}