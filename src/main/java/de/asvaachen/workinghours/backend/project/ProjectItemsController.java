package de.asvaachen.workinghours.backend.project;

import de.asvaachen.workinghours.backend.project.model.ProjectItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projectItems")
public class ProjectItemsController {

    ProjectItemService projectItemService;

    public ProjectItemsController(ProjectItemService projectItemService) {
        this.projectItemService = projectItemService;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> createProjectItem(@RequestBody ProjectItemDto projectItemDto) {
        projectItemService.saveProjectItem(projectItemDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}