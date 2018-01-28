package de.asvaachen.workinghours.backend.member.controller;

import de.asvaachen.workinghours.backend.member.converter.UserCreateDtoToMemberEntityConverter;
import de.asvaachen.workinghours.backend.member.model.*;
import de.asvaachen.workinghours.backend.member.service.MemberService;
import de.asvaachen.workinghours.backend.member.service.ReductionStatusService;
import de.asvaachen.workinghours.backend.member.service.UserService;
import de.asvaachen.workinghours.backend.project.model.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members/")
public class MembersController {

    private MemberService memberService;
    private UserService userService;
    private ReductionStatusService reductionStatusService;
    private UserCreateDtoToMemberEntityConverter userCreateDtoToMemberEntityConverter;

    public MembersController(MemberService memberService, UserService userService, ReductionStatusService reductionStatusService, UserCreateDtoToMemberEntityConverter userCreateDtoToMemberEntityConverter) {
        this.memberService = memberService;
        this.userService = userService;
        this.reductionStatusService = reductionStatusService;
        this.userCreateDtoToMemberEntityConverter = userCreateDtoToMemberEntityConverter;
    }

    @CrossOrigin
    @PostMapping("create")
    public ResponseEntity createUser(@Valid @RequestBody CreateMemberDto createMemberDto) {
        if (userService.createUser(userCreateDtoToMemberEntityConverter.convert(createMemberDto), createMemberDto.getFirstSeason(), createMemberDto.getStatus())) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), "EMAIL_EXISTING", "email", "Email bereits vorhanden"), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("update")
    public ResponseEntity editUser(Principal user, @RequestBody UpdateMemberDto updateMemberDto) {
        userService.updatePassword(user.getName(), updateMemberDto.getNewPassword());
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

        return new ResponseEntity(memberService.getActiveMemberWorkinghours(userService.getUserByEmail(principal.getName()).getMember(), year), HttpStatus.OK);
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
        return new ResponseEntity(memberService.getActiveMemberWorkinghours(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("overview")
    public ResponseEntity<List<OverviewSeasonDto>> getMemberOverview(Principal principal) {
        return new ResponseEntity(memberService.getMemberOverview(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("detail")
    public ResponseEntity<MemberDetailsDto> getMemberDetails(Principal principal) {
        return new ResponseEntity(memberService.getMemberDetails(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
    }
}