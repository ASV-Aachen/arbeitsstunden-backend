package de.asvaachen.workinghours.backend.season;

import de.asvaachen.workinghours.backend.project.SeasonService;
import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * Programmatically we switch to a new workinghour season at 01.11.X to 31.10.X+1.
 **/
@RestController
@RequestMapping("/api/seasons")
public class SeasonsController {

    SeasonService seasonService;
    SeasonDtoToSeasonEntityConverter converter;

    public SeasonsController(SeasonService seasonService, SeasonDtoToSeasonEntityConverter converter) {
        this.seasonService = seasonService;
        this.converter = converter;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<SeasonDto> createCurrentSeason(@RequestBody @Valid SeasonDto seasonDto) {
        return new ResponseEntity<SeasonDto>(seasonService.createSeason(converter.convert(seasonDto)), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<AvailableSeasonsDto> getCurrentSeasons() {
        return new ResponseEntity<>(createAvailableSeasonsDto(), HttpStatus.OK);
    }

    private AvailableSeasonsDto createAvailableSeasonsDto() {
        return new AvailableSeasonsDto(2017, createWorkingHoursSeasonDto());
    }

    private List<SeasonDto> createWorkingHoursSeasonDto() {
        List<SeasonDto> availableSeasons = new ArrayList<>();
        return availableSeasons;
    }
}
