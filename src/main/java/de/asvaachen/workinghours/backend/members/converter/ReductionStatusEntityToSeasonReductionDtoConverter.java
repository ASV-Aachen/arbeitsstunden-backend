package de.asvaachen.workinghours.backend.members.converter;

import de.asvaachen.workinghours.backend.project.persistence.ReductionStatusEntity;
import de.asvaachen.workinghours.backend.members.model.SeasonReductionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReductionStatusEntityToSeasonReductionDtoConverter implements Converter<ReductionStatusEntity, SeasonReductionDto> {

    @Override
    public SeasonReductionDto convert(ReductionStatusEntity entity) {
        SeasonReductionDto seasonReductionDto = new SeasonReductionDto();

        seasonReductionDto.setYear(entity.getSeason());
        seasonReductionDto.setAsvStatus(entity.getStatus());
        seasonReductionDto.setReduction(entity.getReduction());

        return seasonReductionDto;
    }
}