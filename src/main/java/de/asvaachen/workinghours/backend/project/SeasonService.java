package de.asvaachen.workinghours.backend.project;

import com.google.common.collect.Lists;
import de.asvaachen.workinghours.backend.season.NextSeasonDto;
import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeasonService {

    private SeasonRepository seasonRepository;
    private ReductionRepository reductionRepository;
    private SeasonEntityToSeasonDtoConverter converter;

    public SeasonService(SeasonRepository seasonRepository, ReductionRepository reductionRepository, SeasonEntityToSeasonDtoConverter converter) {
        this.seasonRepository = seasonRepository;
        this.reductionRepository = reductionRepository;
        this.converter = converter;
    }

    public SeasonDto createSeason(SeasonEntity seasonEntity) {
        SeasonEntity savedSeasonEntity = seasonRepository.save(seasonEntity);

        List<ReductionStatusEntity> newReductions = reductionRepository.findAllBySeason(seasonEntity.getYear()-1).stream().map(entity -> {
            ReductionStatusEntity reductionStatusEntity = new ReductionStatusEntity();

            reductionStatusEntity.setReduction(0);
            reductionStatusEntity.setSeason(seasonEntity.getYear());
            reductionStatusEntity.setStatus(entity.getStatus());
            reductionStatusEntity.setMember(entity.getMember());

            return reductionStatusEntity;
        }).collect(Collectors.toList());

        reductionRepository.save(newReductions);

        return converter.convert(savedSeasonEntity);
    }

    public List<SeasonDto> getAllSeasons() {
        return seasonRepository.findAllByOrderByYearDesc().stream().map(converter::convert).collect(Collectors.toList());
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

    public NextSeasonDto getNextSeason() {
        NextSeasonDto nextSeasonDto = new NextSeasonDto();

        nextSeasonDto.setNextSeason(seasonRepository.findTopByOrderByYearDesc().getYear() + 1);

        return nextSeasonDto;
    }

    public Integer getFirstSeason(MemberEntity member) {
        return reductionRepository.findTopByMemberOrderBySeasonAsc(member).getSeason();
    }
}