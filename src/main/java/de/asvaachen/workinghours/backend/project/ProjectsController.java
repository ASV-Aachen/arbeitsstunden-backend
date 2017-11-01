package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.AktiveProjectsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("{year}")
    public ResponseEntity<List<ProjectDto>> getActiveProjectsForYear(@PathVariable Integer year) {
        return new ResponseEntity<>(projectService.getActiveProjects(year), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("active")
    public ResponseEntity<AktiveProjectsDto> getActiveProjects() {
        return new ResponseEntity<>(projectService.getActiveProjects(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project) {
        return new ResponseEntity<>(projectService.createProject(converter.convert(project)), HttpStatus.CREATED);
    }
}