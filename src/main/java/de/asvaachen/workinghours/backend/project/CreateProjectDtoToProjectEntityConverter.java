package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.CreateProjectDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectDtoToProjectEntityConverter implements Converter<CreateProjectDto, ProjectEntity> {

    @Override
    public ProjectEntity convert(CreateProjectDto source) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(source.getName());
        projectEntity.setDescription(source.getDescription());
        return projectEntity;
    }
}
