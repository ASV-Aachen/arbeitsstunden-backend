package de.asvaachen.workinghours.backend.project;

import com.google.common.collect.Lists;
import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeasonService {

    SeasonRepository seasonRepository;
    SeasonEntityToSeasonDtoConverter converter;

    public SeasonService(SeasonRepository seasonRepository, SeasonEntityToSeasonDtoConverter converter) {
        this.seasonRepository = seasonRepository;
        this.converter = converter;
    }

    public SeasonDto createSeason(SeasonEntity seasonEntity) {
        SeasonEntity savedSeasonEntity = seasonRepository.save(seasonEntity);
        return converter.convert(savedSeasonEntity);
    }

    public List<SeasonDto> getAllSeasons() {
        return seasonRepository.findAllByOrderByYearAsc().stream().map(converter::convert).collect(Collectors.toList());
    }

    public List<SeasonDto> getSeasonsIn(List<Integer> seasons) {
        return Lists.newArrayList(seasonRepository.findAll(seasons)).stream().map(converter::convert).collect(Collectors.toList());
    }

    public SeasonDto getSeason(Integer season) {
        return converter.convert(seasonRepository.findOne(season));
    }

    public Integer getObligatoryMinutes(Integer season) {
        return seasonRepository.findByYear(season).getObligatoryMinutes();
    }

    public AvailableSeasonsDto getAvailableSeasons() {
        Integer activeYear = 2017; //TODO: Programmatically we switch to a new workinghour season at 01.11.X to 31.10.X+1.

        AvailableSeasonsDto availableSeasonsDto = new AvailableSeasonsDto();

        availableSeasonsDto.setActiveSeason(activeYear);
        availableSeasonsDto.setSeasons(getAllSeasons());

        return availableSeasonsDto;
    }
}