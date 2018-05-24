package de.asvaachen.workinghours.backend.member;

import de.asvaachen.workinghours.backend.configuration.SecurityConfiguration;
import de.asvaachen.workinghours.backend.member.model.MemberDetailsDto;
import de.asvaachen.workinghours.backend.member.model.MemberOverviewDto;
import de.asvaachen.workinghours.backend.member.model.MemberWorkinghoursDto;
import de.asvaachen.workinghours.backend.members.service.MemberService;
import de.asvaachen.workinghours.backend.members.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping
public class MemberController {

    private final UserService userService;
    private final MemberService memberService;
    private final SecurityConfiguration securityConfiguration;

    public MemberController(UserService userService, MemberService memberService, SecurityConfiguration securityConfiguration) {
        this.userService = userService;
        this.memberService = memberService;
        this.securityConfiguration = securityConfiguration;
    }

    @CrossOrigin
    @GetMapping("/api/member/{memberId}/detail") //XXX Secured and used
    public ResponseEntity<MemberDetailsDto> getMemberDetails(@PathVariable("memberId") String memberId, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getMemberDetails(memberService.getMember(UUID.fromString(memberId))), HttpStatus.OK);
        } else {
            return new ResponseEntity(memberService.getMemberDetails(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/api/member/{memberId}/overview") //XXX Secured and used
    public ResponseEntity<MemberOverviewDto> getMemberOverview(@PathVariable("memberId") String memberId, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getMemberOverview(memberService.getMember(UUID.fromString(memberId))), HttpStatus.OK);
        } else {
            return new ResponseEntity(memberService.getMemberOverview(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/api/member/{memberId}/season/{season}") //XXX Secured and used
    public ResponseEntity<MemberWorkinghoursDto> getWorkinghours(@PathVariable("memberId") String memberId, @PathVariable("season") Integer season, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getMemberWorkinghours(memberService.getMember(UUID.fromString(memberId)), season), HttpStatus.OK);
        } else {
            return new ResponseEntity(memberService.getMemberWorkinghours(userService.getUserByEmail(principal.getName()).getMember(), season), HttpStatus.OK);
        }
    }
}
