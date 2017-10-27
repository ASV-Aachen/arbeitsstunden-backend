package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectDtoToProjectEntityConverter implements Converter<ProjectDto, ProjectEntity> {

    @Override
    public ProjectEntity convert(ProjectDto source) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(UUID.fromString(source.getId()));
        projectEntity.setName(source.getName());
        projectEntity.setDescription(source.getDescription());
        projectEntity.setFirstSeason(source.getFirstSeason());
        projectEntity.setLastSeason(source.getLastSeason());
        return projectEntity;
    }
}
