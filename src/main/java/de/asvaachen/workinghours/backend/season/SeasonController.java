package de.asvaachen.workinghours.backend.season;

import de.asvaachen.workinghours.backend.season.converter.SeasonDtoToSeasonEntityConverter;
import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.NextSeasonDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/seasons")
public class SeasonController {

    private final SeasonService seasonService;
    private final SeasonDtoToSeasonEntityConverter converter;

    public SeasonController(SeasonService seasonService,
                            SeasonDtoToSeasonEntityConverter converter) {
        this.seasonService = seasonService;
        this.converter = converter;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<SeasonDto> createSeason(@RequestBody @Valid SeasonDto seasonDto) {
        return new ResponseEntity<>(seasonService.createSeason(converter.convert(seasonDto)), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<AvailableSeasonsDto> getAvailableSeasons() {
        return new ResponseEntity<>(seasonService.getAvailableSeasons(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("next")
    public ResponseEntity<NextSeasonDto> getNextSeason() {
        return new ResponseEntity<>(seasonService.getNextSeason(), HttpStatus.OK);
    }
}