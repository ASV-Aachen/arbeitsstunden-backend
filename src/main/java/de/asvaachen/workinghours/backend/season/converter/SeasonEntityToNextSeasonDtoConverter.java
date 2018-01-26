package de.asvaachen.workinghours.backend.season.converter;

import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity;
import de.asvaachen.workinghours.backend.season.model.NextSeasonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SeasonEntityToNextSeasonDtoConverter implements Converter<SeasonEntity, NextSeasonDto> {
    public NextSeasonDto convert(SeasonEntity seasonEntity) {
        NextSeasonDto nextSeasonDto = new NextSeasonDto();
        nextSeasonDto.setNextSeason(seasonEntity.getYear() + 1);
        return nextSeasonDto;
    }
}