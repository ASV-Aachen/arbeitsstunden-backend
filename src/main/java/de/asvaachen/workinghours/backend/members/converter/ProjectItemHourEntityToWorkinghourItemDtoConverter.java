package de.asvaachen.workinghours.backend.members.converter;

import de.asvaachen.workinghours.backend.project.persistence.ProjectItemHourEntity;
import de.asvaachen.workinghours.backend.members.model.WorkinghourItemDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ProjectItemHourEntityToWorkinghourItemDtoConverter implements Converter<ProjectItemHourEntity, WorkinghourItemDto> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    @Override
    public WorkinghourItemDto convert(ProjectItemHourEntity source) {
        WorkinghourItemDto workinghourItemDto = new WorkinghourItemDto();
        workinghourItemDto.setId(source.getId().toString());
        workinghourItemDto.setDate(FORMATTER.format(source.getProjectItem().getDate()));
        workinghourItemDto.setTitle(source.getProjectItem().getTitle());
        workinghourItemDto.setProject(source.getProjectItem().getProject().getName());
        workinghourItemDto.setDuration(source.getDuration());
        return workinghourItemDto;
    }
}
