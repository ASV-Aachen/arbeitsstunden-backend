package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.ProjectItemHourEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectItemHourEntityToWorkinghourItemDtoConverter implements Converter<ProjectItemHourEntity, WorkinghourItemDto> {

    @Override
    public WorkinghourItemDto convert(ProjectItemHourEntity source) {
        WorkinghourItemDto workinghourItemDto = new WorkinghourItemDto();
        //workinghourItemDto.setDate(source.getProjectItem().getDate());
        workinghourItemDto.setTitle(source.getProjectItem().getTitle());
        workinghourItemDto.setProject(source.getProjectItem().getProject().getName());
        workinghourItemDto.setDuration(source.getDuration());
        return workinghourItemDto;
    }
}
