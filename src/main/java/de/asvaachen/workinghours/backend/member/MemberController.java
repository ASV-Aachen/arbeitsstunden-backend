package de.asvaachen.workinghours.backend.member;

import de.asvaachen.workinghours.backend.configuration.SecurityConfiguration;
import de.asvaachen.workinghours.backend.member.model.MemberDetailsDto;
import de.asvaachen.workinghours.backend.member.model.MemberOverviewDto;
import de.asvaachen.workinghours.backend.member.model.MemberWorkinghoursDto;
import de.asvaachen.workinghours.backend.members.model.SeasonReductionDto;
import de.asvaachen.workinghours.backend.members.service.MemberService;
import de.asvaachen.workinghours.backend.members.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
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
    @GetMapping("/arbeitsstunden/api/member/{memberId}/detail")
    public ResponseEntity<MemberDetailsDto> getMemberDetails(@PathVariable("memberId") String memberId, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getMemberDetails(memberService.getMember(UUID.fromString(memberId))), HttpStatus.OK);
        } else {
            return new ResponseEntity(memberService.getMemberDetails(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/arbeitsstunden/api/member/{memberId}/overview")
    public ResponseEntity<MemberOverviewDto> getMemberOverview(@PathVariable("memberId") String memberId, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getMemberOverview(memberService.getMember(UUID.fromString(memberId))), HttpStatus.OK);
        } else {
            return new ResponseEntity(memberService.getMemberOverview(userService.getUserByEmail(principal.getName()).getMember()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/arbeitsstunden/api/member/{memberId}/season/{season}")
    public ResponseEntity<MemberWorkinghoursDto> getWorkinghours(@PathVariable("memberId") String memberId, @PathVariable("season") Integer season, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getMemberWorkinghours(memberService.getMember(UUID.fromString(memberId)), season), HttpStatus.OK);
        } else {
            return new ResponseEntity(memberService.getMemberWorkinghours(userService.getUserByEmail(principal.getName()).getMember(), season), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/arbeitsstunden/api/member/{memberId}/seasons")
    public ResponseEntity<List<SeasonReductionDto>> getStatusAndReduction(@PathVariable("memberId") String memberId, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            return new ResponseEntity(memberService.getSeasonsAndReductions(memberService.getMember(UUID.fromString(memberId))), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @PostMapping("/arbeitsstunden/api/member/{memberId}/seasons")
    public ResponseEntity<List<SeasonReductionDto>> updateStatusAndReduction(@PathVariable("memberId") String memberId, @RequestBody List<SeasonReductionDto> seasonsAndReductions, Principal principal) {
        if (securityConfiguration.isTakel(principal)) {
            memberService.updateSeasonsAndReductions(memberService.getMember(UUID.fromString(memberId)), seasonsAndReductions);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    // @CrossOrigin
    // @PostMapping("/arbeitsstunden/api/member/{memberId}/passwordReset")
    // public ResponseEntity<List<SeasonReductionDto>> resetPassword(@PathVariable("memberId") String memberId, Principal principal) {
    //     if (securityConfiguration.isTakel(principal)) {
    //         userService.resetPassword(memberService.getMember(UUID.fromString(memberId)).getUser());
    //         return new ResponseEntity(HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity(HttpStatus.FORBIDDEN);
    //     }
    // }
}
