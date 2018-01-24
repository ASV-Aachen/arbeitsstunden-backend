package de.asvaachen.workinghours.backend.project.model;

import com.google.common.base.Strings;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemHourEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class ProjectItemEntityToProjectDetailsItemDto implements Converter<ProjectItemEntity, ProjectDetailsItemDto> {

    private ProjectItemHourEntityToProjectDetailsMinutesDto converter;

    public ProjectItemEntityToProjectDetailsItemDto(ProjectItemHourEntityToProjectDetailsMinutesDto converter) {
        this.converter = converter;
    }

    @Override
    public ProjectDetailsItemDto convert(ProjectItemEntity projectItem) {

        ProjectDetailsItemDto projectDetailsItemDto = new ProjectDetailsItemDto();
        projectDetailsItemDto.setId(projectItem.getId());
        projectDetailsItemDto.setTitle(projectItem.getTitle());
        projectDetailsItemDto.setDescription(Strings.isNullOrEmpty(projectItem.getDescription()) ? "keine Beschreibung" : projectItem.getDescription());
        projectDetailsItemDto.setDate(DateTimeFormatter.ISO_LOCAL_DATE.format(projectItem.getDate()));

        projectDetailsItemDto.setMinutes(projectItem.getHours().stream().map(converter::convert).collect(Collectors.toList()));
        projectDetailsItemDto.setDuration(projectItem.getHours().stream().mapToInt(ProjectItemHourEntity::getDuration).sum());

        return projectDetailsItemDto;
    }
}