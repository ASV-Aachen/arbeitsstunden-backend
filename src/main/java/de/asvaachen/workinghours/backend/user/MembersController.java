package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members/")
public class MembersController {

    MembersService memberService;
    ReductionStatusService reductionStatusService;

    public MembersController(MembersService memberService, ReductionStatusService reductionStatusService) {
        this.memberService = memberService;
        this.reductionStatusService = reductionStatusService;
    }

    @CrossOrigin
    @PostMapping("reduction")
    public ResponseEntity<Void> createReductionStatus(@RequestBody ReductionStatusCreateDto reductionStatusCreateDto) {
        reductionStatusService.create(reductionStatusCreateDto.getMemberId(), reductionStatusCreateDto.getYears());
        return new ResponseEntity(HttpStatus.OK);
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

    @CrossOrigin
    @GetMapping("overview")
    public ResponseEntity<List<OverviewSeasonDto>> getMemberOverview() {
        return new ResponseEntity(memberService.getMemberOverview(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("detail")
    public ResponseEntity<MemberDetailsDto> getMemberDetails() {
        return new ResponseEntity(memberService.getMemberDetails(), HttpStatus.OK);
    }
}