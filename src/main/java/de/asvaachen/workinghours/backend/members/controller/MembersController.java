package de.asvaachen.workinghours.backend.members.controller;

import de.asvaachen.workinghours.backend.members.converter.UserCreateDtoToMemberEntityConverter;
import de.asvaachen.workinghours.backend.members.model.CreateMemberDto;
import de.asvaachen.workinghours.backend.members.model.ErrorMessageDto;
import de.asvaachen.workinghours.backend.members.model.MemberDistributionItemDto;
import de.asvaachen.workinghours.backend.members.model.MemberListItemDto;
import de.asvaachen.workinghours.backend.members.model.MembersSummaryDto;
import de.asvaachen.workinghours.backend.members.model.UpdateMemberDto;
import de.asvaachen.workinghours.backend.members.service.MemberService;
import de.asvaachen.workinghours.backend.members.service.ReductionStatusService;
import de.asvaachen.workinghours.backend.members.service.UserService;
import de.asvaachen.workinghours.backend.project.model.MemberDto;
import de.asvaachen.workinghours.backend.projects.model.CurrentSeasonsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MembersController {

    private final MemberService memberService;
    private final UserService userService;
    private final ReductionStatusService reductionStatusService;
    private final UserCreateDtoToMemberEntityConverter userCreateDtoToMemberEntityConverter;

    public MembersController(MemberService memberService, UserService userService, ReductionStatusService reductionStatusService, UserCreateDtoToMemberEntityConverter userCreateDtoToMemberEntityConverter) {
        this.memberService = memberService;
        this.userService = userService;
        this.reductionStatusService = reductionStatusService;
        this.userCreateDtoToMemberEntityConverter = userCreateDtoToMemberEntityConverter;
    }

    @CrossOrigin
    @GetMapping //XXX Secured and used (takel)
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("takel") //XXX Secured and used (takel)
    public ResponseEntity<List<MemberDto>> getAllTakelMembers() {
        return new ResponseEntity<>(memberService.getAllTakelMembers(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("seasons") //XXX Secured and used
    public ResponseEntity<CurrentSeasonsDto> getSeasons() {
        return new ResponseEntity<>(memberService.getSeasons(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("{season}") //XXX Secured and used
    public ResponseEntity<List<MemberListItemDto>> getMembersForSeason(@PathVariable("season") Integer season) {
        return new ResponseEntity(memberService.getMemberList(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("{season}/details") //XXX Secured and used
    public ResponseEntity<MembersSummaryDto> getSummary(@PathVariable("season") Integer season) {
        return new ResponseEntity(memberService.getMembersSummary(season), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("{season}/distribution")
    public ResponseEntity<List<MemberDistributionItemDto>> getDistribution(@PathVariable("season") Integer season) {
        return new ResponseEntity(memberService.getMemberDistribution(season), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
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

    //@CrossOrigin
    //@PostMapping("reduction")
    //public ResponseEntity<Void> createReductionStatus(@RequestBody ReductionStatusCreateDto reductionStatusCreateDto) {
    //    reductionStatusService.create(reductionStatusCreateDto.getMemberId(), reductionStatusCreateDto.getYears());
    //    return new ResponseEntity(HttpStatus.OK);
    //}

    //@CrossOrigin
    //@GetMapping("list/{season}")
    //public ResponseEntity<List<MemberListItemDto>> getMemberList(@PathVariable("season") Integer season) {
    //    return new ResponseEntity(memberService.getMemberList(season), HttpStatus.OK);
    //}
}