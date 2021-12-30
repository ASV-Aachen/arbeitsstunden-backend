package de.asvaachen.workinghours.backend.season;

import de.asvaachen.workinghours.backend.configuration.SecurityConfiguration;
import de.asvaachen.workinghours.backend.project.service.ProjectService;
import de.asvaachen.workinghours.backend.season.converter.SeasonDtoToSeasonEntityConverter;
import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.NextSeasonDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/arbeitsstunden/api/seasons")
public class SeasonController {

    private final SeasonService seasonService;
    private final ProjectService projectService;
    private final SecurityConfiguration securityConfiguration;
    private final SeasonDtoToSeasonEntityConverter converter;

    public SeasonController(SeasonService seasonService,
                            ProjectService projectService, SecurityConfiguration securityConfiguration, SeasonDtoToSeasonEntityConverter converter) {
        this.seasonService = seasonService;
        this.projectService = projectService;
        this.securityConfiguration = securityConfiguration;
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

    @CrossOrigin
    @GetMapping("{season}/export")
    public ResponseEntity<String> export(Principal principal, @PathVariable Integer season) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity<>(projectService.exportForSeason(season), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}