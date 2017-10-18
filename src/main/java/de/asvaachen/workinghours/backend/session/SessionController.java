package de.asvaachen.workinghours.backend.session;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @CrossOrigin
    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody CredentialsDto user) {
        if (user.getEmail().equals("test@test.de") && user.getPassword().equals("4sv")) {
            SessionDto sessionDto = new SessionDto();
            sessionDto.setToken(UUID.randomUUID());
            return new ResponseEntity<>(sessionDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}