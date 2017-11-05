package de.asvaachen.workinghours.backend.season;

import de.asvaachen.workinghours.backend.project.SeasonEntity;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SeasonDtoToSeasonEntityConverter implements Converter<SeasonDto, SeasonEntity> {

    @Override
    public SeasonEntity convert(SeasonDto source) {
        SeasonEntity entity = new SeasonEntity();
        entity.setYear(source.getYear());
        entity.setObligatoryMinutes(source.getObligatoryMinutes());
        return entity;
    }
}
