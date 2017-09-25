package de.asvaachen.workinghours.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    @CrossOrigin
    @GetMapping("/current")
    public ResponseEntity<CurrentProjectsDto> getCurrentProjects() {

        return new ResponseEntity<CurrentProjectsDto>(createCurrentProjectsDto(), HttpStatus.OK);

    }

    private CurrentProjectsDto createCurrentProjectsDto() {
        List<ProjectDto> projects = new ArrayList<>();

        Random random = new Random();
        int upperBound = 5+random.nextInt(10);
        for(int i=1; i < upperBound; i++) {
            projects.add(createProjectDto(i));
        }

        CurrentProjectsDto currentProjectsDto = new CurrentProjectsDto("2016", createWorkingHoursSeasonDto(), projects);
        return currentProjectsDto;
    }

    private List<WorkingHoursSeasonDto> createWorkingHoursSeasonDto() {
        List<WorkingHoursSeasonDto> availableSeasons = new ArrayList<>();

        availableSeasons.add(new WorkingHoursSeasonDto("2010", "2010/2011"));
        availableSeasons.add(new WorkingHoursSeasonDto("2011", "2011/2012"));
        availableSeasons.add(new WorkingHoursSeasonDto("2012", "2012/2013"));
        availableSeasons.add(new WorkingHoursSeasonDto("2013", "2013/2014"));
        availableSeasons.add(new WorkingHoursSeasonDto("2015", "2015/2016"));
        availableSeasons.add(new WorkingHoursSeasonDto("2016", "2016/2017"));
        availableSeasons.add(new WorkingHoursSeasonDto("2014", "2014/2015"));
        availableSeasons.add(new WorkingHoursSeasonDto("2017", "2017/2018"));

        return availableSeasons;
    }

    private ProjectDto createProjectDto(Integer id) {
        Random random = new Random();
        return new ProjectDto(""+id, "project"+id, random.nextInt(10000));
    }
}
