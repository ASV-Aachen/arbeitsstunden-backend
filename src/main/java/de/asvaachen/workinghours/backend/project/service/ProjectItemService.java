package de.asvaachen.workinghours.backend.project.service;

import de.asvaachen.workinghours.backend.project.converter.ProjectItemDtoToProjectItemEntityConverter;
import de.asvaachen.workinghours.backend.project.model.ProjectItemDto;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemEntity;
import de.asvaachen.workinghours.backend.project.persistence.ProjectItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectItemService {

    private ProjectItemRepository projectItemRepository;
    private ProjectItemDtoToProjectItemEntityConverter converter;

    public ProjectItemService(ProjectItemRepository projectItemRepository, ProjectItemDtoToProjectItemEntityConverter converter) {
        this.projectItemRepository = projectItemRepository;
        this.converter = converter;
    }

    public void saveProjectItem(ProjectItemDto projectItemDto) {
        ProjectItemEntity entity = converter.convert(projectItemDto);
        projectItemRepository.save(entity);
    }


}
