package de.asvaachen.workinghours.backend.season;

import de.asvaachen.workinghours.backend.season.model.AvailableSeasonsDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Programmatically we switch to a new workinghour season at 01.11.X to 31.10.X+1.
 **/
@RestController
@RequestMapping("/api/seasons")
public class SeasonsController {

    @CrossOrigin
    @GetMapping
    public ResponseEntity<AvailableSeasonsDto> getCurrentSeasons() {
        return new ResponseEntity<>(createAvailableSeasonsDto(), HttpStatus.OK);
    }

    private AvailableSeasonsDto createAvailableSeasonsDto() {
        return new AvailableSeasonsDto(2016, createWorkingHoursSeasonDto());
    }

    private List<SeasonDto> createWorkingHoursSeasonDto() {
        List<SeasonDto> availableSeasons = new ArrayList<>();

        availableSeasons.add(new SeasonDto(2007, "2007/2008"));
        availableSeasons.add(new SeasonDto(2008, "2008/2009"));
        availableSeasons.add(new SeasonDto(2009, "2009/2010"));
        availableSeasons.add(new SeasonDto(2010, "2010/2011"));
        availableSeasons.add(new SeasonDto(2011, "2011/2012"));
        availableSeasons.add(new SeasonDto(2012, "2012/2013"));
        availableSeasons.add(new SeasonDto(2013, "2013/2014"));
        availableSeasons.add(new SeasonDto(2014, "2014/2015"));
        availableSeasons.add(new SeasonDto(2015, "2015/2016"));
        availableSeasons.add(new SeasonDto(2016, "2016/2017"));
        availableSeasons.add(new SeasonDto(2017, "2017/2018"));
        availableSeasons.add(new SeasonDto(2018, "2018/2019"));

        return availableSeasons;
    }
}
