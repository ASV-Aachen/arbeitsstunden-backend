package de.asvaachen.workinghours.backend.login;

import de.asvaachen.workinghours.backend.members.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.UUID;

import static de.asvaachen.workinghours.backend.configuration.SecurityConfiguration.ROLE_USER;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping //XXX Secured and used
    public ResponseEntity<LoginDto> createSession(Principal principal) {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        LoginDto loginDto = new LoginDto();
        loginDto.setToken(UUID.randomUUID());
        loginDto.setRole(authorities.stream().findFirst().map(SimpleGrantedAuthority::getAuthority).orElse(ROLE_USER));
        loginDto.setMemberId(userService.getUserByEmail(principal.getName()).getMember().getId());
        return new ResponseEntity<>(loginDto, HttpStatus.CREATED);
    }
}