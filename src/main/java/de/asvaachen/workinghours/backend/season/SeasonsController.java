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
    public ResponseEntity<AvailableSeasonsDto> getAvailableSeasons() {
        return new ResponseEntity<>(seasonService.getAvailableSeasons(), HttpStatus.OK);
    }
}
