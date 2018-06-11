package de.asvaachen.workinghours.backend.projects;

import de.asvaachen.workinghours.backend.project.converter.ProjectDtoToProjectEntityConverter;
import de.asvaachen.workinghours.backend.project.model.ProjectDto;
import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import de.asvaachen.workinghours.backend.project.service.ProjectService;
import de.asvaachen.workinghours.backend.projects.model.CurrentSeasonsDto;
import de.asvaachen.workinghours.backend.projects.model.ProjectsDetailDto;
import de.asvaachen.workinghours.backend.projects.model.ProjectsOverviewDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("{season}") //XXX Secured and used
    public ResponseEntity<List<ProjectOverviewDto>> getProjects(@PathVariable Integer season) {
        return new ResponseEntity<>(projectService.getSeasons(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("seasons") //XXX Secured and used
    public ResponseEntity<CurrentSeasonsDto> getSeasons() {
        return new ResponseEntity<>(projectService.getSeasons(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("overview") //XXX Secured and used
    public ResponseEntity<List<ProjectsOverviewDto>> getOverview() {
        return new ResponseEntity<>(projectService.getOverview(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("{season}/detail") //XXX Secured and used
    public ResponseEntity<ProjectsDetailDto> getDetail(@PathVariable Integer season) {
        return new ResponseEntity<>(projectService.getDetail(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping //XXX Secured and used
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    //@CrossOrigin
    //@PostMapping
    //public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project) {
    //    return new ResponseEntity<>(projectService.createProject(converter.convert(project)), HttpStatus.CREATED);
    //}

    //@CrossOrigin
    //@GetMapping("/takel/summary")
    //public ResponseEntity<ProjectsTakelSummaryDto> getProjectsTakelSummary() {
    //    return new ResponseEntity<>(projectService.getProjectsTakelSummary(), HttpStatus.OK);
    //}
}