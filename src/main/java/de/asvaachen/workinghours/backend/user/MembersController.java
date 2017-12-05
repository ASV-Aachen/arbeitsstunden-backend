package de.asvaachen.workinghours.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CrossOrigin
    @GetMapping("list/{season}")
    public ResponseEntity<List<MemberListItemDto>> getMemberList(Integer season) {
        return new ResponseEntity(memberService.getMemberList(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("distribution/{season}")
    public ResponseEntity<List<MemberDistributionItemDto>> getDistribution(Integer season) {
        return new ResponseEntity(memberService.getMemberDistribution(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("summary/{season}")
    public ResponseEntity<MembersSummaryDto> getSummary(Integer season) {
        return new ResponseEntity(memberService.getMembersSummary(season), HttpStatus.OK);
    }
}