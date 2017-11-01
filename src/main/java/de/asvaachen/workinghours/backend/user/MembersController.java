package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.model.ProjectOverviewDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members/")
public class MembersController {

    MembersService memberService;

    public MembersController(MembersService memberService) {
        this.memberService = memberService;
    }

    @CrossOrigin
    @GetMapping("{year}")
    public ResponseEntity<List<WorkinghourItemDto>> getWorkinghoursForYear(@PathVariable Integer year) {
        return new ResponseEntity(memberService.getActiveMemberWorkinghours(year), HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("active")
    public ResponseEntity<ActiveMemberWorkinghoursDto> getActiveWorkinghours() {
        return new ResponseEntity(memberService.getActiveMemberWorkinghours(), HttpStatus.OK);
    }
}