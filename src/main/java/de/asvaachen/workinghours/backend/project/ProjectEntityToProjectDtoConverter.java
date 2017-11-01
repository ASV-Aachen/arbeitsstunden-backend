package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectEntityToProjectDtoConverter implements Converter<ProjectEntity, ProjectDto> {

    @Override
    public ProjectDto convert(ProjectEntity source) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(source.getId().toString());
        projectDto.setName(source.getName());
        projectDto.setDescription(source.getDescription());
        projectDto.setFirstSeason(source.getFirstSeason());
        projectDto.setLastSeason(source.getLastSeason());
        return projectDto;
    }
}
