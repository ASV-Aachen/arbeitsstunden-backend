package de.asvaachen.workinghours.backend.season.converter;

import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SeasonEntityToSeasonDtoConverter implements Converter<SeasonEntity, SeasonDto> {

    @Override
    public SeasonDto convert(SeasonEntity seasonEntity) {
        SeasonDto seasonDto = new SeasonDto();

        seasonDto.setYear(seasonEntity.getYear());
        seasonDto.setObligatoryMinutes(seasonEntity.getObligatoryMinutes());

        Integer seasonYear = seasonEntity.getYear();
        Integer seasonStartYear = seasonYear - 1;
        seasonDto.setLabel(String.format("%d/%d", seasonStartYear, seasonYear));

        return seasonDto;
    }
}