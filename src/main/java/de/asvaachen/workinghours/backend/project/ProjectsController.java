package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    ProjectService projectService;
    CreateProjectDtoToProjectEntityConverter converter;

    public ProjectsController(ProjectService projectService, CreateProjectDtoToProjectEntityConverter converter) {
        this.projectService = projectService;
        this.converter = converter;
    }

    @CrossOrigin
    @GetMapping("/current")
    public ResponseEntity<CurrentProjectsDto> getCurrentProjects() {
        return new ResponseEntity<CurrentProjectsDto>(createCurrentProjectsDto(), HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping("/{year}")
    public ResponseEntity<List<ProjectOverviewDto>> getCurrentProjects(@PathVariable String year) {
        return new ResponseEntity<List<ProjectOverviewDto>>(createProjectsDto(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/create/{year}")
    public ResponseEntity<ProjectDto> createProject(@PathVariable String year, @Valid @RequestBody CreateProjectDto projectDto) {
        ProjectEntity projectEntity = converter.convert(projectDto);

        return new ResponseEntity<ProjectDto>(projectService.createProject(projectEntity), HttpStatus.OK);
    }

    private List<ProjectOverviewDto> createProjectsDto() {
        List<ProjectOverviewDto> projects = new ArrayList<>();

        Random random = new Random();
        int upperBound = 5 + random.nextInt(10);
        for (int i = 1; i < upperBound; i++) {
            projects.add(createProjectDto(i));
        }

        return projects;
    }

    private CurrentProjectsDto createCurrentProjectsDto() {
        List<ProjectOverviewDto> projects = new ArrayList<>();

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

    private ProjectOverviewDto createProjectDto(Integer id) {
        Random random = new Random();


        ProjectOverviewDto project = new ProjectOverviewDto();
        project.setId(Integer.toString(id));
        project.setName("project" + id);
        project.setDuration(random.nextInt(10000));
        return project;
    }
}
