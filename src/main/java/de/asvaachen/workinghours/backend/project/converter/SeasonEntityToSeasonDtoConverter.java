package de.asvaachen.workinghours.backend.project.converter;

import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SeasonEntityToSeasonDtoConverter implements Converter<SeasonEntity, SeasonDto> {
    @Override
    public SeasonDto convert(SeasonEntity source) {
        SeasonDto seasonDto = new SeasonDto();
        seasonDto.setYear(source.getYear());
        seasonDto.setObligatoryMinutes(source.getObligatoryMinutes());
        int year = source.getYear();
        int startYear = year - 1;
        seasonDto.setLabel(String.format("%d/%d", startYear, year));
        return seasonDto;
    }
}