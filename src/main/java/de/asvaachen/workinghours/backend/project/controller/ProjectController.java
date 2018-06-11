package de.asvaachen.workinghours.backend.project.controller;

import de.asvaachen.workinghours.backend.project.model.ProjectDetailsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDetailsItemDto;
import de.asvaachen.workinghours.backend.project.model.ProjectDurationsForYearsDto;
import de.asvaachen.workinghours.backend.project.model.ProjectItemDto;
import de.asvaachen.workinghours.backend.project.service.ProjectItemService;
import de.asvaachen.workinghours.backend.project.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectItemService projectItemService;
    private final ProjectService projectService;

    public ProjectController(ProjectItemService projectItemService, ProjectService projectService) {
        this.projectItemService = projectItemService;
        this.projectService = projectService;
    }

    @CrossOrigin
    @PostMapping //XXX Secured and used
    public ResponseEntity<Void> createProjectItem(@RequestBody ProjectItemDto projectItemDto) {
        projectItemService.saveProjectItem(projectItemDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("{projectId}/{season}")  //XXX Secured and used
    public ResponseEntity<List<ProjectDetailsItemDto>> getProject(@PathVariable UUID projectId, @PathVariable Integer season) {
        return new ResponseEntity<>(projectService.getProjectDetails(season, projectId), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("{projectId}/{season}/details")  //XXX Secured and used
    public ResponseEntity<ProjectDetailsDto> getProjectDetails(@PathVariable UUID projectId, @PathVariable Integer season) {
        return new ResponseEntity<>(projectService.getProjectSummary(season, projectId), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("{projectId}/distribution")  //XXX Secured and used
    public ResponseEntity<List<ProjectDurationsForYearsDto>> getProjectYears(@PathVariable UUID projectId) {
        return new ResponseEntity<>(projectService.getProjectForYears(projectId), HttpStatus.OK);
    }
}
