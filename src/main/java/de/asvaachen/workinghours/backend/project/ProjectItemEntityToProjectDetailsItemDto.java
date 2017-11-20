package de.asvaachen.workinghours.backend.project;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ProjectItemEntityToProjectDetailsItemDto implements Converter<ProjectItemEntity, ProjectDetailsItemDto> {
    @Override
    public ProjectDetailsItemDto convert(ProjectItemEntity projectItem) {

        ProjectDetailsItemDto projectDetailsItemDto = new ProjectDetailsItemDto();
        projectDetailsItemDto.setId(projectItem.getId());
        projectDetailsItemDto.setTitle(projectItem.getTitle());
        projectDetailsItemDto.setDescription(projectItem.getDescription());
        projectDetailsItemDto.setDate(DateTimeFormatter.ISO_LOCAL_DATE.format(projectItem.getDate()));

        projectDetailsItemDto.setItemDetailsMinutes(projectItem.getHours());

        return projectDetailsItemDto;
    }
}