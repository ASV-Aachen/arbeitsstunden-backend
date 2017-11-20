package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ActiveProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project) {
        return new ResponseEntity<>(projectService.createProject(converter.convert(project)), HttpStatus.CREATED);
    }


    @CrossOrigin
    @GetMapping("{year}")
    public ResponseEntity<List<ProjectOverviewDto>> getActiveProjectsForYear(@PathVariable Integer year) {
        return new ResponseEntity<>(projectService.getActiveProjects(year), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("active")
    public ResponseEntity<ActiveProjectsDto> getActiveProjects() {
        return new ResponseEntity<>(projectService.getActiveProjects(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("details/{season}/{projectId}")
    public ResponseEntity<ProjectDetailsDto> getProjectDetails(@PathVariable Integer season, @PathVariable UUID projectId) {
        return new ResponseEntity<>(projectService.getProjectDetails(season, projectId),HttpStatus.OK);
    }
}