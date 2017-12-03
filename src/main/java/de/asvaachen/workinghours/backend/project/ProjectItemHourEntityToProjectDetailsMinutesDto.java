package de.asvaachen.workinghours.backend.project;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectItemHourEntityToProjectDetailsMinutesDto implements Converter<ProjectItemHourEntity, ItemDetailsMinutesDto> {

    MemberEntityToMemberDtoConverter converter;

    public ProjectItemHourEntityToProjectDetailsMinutesDto(MemberEntityToMemberDtoConverter converter) {
        this.converter = converter;
    }

    @Override
    public ItemDetailsMinutesDto convert(ProjectItemHourEntity source) {
        ItemDetailsMinutesDto itemDetailsMinutesDto = new ItemDetailsMinutesDto();
        itemDetailsMinutesDto.setDuration(source.getDuration());
        itemDetailsMinutesDto.setMember(converter.convert(source.getMember()));
        return itemDetailsMinutesDto;
    }
}