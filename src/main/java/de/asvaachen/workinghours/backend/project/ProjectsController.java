package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.CurrentProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.WorkingHoursSeasonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @GetMapping("/{year}")
    public ResponseEntity<List<ProjectDto>> getCurrentProjects(@PathVariable String year) {
        return new ResponseEntity<List<ProjectDto>>(createProjectsDto(), HttpStatus.OK);
    }

    private List<ProjectDto> createProjectsDto() {
        List<ProjectDto> projects = new ArrayList<>();

        Random random = new Random();
        int upperBound = 5 + random.nextInt(10);
        for (int i = 1; i < upperBound; i++) {
            projects.add(createProjectDto(i));
        }

        return projects;
    }

    private CurrentProjectsDto createCurrentProjectsDto() {
        List<ProjectDto> projects = new ArrayList<>();

        Random random = new Random();
        int upperBound = 5 + random.nextInt(10);
        for (int i = 1; i < upperBound; i++) {
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
        return new ProjectDto("" + id, "project" + id, random.nextInt(10000));
    }
}
