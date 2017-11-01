package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ProjectItemDto;
import org.springframework.stereotype.Service;

@Service
public class ProjectItemService {

    ProjectItemRepository projectItemRepository;
    ProjectItemDtoToProjectItemEntityConverter converter;

    public ProjectItemService(ProjectItemRepository projectItemRepository, ProjectItemDtoToProjectItemEntityConverter converter) {
        this.projectItemRepository = projectItemRepository;
        this.converter = converter;
    }

    public void saveProjectItem(ProjectItemDto projectItemDto) {
        ProjectItemEntity entity = converter.convert(projectItemDto);
        projectItemRepository.save(entity);
    }


}
