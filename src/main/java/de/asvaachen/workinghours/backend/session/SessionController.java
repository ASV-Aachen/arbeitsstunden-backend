package de.asvaachen.workinghours.backend.session;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @CrossOrigin
    @PostMapping
    public ResponseEntity<SessionDto> createSession() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setToken(UUID.randomUUID());
        return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
    }
}