package de.asvaachen.workinghours.backend.session;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

import static de.asvaachen.workinghours.backend.configuration.SecurityConfiguration.ROLE_USER;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @CrossOrigin
    @PostMapping
    public ResponseEntity<SessionDto> createSession() {

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        SessionDto sessionDto = new SessionDto();
        sessionDto.setToken(UUID.randomUUID());
        sessionDto.setRole(authorities.stream().findFirst().map(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority()).orElse(ROLE_USER));
        return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }
}