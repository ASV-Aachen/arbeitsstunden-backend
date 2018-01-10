package de.asvaachen.workinghours.backend.user;

import de.asvaachen.workinghours.backend.project.MemberDto;
import de.asvaachen.workinghours.backend.user.model.CreateMemberDto;
import de.asvaachen.workinghours.backend.user.model.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members/")
public class MembersController {

    private MembersService memberService;
    private UsersService usersService;
    private ReductionStatusService reductionStatusService;
    private UserCreateDtoToMemberEntityConverter userCreateDtoToMemberEntityConverter;

    public MembersController(MembersService memberService, UsersService usersService, ReductionStatusService reductionStatusService, UserCreateDtoToMemberEntityConverter userCreateDtoToMemberEntityConverter) {
        this.memberService = memberService;
        this.usersService = usersService;
        this.reductionStatusService = reductionStatusService;
        this.userCreateDtoToMemberEntityConverter = userCreateDtoToMemberEntityConverter;
    }

    @CrossOrigin
    @PostMapping("create")
    public ResponseEntity updateUser(@Valid @RequestBody CreateMemberDto createMemberDto) {
        if (usersService.createUser(userCreateDtoToMemberEntityConverter.convert(createMemberDto), createMemberDto.getFirstSeason(), createMemberDto.getStatus())) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), "EMAIL_EXISTING", "email", "Email bereits vorhanden"), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("update")
    public ResponseEntity editUser(Principal user, @RequestBody UpdateMemberDto updateMemberDto) {
        usersService.updatePassword(user.getName(), updateMemberDto.getNewPassword());
        return new ResponseEntity(HttpStatus.OK);
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
    public ResponseEntity<List<MemberListItemDto>> getMemberList(@PathVariable("season") Integer season) {
        return new ResponseEntity(memberService.getMemberList(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("distribution/{season}")
    public ResponseEntity<List<MemberDistributionItemDto>> getDistribution(@PathVariable("season") Integer season) {
        return new ResponseEntity(memberService.getMemberDistribution(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("summary/{season}")
    public ResponseEntity<MembersSummaryDto> getSummary(@PathVariable("season") Integer season) {
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