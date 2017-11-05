package de.asvaachen.workinghours.backend.cleanup;

import de.asvaachen.workinghours.backend.project.MemberRepository;
import de.asvaachen.workinghours.backend.project.ProjectItemRepository;
import de.asvaachen.workinghours.backend.project.ProjectRepository;
import de.asvaachen.workinghours.backend.project.SeasonRepository;
import de.asvaachen.workinghours.backend.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cleanup")
public class CleanupController {

    MemberRepository memberRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    ProjectItemRepository projectItemRepository;
    SeasonRepository seasonRepository;

    public CleanupController(MemberRepository memberRepository, UserRepository userRepository, ProjectRepository projectRepository, ProjectItemRepository projectItemRepository, SeasonRepository seasonRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectItemRepository = projectItemRepository;
        this.seasonRepository = seasonRepository;
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        projectItemRepository.deleteAll();
        projectRepository.deleteAll();
        memberRepository.deleteAll();
        seasonRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}