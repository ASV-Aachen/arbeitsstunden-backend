package de.asvaachen.workinghours.backend.project;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectDetailsDtoConverter {

    private ProjectItemEntityToProjectDetailsItemDto projectItemEntityToProjectDetailsItemDto;

    public ProjectDetailsDtoConverter(ProjectItemEntityToProjectDetailsItemDto projectItemEntityToProjectDetailsItemDto) {
        this.projectItemEntityToProjectDetailsItemDto = projectItemEntityToProjectDetailsItemDto;
    }

    public ProjectDetailsDto convert(ProjectEntity projectEntity, List<ProjectItemEntity> projectItems) {
        ProjectDetailsDto projectDetailsDto = new ProjectDetailsDto();

        projectDetailsDto.setName(projectEntity.getName());
        projectDetailsDto.setDescription(projectEntity.getDescription());
        projectDetailsDto.setProjectItems(projectItems.stream().map(projectItemEntityToProjectDetailsItemDto::convert).collect(Collectors.toList()));

        return projectDetailsDto;
    }
}