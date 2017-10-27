package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.CurrentProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import de.asvaachen.workinghours.backend.season.model.SeasonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    ProjectService projectService;
    ProjectDtoToProjectEntityConverter converter;

    public ProjectsController(ProjectService projectService, ProjectDtoToProjectEntityConverter converter) {
        this.projectService = projectService;
        this.converter = converter;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("active")
    public ResponseEntity<List<ProjectDto>> getActiveProjects() {
        return new ResponseEntity<>(projectService.getActiveProjects(), HttpStatus.OK);
    }



    @CrossOrigin
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project) {
        return new ResponseEntity<>(projectService.createProject(converter.convert(project)), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/current")
    public ResponseEntity<CurrentProjectsDto> getCurrentProjects() {
        return new ResponseEntity<CurrentProjectsDto>(createCurrentProjectsDto(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{year}")
    public ResponseEntity<List<ProjectOverviewDto>> getCurrentProjects(@PathVariable String year) {
        return new ResponseEntity<List<ProjectOverviewDto>>(createProjectsOverviewDto(), HttpStatus.OK);
    }
/*
    @CrossOrigin
    @PostMapping("/create/{year}")
    public ResponseEntity<ProjectDto> createProject(@PathVariable String year, @Valid @RequestBody CreateProjectDto projectDto) {
        ProjectEntity projectEntity = converter.convert(projectDto);

        return new ResponseEntity<ProjectDto>(projectService.createProject(projectEntity), HttpStatus.OK);
    }*/

    private List<ProjectDto> createProjectsDto() {
        List<ProjectDto> projects = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            projects.add(createProjectDto(i));
        }

        return projects;
    }

    private List<ProjectOverviewDto> createProjectsOverviewDto() {
        List<ProjectOverviewDto> projects = new ArrayList<>();

        Random random = new Random();
        int upperBound = 5 + random.nextInt(10);
        for (int i = 1; i < upperBound; i++) {
            projects.add(createProjectOverviewDto(i));
        }

        return projects;
    }

    private CurrentProjectsDto createCurrentProjectsDto() {
        List<ProjectOverviewDto> projects = new ArrayList<>();

        Random random = new Random();
        int upperBound = 5 + random.nextInt(10);
        for (int i = 1; i < upperBound; i++) {
            projects.add(createProjectOverviewDto(i));
        }

        CurrentProjectsDto currentProjectsDto = new CurrentProjectsDto("2016", createWorkingHoursSeasonDto(), projects);
        return currentProjectsDto;
    }

    private List<SeasonDto> createWorkingHoursSeasonDto() {
        List<SeasonDto> availableSeasons = new ArrayList<>();

        availableSeasons.add(new SeasonDto(2010, "2010/2011"));
        availableSeasons.add(new SeasonDto(2011, "2011/2012"));
        availableSeasons.add(new SeasonDto(2012, "2012/2013"));
        availableSeasons.add(new SeasonDto(2013, "2013/2014"));
        availableSeasons.add(new SeasonDto(2015, "2015/2016"));
        availableSeasons.add(new SeasonDto(2016, "2016/2017"));
        availableSeasons.add(new SeasonDto(2014, "2014/2015"));
        availableSeasons.add(new SeasonDto(2017, "2017/2018"));

        return availableSeasons;
    }

    private ProjectOverviewDto createProjectOverviewDto(Integer id) {
        Random random = new Random();
        ProjectOverviewDto project = new ProjectOverviewDto();
        project.setId(Integer.toString(id));
        project.setName("project" + id);
        project.setDuration(random.nextInt(10000));
        return project;
    }

    private ProjectDto createProjectDto(Integer id) {
        Random random = new Random();
        ProjectDto project = new ProjectDto();
        project.setId(Integer.toString(id));
        project.setName("project" + id);
        project.setDescription("description of project" + id);
        return project;
    }
}