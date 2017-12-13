package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members/")
public class MembersController {

    private MembersService memberService;
    private UsersService usersService;
    private ReductionStatusService reductionStatusService;

    public MembersController(MembersService memberService, UsersService usersService, ReductionStatusService reductionStatusService) {
        this.memberService = memberService;
        this.usersService = usersService;
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
    public ResponseEntity<MemberWorkinghoursDto> getWorkinghoursForYear(Principal principal, @PathVariable Integer year) {

        return new ResponseEntity(memberService.getActiveMemberWorkinghours(usersService.getUserByEmail(principal.getName()).getMember(), year), HttpStatus.OK);
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


    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return new ResponseEntity<List<MemberDto>>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("active")
    public ResponseEntity<ActiveMemberWorkinghoursDto> getActiveWorkinghours(Principal principal) {
        return new ResponseEntity(memberService.getActiveMemberWorkinghours(usersService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("overview")
    public ResponseEntity<List<OverviewSeasonDto>> getMemberOverview(Principal principal) {
        return new ResponseEntity(memberService.getMemberOverview(usersService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("detail")
    public ResponseEntity<MemberDetailsDto> getMemberDetails(Principal principal) {
        return new ResponseEntity(memberService.getMemberDetails(usersService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
    }
}