package de.asvaachen.workinghours.backend.season;

import com.google.common.collect.Lists;
import de.asvaachen.workinghours.backend.project.persistence.MemberEntity;
import de.asvaachen.workinghours.backend.project.persistence.ReductionRepository;
import de.asvaachen.workinghours.backend.project.persistence.ReductionStatusEntity;
import de.asvaachen.workinghours.backend.project.persistence.SeasonEntity;
import de.asvaachen.workinghours.backend.project.persistence.SeasonRepository;
import de.asvaachen.workinghours.backend.project.service.ProjectService;
import de.asvaachen.workinghours.backend.season.converter.SeasonEntityToNextSeasonDtoConverter;
import de.asvaachen.workinghours.backend.season.converter.SeasonEntityToSeasonDtoConverter;
import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.NextSeasonDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final ReductionRepository reductionRepository;
    private final SeasonEntityToSeasonDtoConverter seasonEntityConverter;
    private final SeasonEntityToNextSeasonDtoConverter seasonEntityToNextSeasonDtoConverter;

    public SeasonService(SeasonRepository seasonRepository, ReductionRepository reductionRepository, SeasonEntityToSeasonDtoConverter seasonEntityConverter, SeasonEntityToNextSeasonDtoConverter seasonEntityToNextSeasonDtoConverter) {
        this.seasonRepository = seasonRepository;
        this.reductionRepository = reductionRepository;
        this.seasonEntityConverter = seasonEntityConverter;
        this.seasonEntityToNextSeasonDtoConverter = seasonEntityToNextSeasonDtoConverter;
    }

    public SeasonDto createSeason(SeasonEntity seasonEntity) {
        SeasonEntity savedSeasonEntity = seasonRepository.save(seasonEntity);

        List<ReductionStatusEntity> newReductions = reductionRepository.findAllBySeason(seasonEntity.getYear() - 1).stream().map(entity -> {
            ReductionStatusEntity reductionStatusEntity = new ReductionStatusEntity();

            reductionStatusEntity.setReduction(0);
            reductionStatusEntity.setSeason(seasonEntity.getYear());
            reductionStatusEntity.setStatus(entity.getStatus());
            reductionStatusEntity.setMember(entity.getMember());

            return reductionStatusEntity;
        }).collect(Collectors.toList());
        reductionRepository.saveAll(newReductions);

        return seasonEntityConverter.convert(savedSeasonEntity);
    }

    public List<SeasonDto> getAllSeasons() {
        return seasonRepository.findAllByOrderByYearDesc().stream().map(seasonEntityConverter::convert).collect(Collectors.toList());
    }

    public List<SeasonDto> getSeasonsIn(List<Integer> seasons) {
        return Lists.newArrayList(seasonRepository.findAllById(seasons)).stream().map(seasonEntityConverter::convert).collect(Collectors.toList());
    }

    public SeasonDto getSeason(Integer season) {
        return seasonEntityConverter.convert(Objects.requireNonNull(seasonRepository.findById(season).orElse(null)));
    }

    public Integer getObligatoryMinutes(Integer season) {
        Optional<SeasonEntity> seasonEntity = seasonRepository.findByYear(season);
        if (seasonEntity.isPresent()) {
            return seasonEntity.get().getObligatoryMinutes();
        } else {
            return -1;
        }
    }

    public AvailableSeasonsDto getAvailableSeasons() {
        AvailableSeasonsDto availableSeasonsDto = new AvailableSeasonsDto();

        availableSeasonsDto.setActiveSeason(getActiveSeason());
        availableSeasonsDto.setSeasons(getAllSeasons());

        return availableSeasonsDto;
    }

    public Integer getMaxSeason() {
        return seasonRepository.findTopByOrderByYearDesc().getYear();
    }

    public NextSeasonDto getNextSeason() {
        return seasonEntityToNextSeasonDtoConverter.convert(seasonRepository.findTopByOrderByYearDesc());
    }

    public Integer getFirstSeason(MemberEntity member) {
        return reductionRepository.findTopByMemberOrderBySeasonAsc(member).getSeason();
    }

    public Integer getActiveSeason() {
        ZonedDateTime now = ZonedDateTime.now();
        int currentYear = now.getYear();

        ZonedDateTime barrier = ZonedDateTime.now().withDayOfMonth(1).withMonth(Month.NOVEMBER.getValue());
        if (now.isAfter(barrier)) {
            return currentYear + 1;
        } else {
            return currentYear;
        }
    }
}